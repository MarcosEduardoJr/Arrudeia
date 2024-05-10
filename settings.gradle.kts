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
include(":feature:stories")
include(":feature:profile")
include(":feature:arrudeia")

