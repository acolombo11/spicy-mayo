kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.events.data)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
        }
    }
}
