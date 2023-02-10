import io.mths.kava.plugins.publish.kavaPublishing

plugins {
    kotlin("multiplatform")
    id("io.mths.kava.publishing")
}

kotlin {
    jvm()
    js()
    ios()
}

kavaPublishing(
    name = "Kava Annotations",
    description = "Annotations to generate extensions for custom validators",
    artifactId = "kava-annotations",
    version = libs.versions.kava.annotations.get()
)