package io.mths.kava.plugins.publish.pom

import com.vanniktech.maven.publish.MavenPublishBaseExtension

fun MavenPublishBaseExtension.kavaPom(
    name: String,
    description: String
) = pom { mavenPom ->
    mavenPom.name.set(name)
    mavenPom.description.set(description)

    with(mavenPom) {
        inceptionYear.set("2023")
        url.set("https://github.com/merlinths/kava")

        kavaLicense()
        kavaDevelopers()
        kavaSourceControlManagement()
    }
}
