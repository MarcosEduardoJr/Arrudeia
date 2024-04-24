pluginManagement {
    repositories {
        includeBuild("build-logic")
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
        maven ("jitpack.io")
    }
}

rootProject.name = "arrudeia"
include(":app")
include(":core:common")
include(":core:graphql")
include(":core:designsystem")
include(":core:domain")
include(":core:graphql")


include(":feature:onboarding")
include(":feature:sign")
include(":feature:home")
include(":feature:trip")
include(":feature:stories")
include(":feature:profile")
include(":feature:arrudeia")
