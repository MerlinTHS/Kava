plugins {
    kotlin("multiplatform") version "1.8.0" apply false
    id("com.google.devtools.ksp") version "1.8.0-1.0.8" apply false
    id("com.vanniktech.maven.publish") version "0.24.0" apply false
}

allprojects {
    group = "io.github.merlinths"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}