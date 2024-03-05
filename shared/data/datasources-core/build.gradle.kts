plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.com.google.ksp)
    alias(libs.plugins.ktorfit)
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

    dependencies {
        add("kspCommonMainMetadata", libs.ktorfit.ksp)
        add("kspAndroid", libs.ktorfit.ksp)
        add("kspIosArm64", libs.ktorfit.ksp)
        add("kspIosX64", libs.ktorfit.ksp)
        add("kspIosSimulatorArm64", libs.ktorfit.ksp)
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

sqldelight {
    databases {
        create(BuildVersion.environment.appDatabaseName) {
            // Database configuration here.
            // https://cashapp.github.io/sqldelight
            packageName.set("${BuildVersion.environment.applicationId}.data.datasources.core.db")
        }
    }
}

tasks.register("testClasses") {
    println("Dummy classes")
}
