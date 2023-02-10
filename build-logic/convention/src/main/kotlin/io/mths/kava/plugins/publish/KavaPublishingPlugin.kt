package io.mths.kava.plugins.publish

import org.gradle.api.Plugin
import org.gradle.api.Project

class KavaPublishingPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.vanniktech.maven.publish")
    }
}