plugins {
    kotlin("multiplatform")
    `maven-publish`
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
        val commonMain by getting {
            dependencies {
                implementation(project(":common"))
                implementation(project(":annotations"))

                // When published to MavenCentral
                //implementation("io.github.merlinths:kava-common:1.0.1")
                //implementation("io.github.merlinths:kava-annotations:1.0.2")
                implementation("com.google.devtools.ksp:symbol-processing-api:1.8.0-1.0.8")

                implementation("com.squareup:kotlinpoet-ksp:1.12.0")
                implementation("com.squareup:kotlinpoet:1.12.0")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(":core"))
                implementation(project(":annotations"))

                // When published to MavenCentral
                //implementation("io.github.merlinths:kava-common:1.0.1")
                //implementation("io.github.merlinths:kava-annotations:1.0.2")

                implementation(kotlin("test"))
                implementation("io.strikt:strikt-core:0.34.0")
                implementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.4.9")
            }
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "Processor"
            version = project.version.toString()

            from(components["java"])
        }
    }
}