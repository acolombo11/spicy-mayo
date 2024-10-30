import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    jvmToolchain(libs.versions.module.jvmToolchain.get().toInt())
    androidTarget { compilerOptions { jvmTarget.set(JvmTarget.JVM_20) } }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)

            api(libs.androidx.datastore)
            api(libs.androidx.datastore.preferences)
        }

        androidMain.dependencies {
            implementation(libs.androidx.datastore.preferences)
        }
    }
}

android {
    namespace = "eu.acolombo.work.calendar.storage"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
}
