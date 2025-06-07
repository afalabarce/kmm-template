import de.jensklingenberg.ktorfit.gradle.ErrorCheckingMode

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.com.google.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.room)
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
            baseName = "DataDatasourcesCore"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.core.common)
            implementation(projects.shared.data.models)
            implementation(projects.shared.data.datasources)

            implementation(libs.bundles.layer.data.datasources.core)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.bundles.android.data.core)
        }

        iosMain.dependencies {
            implementation(libs.bundles.ios.data.core)
        }

    }
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)

    configurations.all {
        exclude(group = "com.intellij", module = "annotations")
    }
}

android {
    namespace = "${BuildVersion.environment.applicationId}.data.datasources.core"
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

buildConfig {
// BuildConfig configuration here.
// https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

ktorfit {
    errorCheckingMode = ErrorCheckingMode.ERROR
    generateQualifiedTypeName = true
}

room {
    schemaDirectory("$projectDir/schemas")
}

tasks.register("testClasses") {
    println("Dummy classes")
}
