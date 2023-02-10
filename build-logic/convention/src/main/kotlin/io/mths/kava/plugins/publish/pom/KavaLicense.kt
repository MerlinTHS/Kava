package io.mths.kava.plugins.publish.pom

import org.gradle.api.publish.maven.MavenPom

internal fun MavenPom.kavaLicense() {
    licenses {
        it.license { pomLicense ->
            with(pomLicense) {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
    }
}