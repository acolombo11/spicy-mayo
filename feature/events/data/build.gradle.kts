import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvmToolchain(libs.versions.module.jvmToolchain.get().toInt())
    androidTarget { compilerOptions { jvmTarget.set(JvmTarget.JVM_20) } }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)

            api(libs.koin.core)
        }
    }
}

android {
    namespace = "eu.acolombo.work.calendar.events.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
}

val secretsGenerator by tasks.registering(Sync::class) {
    val secrets = Properties()
    secrets.load(project.rootProject.file("secrets.properties").inputStream())

    val spicyApiKey: String by secrets
    val spicyDeployId: String by secrets

    from(
        resources.text.fromString(
            """
        |package eu.acolombo.work.calendar.events.data
        |
        |object Secrets {
        |  internal const val ApiKey = "$spicyApiKey"
        |  internal const val DeployId = "$spicyDeployId"
        |}
            """.trimMargin()
        )
    ) {
        rename { "Secrets.kt" }
        into("eu/acolombo/work/calendar/events/data/")
    }
    into(layout.buildDirectory.dir("generated-src/kotlin"))
}

kotlin {
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir(
                secretsGenerator.map { it.destinationDir }
            )
        }
    }
}