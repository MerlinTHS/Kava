package io.mths.kava.plugins.publish.pom

import org.gradle.api.publish.maven.MavenPom

internal fun MavenPom.kavaDevelopers() {
    developers {
        it.developer { developer ->
            developer.id.set("merlinths")
            developer.name.set("MerlinTHS")
            developer.url.set("https://github.com/MerlinTHS/")
        }
    }
}