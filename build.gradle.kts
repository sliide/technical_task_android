buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle.tools)
        classpath(libs.kotlin.gradle)
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}