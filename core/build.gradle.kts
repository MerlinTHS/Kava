plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
    id("com.vanniktech.maven.publish") version "0.24.0"
    `maven-publish`
}

version = "1.0.0"

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
                implementation(project(":annotations"))
                api(project(":common"))

                // When published to MavenCentral
                //implementation("io.github.merlinths:kava-annotations:1.0.2")
                //api("io.github.merlinths:kava-common:1.0.1")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))

                implementation(project(":annotations"))
                implementation(project(":common"))

                // When published to MavenCentral
                //implementation("io.github.merlinths:kava-annotations:1.0.2")
                //api("io.github.merlinths:kava-common:1.0.1")
            }
        }
    }
}

ksp {
    arg("Kava.SeparateFiles", "true")
}

dependencies {
    add("kspJvm", project(":processor"))
    add("kspJvmTest", project(":processor"))
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.Companion.S01, true)
    signAllPublications()

    coordinates("io.github.merlinths", "kava", version.toString())
    pom {
        name.set("Kava")
        description.set("Core kava functionalities.")
        inceptionYear.set("2023")
        url.set("https://github.com/merlinths/kava")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("merlinths")
                name.set("MerlinTHS")
                url.set("https://github.com/MerlinTHS/")
            }
        }

        scm {
            url.set("https://github.com/MerlinTHS/Kava/")
            connection.set("scm:git:git://github.com/merlinths/kava.git")
            developerConnection.set("scm:git:ssh://git@github.com/merlinths/kava.git")
        }
    }
}