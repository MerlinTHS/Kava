plugins {
    kotlin("jvm") version "1.8.0"
    id("java-gradle-plugin")
}

group = "io.mths.kava.convention"

dependencies {
    implementation("com.vanniktech.maven.publish.base:com.vanniktech.maven.publish.base.gradle.plugin:0.24.0")
    implementation(gradleKotlinDsl())
}

gradlePlugin {
    plugins {
        register("kavaPublishing") {
            id = "io.mths.kava.publishing"
            implementationClass = "io.mths.kava.plugins.publish.KavaPublishingPlugin"
        }
    }
}