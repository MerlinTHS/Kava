plugins {
    kotlin("multiplatform")
    `maven-publish`
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