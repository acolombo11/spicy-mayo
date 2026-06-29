import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.kotlin.serialization)
}

val secretsGenerator by tasks.registering(Sync::class) {
    val secrets = Properties()
    secrets.load(project.rootProject.file("secrets.properties").inputStream())

    val spicyApiKey: String by secrets
    val spicyDeployId: String by secrets

    from(
        resources.text.fromString(
            """
            package eu.acolombo.work.calendar.events.data

            object Secrets {
                internal const val ApiKey = "$spicyApiKey"
                internal const val DeployId = "$spicyDeployId"
            }
            """.trimIndent(),
        ),
    ) {
        rename { "Secrets.kt" }
        into("eu/acolombo/work/calendar/events/data/")
    }
    into(layout.buildDirectory.dir("generated-src/kotlin"))
}

kotlin {
    sourceSets {
        commonMain {
            kotlin.srcDir(secretsGenerator.map { it.destinationDir })
        }
        commonMain.dependencies {
            implementation(projects.core.network)
            implementation(projects.core.storage)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization)
        }
    }
}
