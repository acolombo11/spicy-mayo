import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.detekt) apply false
}

val nameSpace by extra("eu.acolombo.work.calendar")
val javaVersion = libs.versions.java.get()

subprojects {
    when {
        path.count { it == ':' } >= 2 -> {
            plugins()
            targets()
            if (name == "presentation" || rootName == "design") compose()
            if (rootName in listOf("feature", "core")) koin()
            if (name == "presentation") composeFeature()
            detekt()
        }

        name == "app" -> {
            plugins()
            targets(name)
            koin()
            compose()
            composeFeature()
            detekt()
        }
    }
}

private fun Project.plugins() {
    plugins {
        alias(rootProject.libs.plugins.android.kotlin.multiplatform.library)
        alias(rootProject.libs.plugins.kotlin.multiplatform)
    }
}

private fun Project.targets(targetName: String? = null) {
    kotlin<KotlinMultiplatformExtension> {
        jvmToolchain(javaVersion.toInt())

        if (pluginManager.hasPlugin(rootProject.libs.plugins.android.kotlin.multiplatform.library.get().pluginId)) {
            extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
                namespace = nameSpace + path.replace(':', '.')
                compileSdk = rootProject.libs.versions.android.targetSdk.get().toInt()
                minSdk = rootProject.libs.versions.android.minSdk.get().toInt()
                androidResources.enable = true
            }
        }

        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        ).forEach { iosTarget ->
            targetName?.let {
                iosTarget.binaries.framework {
                    baseName = targetName
                    isStatic = true
                }
            }
        }
    }
}

private fun Project.compose() {
    plugins {
        alias(rootProject.libs.plugins.jetbrains.compose)
        alias(rootProject.libs.plugins.compose.compiler)
    }

    kotlin<KotlinMultiplatformExtension> {
        sourceSets {
            commonMain.dependencies {
                implementation(rootProject.libs.compose.runtime)
                implementation(rootProject.libs.compose.foundation)
                implementation(rootProject.libs.compose.material3)
                implementation(rootProject.libs.compose.ui)
                implementation(rootProject.libs.compose.components.resources)
                implementation(rootProject.libs.compose.tooling.preview)
            }
        }
    }
}

private fun Project.koin() {
    kotlin<KotlinMultiplatformExtension> {
        sourceSets {
            commonMain.dependencies {
                implementation(rootProject.libs.koin.core)
            }
        }
    }
}

private fun Project.composeFeature() {
    plugins {
        alias(rootProject.libs.plugins.kotlin.serialization)
    }

    kotlin<KotlinMultiplatformExtension> {
        sourceSets {
            commonMain.dependencies {
                implementation(rootProject.libs.kotlinx.serialization)

                implementation(rootProject.projects.design.theme)
                implementation(rootProject.libs.koin.compose)
                implementation(rootProject.libs.koin.compose.viewmodel)
                implementation(rootProject.libs.lifecycle.runtime.compose)
                implementation(rootProject.libs.lifecycle.viewmodel.compose)
                implementation(rootProject.libs.navigation.compose)
            }
        }
    }
}

private fun Project.detekt() {
    plugins {
        alias(rootProject.libs.plugins.detekt)
    }

    detekt {
        buildUponDefaultConfig = true

        val baselineFile = file("config/detekt/baseline.xml")
        val configFile = "$rootDir/config/detekt/detekt.yml"
        config.setFrom(configFile)
        baseline = baselineFile

        dependencies {
            add("detektPlugins", rootProject.libs.detekt.formatting)
            add("detektPlugins", rootProject.libs.detekt.compose.rules)
        }

        tasks.withType<Detekt>().configureEach {
            // Clearing classpath to avoid variant ambiguity in KMP Android modules
            // Type-safe analysis will be disabled for these tasks as a workaround for resolution issues
            classpath.setFrom(files())
            setSource(files(project.projectDir))
            exclude("**/build/**")
            exclude { it.file.toPath().startsWith(layout.buildDirectory.get().asFile.toPath()) }
        }
        val detektBaselines by tasks.registering(DetektCreateBaselineTask::class) {
            description = "Overrides current baseline."
            buildUponDefaultConfig = true
            ignoreFailures = true
            parallel = true
            classpath.setFrom(files())
            setSource(files(projectDir))
            config.setFrom(files(configFile))
            baseline.set(baselineFile)
            include("**/*.kt")
            include("**/*.kts")
            exclude("**/resources/**")
            exclude("**/build/**")
        }
        val detektAll by tasks.registering {
            dependsOn(tasks.withType<Detekt>())
        }
    }
}

//region Project Extensions to write code in Project scope as if it was in module's build.gradle.kts
private fun Project.detekt(block: DetektExtension.() -> Unit) {
    the<DetektExtension>().apply(block)
}

private inline fun <reified T : org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension> Project.kotlin(
    block: T.() -> Unit
) {
    the<T>().apply(block)
}

private fun Project.plugins(apply: ProjectPluginDependenciesSpecScope.() -> Unit) {
    apply(ProjectPluginDependenciesSpecScope(this))
}

private val Project.rootName
    get() = path.split(':')[1]

class ProjectPluginDependenciesSpecScope(private val project: Project) {
    fun alias(provider: Provider<PluginDependency>) {
        project.apply(plugin = provider.get().pluginId)
    }
}
//endregion