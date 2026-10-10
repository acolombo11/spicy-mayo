rootProject.name = "spicy-mayo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    plugins {
        id("com.android.settings") version "9.4.1" apply false
    }
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("com.android.settings")
}

android {
    compileSdk {
        version = release(37) { minorApiLevel = 1 }
    }
    minSdk = 26
    targetSdk = 37
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":app")
include(":appAndroid")

include("core:storage")
include("core:network")

include("feature:events:data")
include("feature:events:domain")
include("feature:events:presentation")

include("design:theme")
include("design:illustrations")