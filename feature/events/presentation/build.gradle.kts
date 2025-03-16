kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.events.domain)
            implementation(projects.core.network) { because("Error mapping") }
            implementation(projects.design.illustrations)

            implementation(compose.materialIconsExtended)

            implementation(libs.kotlinx.datetime)
        }
    }
}