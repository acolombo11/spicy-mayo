kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.datastore)
            api(libs.androidx.datastore.preferences)
        }

        androidMain.dependencies {
            implementation(libs.androidx.datastore.preferences)
        }
    }
}
