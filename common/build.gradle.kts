plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
    `maven-publish`
}

version = "1.0.1"

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

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.Companion.S01, true)
    signAllPublications()

    coordinates("io.github.merlinths", "kava-common", version.toString())
    pom {
        name.set("Kava Common")
        description.set("Common interfaces shared by the other kava modules.")
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