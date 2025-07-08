import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.com.google.ksp)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "core.viewmodels"
            isStatic = true
        }
    }


    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.common)
            implementation(libs.bundles.layer.core.viewmodels)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.bundles.android.core)
        }

        iosMain.dependencies {

        }
    }
}

android {
    namespace = "${BuildVersion.environment.applicationId}.core.viewmodels"
    compileSdk = BuildVersion.android.compileSdk
    defaultConfig {
        minSdk = BuildVersion.android.minSdk
    }

    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
    }
    compileOptions {
        sourceCompatibility = BuildVersion.environment.javaVersion
        targetCompatibility = BuildVersion.environment.javaVersion
    }
}

tasks.register("testClasses") {
    println("Dummy classes")
}
