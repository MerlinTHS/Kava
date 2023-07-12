plugins {
    kotlin("multiplatform") version "1.8.20-Beta" apply false
    id("com.google.devtools.ksp") version "1.8.20-Beta-1.0.9" apply false
    id("com.vanniktech.maven.publish") version "0.24.0" apply false
}

allprojects {
    group = "io.github.merlinths"
    version = "1.0.0"
}