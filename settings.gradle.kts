@file:Incubating

rootProject.name = "KMMTemplate"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":androidApp")
include(":shared:presentation:ui")
include(":shared:presentation:viewmodels")
include(":shared:core:common")
include(":shared:core:di")
include(":shared:core:viewmodels")
include(":shared:core:ui")
include(":shared:domain:usecases")
include(":shared:domain:models")
include(":shared:domain:repository")
include(":shared:data:repository")
include(":shared:data:models")
include(":shared:data:datasources")
include(":shared:data:datasources-core")