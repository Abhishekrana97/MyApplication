pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")

        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")

        mavenCentral()
    }
}

rootProject.name = "MyApplication"
include(":app")
