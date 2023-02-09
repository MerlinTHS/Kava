plugins {
    kotlin("multiplatform")
    id("com.vanniktech.maven.publish")
    `maven-publish`
}

version = "1.0.2"

kotlin {
    jvm()
    js()
    ios()
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.Companion.S01, true)
    signAllPublications()

    coordinates("io.github.merlinths", "kava-annotations", version.toString())
    pom {
        name.set("Kava Annotations")
        description.set("Annotations for custom validators")
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