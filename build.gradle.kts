import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.8.0" apply false
    id("com.google.devtools.ksp") version "1.8.0-1.0.8" apply false
}

allprojects {
    group = "com.github.merlinths.kava"
    version = "1.0.3"

    repositories {
        mavenCentral()
    }
}

subprojects {
    println("In subprojets block!")

    tasks.withType<KotlinCompile>().configureEach {
        println("Configuring $name in ${project.name}")
    }
}