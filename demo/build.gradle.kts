import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir("build/generated/ksp/main/kotlin")
        }

        test {
            kotlin.srcDir("build/generated/ksp/test/kotlin")
        }
    }
}

dependencies {
    //add("ksp", project(":processor"))
    //add("kspTest", project(":processor"))

    add("ksp", "io.github.merlinths:kava-processor:1.0.0")
    add("kspTest", "io.github.merlinths:kava-processor:1.0.0")

    implementation(project(":core"))
    //implementation("io.github.merlinths:kava-core:1.0.0")
    implementation("io.github.merlinths:kava-annotations:1.0.3")
}

tasks.withType(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}

ksp {
    arg("Kava.SeparateFiles", "true")
}