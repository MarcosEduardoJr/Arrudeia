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
include(":core:data-test")
include(":core:database")
include(":core:datastore")
include(":core:datastore-test")
include(":core:designsystem")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:ui")
include(":core:testing")
include(":core:analytics")
include(":core:notifications")

include(":lint")


include(":sync:work")


include(":feature:onboarding")
include(":feature:sign")
include(":feature:home")
include(":feature:trip")
include(":feature:stories")
include(":lint")
include(":sync:work")
include(":sync:sync-test")
//include(":ui-test-hilt-manifest")