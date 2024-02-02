pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "spicy-mayo"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include("feature:events:screen")
include("feature:events:data")
include("design:theme")
include("design:illustrations")
