import java.util.*

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlinx-serialization")
}

val localProps = Properties()
val localProperties = File(rootProject.rootDir, "local.properties")
if (localProperties.exists() && localProperties.isFile) {
    localProperties.inputStream().use { input ->
        localProps.load(input)
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.tagetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val token = checkNotNull(localProps.getProperty("go.rest.token") ?: System.getenv("GO_REST_TOKEN"))
        buildConfigField("String", "GO_REST_TOKEN", "\"$token\"")
        buildConfigField("String", "GO_REST_URL", "\"https://gorest.co.in/public/v2/\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(projects.boundary)

    implementation(libs.inject)

    implementation(libs.androidx.annotation)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.retrofit2)

    testImplementation(libs.bundles.tests.unit)
}