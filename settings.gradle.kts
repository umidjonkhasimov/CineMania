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

rootProject.name = "CineMania"
include(":app")
include(":core")
include(":core:ui")
include(":core:util")
include(":feature")
include(":feature:onboarding")
include(":core:datastore")
include(":feature:authentication")
include(":core:domain")
include(":core:data")
include(":feature:home")
include(":feature:search")
include(":feature:profile")
include(":feature:for_you")
include(":feature:details")
include(":feature:paginated_items")
