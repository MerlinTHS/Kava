import io.mths.kava.plugins.publish.kavaPublishing

plugins {
    kotlin("multiplatform")
    id("io.mths.kava.publishing")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }

        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
}

kavaPublishing(
    name = "Kava Common",
    description = "Common interfaces shared by the other kava modules.",
    artifactId = "kava-common",
    version = libs.versions.kava.common.get()
)