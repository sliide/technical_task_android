plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        versionCode = 1
        versionName = "1.0"

        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.tagetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            packagingOptions { resources.excludes += "DebugProbesKt.bin" }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions { kotlinCompilerExtensionVersion = libs.versions.compose.get() }

    packagingOptions {
        resources {
            excludes.add("META-INF/AL2.0")
            excludes.add("META-INF/LGPL2.1")
            excludes.add("META-INF/INDEX.LIST")
            excludes.add("META-INF/DEPENDENCIES")
        }
    }
}

dependencies {
    implementation(projects.presentation)
    implementation(projects.interactor)
    implementation(projects.domain)
    implementation(projects.boundary)
    implementation(projects.data)

    implementation(libs.google.accompanist.insets)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.androidx.compose)

    debugImplementation(libs.leak.canary)
}