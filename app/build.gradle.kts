import java.util.Properties

plugins {
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    id("com.android.application")
    id("st.kotlin")
    id("st.ktlint")
    kotlin("android")
}

val signingProperties = Properties()
val signingFile = file("keystore/sessiontimer.properties")
val hasSigningConfig = signingFile.exists()
if (hasSigningConfig) {
    signingProperties.load(signingFile.inputStream())
}

android {
    compileSdk = AppConfig.compileSdk
    namespace = AppConfig.applicationId

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    if (hasSigningConfig) {
        signingConfigs {
            create("signing") {
                enableV3Signing = true
                enableV4Signing = true
                storeFile = file(signingProperties.getProperty("signing.storeFilePath"))
                storePassword = signingProperties.getProperty("signing.storePassword")
                keyAlias = signingProperties.getProperty("signing.keyAlias")
                keyPassword = signingProperties.getProperty("signing.keyPassword")
            }
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            if (hasSigningConfig) {
                signingConfig = signingConfigs.getByName("signing")
            }
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_21
        sourceCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/*.kotlin_module"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    val implModules = listOf(
        ":core:common",
        ":core:database",
        ":core:database-api",
        ":core:defaults",
        ":core:di",
        ":core:test",
        ":core:theme",
        ":core:timer",
        ":core:timer-api",
        ":core:ui",
        ":core:usecases",
        ":core:usecases-api",
        ":feature:session-editor",
        ":feature:session-editor-api",
        ":feature:session-overview",
        ":feature:session-overview-api",
        ":feature:session-player",
        ":feature:session-player-api",
        ":feature:taskgroup-editor",
        ":feature:taskgroup-editor-api"
    )
    for (module in implModules) {
        implementation(project(module))
    }

    // Database
    implementation(libs.sqlDelight.android)

    // Android
    implementation(libs.androidX.activity)
    implementation(libs.androidX.activity.compose)
    implementation(libs.android.material)

    // Compose
    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.ui)
    implementation(libs.androidX.compose.ui.tooling.preview)
    implementation(libs.androidX.compose.material3)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Navigation
    implementation(libs.bundles.navigation)
}
