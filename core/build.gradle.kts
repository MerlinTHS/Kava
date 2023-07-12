import io.mths.kava.plugins.publish.kavaPublishing

plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
    id("io.mths.kava.publishing")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-Xcontext-receivers")
            }
        }

        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        val jvmMain by getting {
            kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin")

            dependencies {
                implementation(libs.kava.annotations)
                api(libs.kava.common)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))

                implementation(libs.kava.annotations)
                implementation(libs.kava.common)
            }
        }
    }
}

ksp {
    arg("Kava.SeparateFiles", "true")
}

dependencies {
    add("kspJvm", libs.kava.processor)
    add("kspJvmTest", libs.kava.processor)

    //add("kspJvm", project(":processor"))
    //add("kspJvmTest", project(":processor"))
}

kavaPublishing(
    name = "Kava Core",
    description = "Core kava functionalities.",
    artifactId = "kava-core",
    version = libs.versions.kava.core.get()
)