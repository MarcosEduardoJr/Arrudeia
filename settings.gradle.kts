pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            google()
            mavenCentral()
        }
    }
}

rootProject.name = "Arrudeia"
include(":app")
include(":core:designsystem")
include(":core:common")
include(":core:graphql")

include(":feature:onboarding")
include(":feature:sign")
include(":feature:home")
include(":feature:trip")
//include(":feature:stories")
include(":feature:profile")
//include(":feature:arrudeia")
include(":feature:checklist")
include(":feature:receipt")
include(":feature:aid")
include(":feature:services")
include(":feature:trail")
include(":feature:social")
include(":feature:tips")


include(":wear:app")
include(":wear:run:data")
include(":wear:run:domain")
include(":wear:run:presentation")
include(":core:designsystem_wear")
