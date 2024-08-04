plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.com.google.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = BuildVersion.environment.jvmTarget
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "PresentationUi"
            isStatic = true

        }
    }

    sourceSets {
        applyDefaultHierarchyTemplate()

        androidMain {
            dependencies {
                implementation(compose.uiTooling)
                implementation(compose.preview)

                implementation(libs.bundles.android.core.ui)
                implementation(libs.ktor.client.okhttp)
            }
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.bundles.layer.core.ui)
            implementation(compose.components.resources)

            implementation(projects.shared.core.ui)
            implementation(projects.shared.core.common)
            implementation(projects.shared.core.di)
            implementation(projects.shared.presentation.viewmodels)
            implementation(projects.shared.domain.models)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        iosMain.dependencies {

        }

        iosTest.dependencies {

        }
    }
}

android {
    namespace = "${BuildVersion.environment.applicationId}.presentation.ui"
    compileSdk = BuildVersion.android.compileSdk

    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

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
    buildFeatures {
        compose = true
    }

    dependencies {
        debugImplementation(compose.uiTooling)
        debugImplementation(compose.preview)
    }
}

buildConfig {
// BuildConfig configuration here.
// https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

tasks.register("testClasses") {
    println("Dummy classes")
}
