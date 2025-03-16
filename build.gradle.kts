import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

val nameSpace = "eu.acolombo.work.calendar"
val javaVersion = libs.versions.java.jdk.get()

allprojects {
    when {
        path.count { it == ':' } >= 2 -> {
            androidLibrary()
            targets()
            if (name in listOf("presentation", "theme", "illustrations")) compose()
            if (path.split(':')[1] in listOf("feature", "core")) koin()
            if (name == "presentation") composeFeature()
        }
        name == "app" -> {
            androidApplication()
            targets("SpicyMayoApp")
            composeApp()
            koin()
            composeFeature()
        }
    }
}

private fun Project.targets(targetName: String? = null) {
    plugins {
        alias(rootProject.libs.plugins.kotlin.multiplatform)
    }

    the<KotlinMultiplatformExtension>().apply {
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

    the<ApplicationExtension>().apply {
        namespace = nameSpace
        compileSdk = rootProject.libs.versions.android.maxSdk.get().toInt()
        defaultConfig {
            minSdk = rootProject.libs.versions.android.minSdk.get().toInt()
            targetSdk = rootProject.libs.versions.android.maxSdk.get().toInt()
            applicationId = nameSpace
        }

        sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
        sourceSets["main"].res.srcDirs("src/androidMain/res")
        sourceSets["main"].resources.srcDirs("src/commonMain/resources")

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

    the<LibraryExtension>().apply {
        namespace = nameSpace + path.replace(':','.')
        compileSdk = rootProject.libs.versions.android.maxSdk.get().toInt()
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

    the<KotlinMultiplatformExtension>().apply {
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

    the<LibraryExtension>().apply {
        sourceSets["main"].resources.srcDirs("src/commonMain/resources")
        sourceSets["main"].resources.srcDirs("src/commonMain/composeResources")
    }
}

private fun Project.koin() {
    the<KotlinMultiplatformExtension>().apply {
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

    the<KotlinMultiplatformExtension>().apply {
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

    the<KotlinMultiplatformExtension>().apply {
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

private fun Project.plugins(apply: ProjectPluginDependenciesSpecScope.() -> Unit) {
    apply(ProjectPluginDependenciesSpecScope(this))
}

class ProjectPluginDependenciesSpecScope(private val project: Project) {
    fun alias(provider: Provider<PluginDependency>) {
        project.apply(plugin = provider.get().pluginId)
    }
}