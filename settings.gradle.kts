pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}

rootProject.name = "session-timer"

include(":app")
include(":core:common")
include(":core:common-test")
include(":core:database")
include(":core:database-api")
include(":core:database-test")
include(":core:defaults")
include(":core:di")
include(":core:test")
include(":core:theme")
include(":core:timer")
include(":core:timer-api")
include(":core:timer-test")
include(":core:ui")
include(":core:usecases")
include(":core:usecases-api")
include(":core:usecases-test")
include(":feature:session-editor")
include(":feature:session-editor-api")
include(":feature:session-overview")
include(":feature:session-overview-api")
include(":feature:session-player")
include(":feature:session-player-api")
include(":feature:taskgroup-editor")
include(":feature:taskgroup-editor-api")
