package io.mths.kava.plugins.publish.pom

import org.gradle.api.publish.maven.MavenPom

internal fun MavenPom.kavaSourceControlManagement() = scm { scm ->
    with(scm) {
        url.set("https://github.com/MerlinTHS/Kava/")
        connection.set("scm:git:git://github.com/merlinths/kava.git")
        developerConnection.set("scm:git:ssh://git@github.com/merlinths/kava.git")
    }
}
