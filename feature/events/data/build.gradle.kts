import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.kotlin.serialization)
}

abstract class GenerateSecretsTask : DefaultTask() {
    @get:InputFile
    abstract val secretsFile: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun generate() {
        val secrets = Properties()
        secrets.load(secretsFile.get().asFile.inputStream())

        val packageDir = outputDirectory.get().asFile.resolve("eu/acolombo/work/calendar/events/data")
        packageDir.mkdirs()
        packageDir.resolve("Secrets.kt").writeText(
            """
            package eu.acolombo.work.calendar.events.data

            object Secrets {
                internal const val ApiKey = "${secrets.getProperty("spicyApiKey")}"
                internal const val DeployId = "${secrets.getProperty("spicyDeployId")}"
            }
            """.trimIndent(),
        )
    }
}

val secretsGenerator = tasks.register<GenerateSecretsTask>("secretsGenerator") {
    secretsFile = rootProject.file("secrets.properties")
    outputDirectory = layout.buildDirectory.dir("generated-src/kotlin")
}

kotlin {
    sourceSets {
        commonMain {
            kotlin.srcDir(secretsGenerator.flatMap { it.outputDirectory })
        }
        commonMain.dependencies {
            implementation(projects.core.network)
            implementation(projects.core.storage)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization)
        }
    }
}
