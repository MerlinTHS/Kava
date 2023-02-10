package io.mths.kava.plugins.publish

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import io.mths.kava.plugins.publish.pom.kavaLicense
import io.mths.kava.plugins.publish.pom.kavaPom
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.kavaPublishing(
    name: String,
    description: String,
    artifactId: String,
    version: String,
) {
    extensions.configure<MavenPublishBaseExtension> {
        publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.Companion.S01, true)
        signAllPublications()

        coordinates(groupId = "io.github.merlinths", artifactId, version)
        kavaPom(name, description)
    }
}