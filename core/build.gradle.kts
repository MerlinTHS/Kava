import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp")
    `maven-publish`
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.apply {
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
            dependencies {
                implementation(project(":common"))
                implementation(project(":annotations"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

dependencies {
    add("kspJvm", project(":processor"))
    add("kspJvmTest", project(":processor"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "Core"
            version = project.version.toString()

            from(components["java"])
        }
    }
}
