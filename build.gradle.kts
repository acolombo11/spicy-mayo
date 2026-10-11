import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import dev.detekt.gradle.extensions.DetektExtension
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
    alias(libs.plugins.kover)
    alias(libs.plugins.roborazzi) apply false
}

val nameSpace by extra("eu.acolombo.work.calendar")
val javaVersion = libs.versions.java.get()

subprojects {
    // Deferred so the Android settings plugin (which hands out the SDK levels in `beforeProject`) runs first.
    beforeEvaluate {
        when {
            path.count { it == ':' } >= 2 -> {
                plugins()
                targets()
                tests()
                if (name == "presentation" || rootName == "design") compose()
                if (rootName in listOf("feature", "core")) koin()
                if (name == "presentation") {
                    composeFeature()
                    screenshots()
                }
                detekt()
            }

            name == "app" -> {
                plugins()
                targets(name)
                tests()
                koin()
                compose()
                composeFeature()
                detekt()
            }
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
        jvm()

        if (pluginManager.hasPlugin(rootProject.libs.plugins.android.kotlin.multiplatform.library.get().pluginId)) {
            extensions.configure<KotlinMultiplatformAndroidLibraryExtension> {
                namespace = nameSpace + path.replace(':', '.')
                androidResources.enable = true
            }
        }

        listOf(
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
                implementation(rootProject.libs.navigation3.ui)
            }
        }
    }
}

private fun Project.detekt() {
    plugins {
        alias(rootProject.libs.plugins.detekt)
    }

    the<DetektExtension>().apply {
        buildUponDefaultConfig = true
        config.setFrom(rootProject.file("config/detekt/detekt.yml"))
        // Findings of the code that predates detekt: new code has to be clean.
        baseline.set(file("detekt-baseline.xml"))
        source.setFrom(files("src").asFileTree.matching { include("**/*.kt") })
    }

    dependencies {
        add("detektPlugins", rootProject.libs.detekt.compose.rules)
    }
}

/** Kotest specs (`src/jvmTest`) and coverage: every module's report is merged into the root's `koverXmlReport`. */
private fun Project.tests() {
    plugins {
        alias(rootProject.libs.plugins.kover)
    }
    rootProject.dependencies.add("kover", this)

    kotlin<KotlinMultiplatformExtension> {
        sourceSets.getByName("jvmTest").dependencies {
            implementation(rootProject.libs.kotest.runner.junit5)
            implementation(rootProject.libs.kotest.assertions.core)
        }
    }

    tasks.withType<Test>().configureEach { useJUnitPlatform() }
}

/**
 * Roborazzi screenshots of every `@Preview` of the module: one shared test renders them on the desktop target and
 * records/verifies them (`./gradlew recordRoborazziJvm`).
 */
private fun Project.screenshots() {
    plugins {
        alias(rootProject.libs.plugins.roborazzi)
    }

    kotlin<KotlinMultiplatformExtension> {
        sourceSets.getByName("jvmTest") {
            kotlin.srcDir(rootProject.file("config/screenshot"))
            dependencies {
                implementation(rootProject.libs.compose.ui.test)
                implementation(rootProject.libs.composable.preview.scanner.jvm)
                implementation(rootProject.libs.junit)
                // The screenshot test is JUnit 4, the specs are Kotest: both run on the JUnit Platform.
                runtimeOnly(rootProject.libs.junit.vintage.engine)
                implementation(rootProject.libs.roborazzi.compose.desktop)
            }
        }
    }

    // The package of the module's sources (their common directory) holds the previews to render.
    tasks.withType<Test>().configureEach {
        val sources = fileTree("src/commonMain/kotlin").files.filter { it.extension == "kt" }
        val common = sources.map { it.parentFile.toPath() }.reduceOrNull { a, b ->
            var p = a
            while (!b.startsWith(p)) p = p.parent
            p
        }
        val root = file("src/commonMain/kotlin").toPath()
        systemProperty("screenshot.package", common?.let { root.relativize(it).joinToString(".") }.orEmpty())
    }
}

//region Project Extensions to write code in Project scope as if it was in module's build.gradle.kts
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
