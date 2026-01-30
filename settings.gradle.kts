pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "StartupHub"
include(":app")
include(":core:navigation")
include(":core:designsystem")
include(":feature:home_api")
include(":feature:home")
include(":core:ui")
include(":feature:profile_api")
include(":feature:profile")
include(":feature:auth_api")
include(":feature:auth")
include(":data:schema")
include(":data:startups")
include(":data:auth")
include(":core:network")
