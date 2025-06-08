import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.detekt) apply false
}

val nameSpace = "eu.acolombo.work.calendar"
val javaVersion = libs.versions.java.jdk.get()

subprojects {
    when {
        path.count { it == ':' } >= 2 -> {
            androidLibrary()
            targets()
            if (name  == "presentation" || rootName == "design") compose()
            if (rootName in listOf("feature", "core")) koin()
            if (name == "presentation") composeFeature()
            detekt()
        }
        name == "app" -> {
            androidApplication()
            targets(name)
            composeApp()
            koin()
            composeFeature()
            detekt()
        }
    }
}

private fun Project.targets(targetName: String? = null) {
    plugins {
        alias(rootProject.libs.plugins.kotlin.multiplatform)
    }

    kotlin<KotlinMultiplatformExtension> {
        jvmToolchain(javaVersion.toInt())
        androidTarget {
            compilerOptions {
                jvmTarget.set(JvmTarget.fromTarget(javaVersion))
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

private fun Project.androidApplication() {
    plugins {
        alias(rootProject.libs.plugins.android.application)
    }

    android<ApplicationExtension> {
        namespace = nameSpace
        compileSdk = rootProject.libs.versions.android.targetSdk.get().toInt()
        defaultConfig {
            minSdk = rootProject.libs.versions.android.minSdk.get().toInt()
            targetSdk = rootProject.libs.versions.android.targetSdk.get().toInt()
            applicationId = nameSpace
        }

        compileOptions {
            val javaVersion = JavaVersion.toVersion(javaVersion.toInt())
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
    }
}

private fun Project.androidLibrary() {
    plugins {
        alias(rootProject.libs.plugins.android.library)
    }

    android<LibraryExtension> {
        namespace = nameSpace + path.replace(':','.')
        compileSdk = rootProject.libs.versions.android.targetSdk.get().toInt()
        defaultConfig {
            minSdk = rootProject.libs.versions.android.minSdk.get().toInt()
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
                val compose = org.jetbrains.compose.ComposePlugin.Dependencies(rootProject)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
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
                implementation(rootProject.libs.navigation.compose)
                implementation(rootProject.libs.koin.compose)
                implementation(rootProject.libs.koin.compose.viewmodel)
            }
        }
    }
}

private fun Project.composeApp() {
    plugins {
        alias(rootProject.libs.plugins.jetbrains.compose)
        alias(rootProject.libs.plugins.compose.compiler)
    }

    kotlin<KotlinMultiplatformExtension> {
        sourceSets {
            commonMain.dependencies {
                val compose = org.jetbrains.compose.ComposePlugin.Dependencies(rootProject)
                implementation(compose.runtime)
                implementation(compose.components.uiToolingPreview)
            }

            androidMain.dependencies {
                implementation(rootProject.libs.androidx.activity.compose)
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

        tasks.withType<Detekt> {
            setSource(files(project.projectDir))
            exclude("**/build/**")
            exclude { it.file.toPath().startsWith(layout.buildDirectory.get().asFile.toPath()) }
        }
        val detektBaselines by tasks.registering(DetektCreateBaselineTask::class) {
            description = "Overrides current baseline."
            buildUponDefaultConfig = true
            ignoreFailures = true
            parallel = true
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

private inline fun <reified T: com.android.build.api.dsl.CommonExtension<*,*,*,*,*,*>> Project.android(block: T.() -> Unit) {
    the<T>().apply(block)
}

private inline fun <reified T: org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension> Project.kotlin(block: T.() -> Unit) {
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