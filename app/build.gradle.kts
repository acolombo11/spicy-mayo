plugins {
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.network)
            implementation(projects.core.storage)
            implementation(projects.feature.events.domain)
            implementation(projects.feature.events.presentation)

            implementation(libs.koin.compose.viewmodel.navigation)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
        }
    }
}
