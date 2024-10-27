import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    jvmToolchain(libs.versions.module.jdk.get().toInt())
    androidTarget { compilerOptions { jvmTarget.set(JvmTarget.JVM_20) } }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.events.data)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }
    }
}

android {
    namespace = "eu.acolombo.work.calendar.events.domain"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
}
