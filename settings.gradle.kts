pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Yakssok"
include(":app")

include(":core:domain")
include(":core:model")
include(":core:data")
include(":core:network")
include(":core:designsystem")
include(":core:navigation")
include(":core:ui")

include(":feature:main")
include(":feature:home")
include(":feature:intro")
include(":feature:routine")
include(":feature:alert")
include(":feature:mate")
