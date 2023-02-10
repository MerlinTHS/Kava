import io.mths.kava.plugins.publish.kavaPublishing

plugins {
    kotlin("multiplatform")
    id("io.mths.kava.publishing")
}

kotlin {
    jvm {}

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kava.common)
                implementation(libs.kava.annotations)
                implementation(libs.ksp)

                implementation(libs.kotlinpoet)
                implementation(libs.kotlinpoet.ksp)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.bundles.kava)

                implementation(kotlin("test"))
                implementation(testLibs.strikt)
                implementation(testLibs.compile.ksp)
            }
        }
    }
}

kavaPublishing(
    name = "Kava Annotation Processor",
    description = "The KSP Kava Annotation Processor to generate validation extensions.",
    artifactId = "kava-processor",
    version = libs.versions.kava.processor.get()
)