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

include(":app")
include("feature:events:ui")
include("feature:events:data")
include("design:theme")
include("design:illustrations")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
