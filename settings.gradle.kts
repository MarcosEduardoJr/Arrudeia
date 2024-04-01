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
include(":core:data")
include(":core:database")
include(":core:designsystem")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:ui")
include(":core:analytics")
include(":core:notifications")

include(":sync:work")

include(":feature:onboarding")
include(":feature:sign")
include(":feature:home")
include(":feature:trip")
include(":feature:stories")
include(":sync:work")
include(":sync:sync-test")
include(":feature:profile")
include(":feature:arrudeia")
