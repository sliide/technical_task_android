buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.gradle.tools)
        classpath(libs.kotlin.gradle)
        classpath(kotlin("serialization", version = libs.versions.kotlin.get()))
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}