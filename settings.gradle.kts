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
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
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
include(":feature:mypage")
include(":feature:profile-edit")
include(":feature:myroutine")
include(":feature:mymate")
include(":feature:info")
include(":feature:calendar")
include(":core:common")
include(":core:datastore")
