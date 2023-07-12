rootProject.name = "Kava"
include(":common")
include(":core")
include(":annotations")
include(":processor")
include(":demo")

pluginManagement {
    includeBuild("build-logic")
}

dependencyResolutionManagement {
    setupRepositories()

    versionCatalogs {
        create("libs") {
            kava()
            kotlinPoet()
            ksp()
        }

        create("testLibs") {
            kotlinCompileTesting()
            strikt()
        }
    }
}

fun DependencyResolutionManagement.setupRepositories() {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}

fun VersionCatalogBuilder.kotlinCompileTesting() {
    library("compile-ksp", "com.github.tschuchortdev", "kotlin-compile-testing-ksp")
        .version("1.4.9")
}

fun VersionCatalogBuilder.strikt() {
    library("strikt", "io.strikt", "strikt-core")
        .version("0.34.0")
}

fun VersionCatalogBuilder.ksp() {
    val version = version("ksp", "1.8.0-1.0.8")

    library("ksp", "com.google.devtools.ksp", "symbol-processing-api")
        .versionRef(version)
}

fun VersionCatalogBuilder.kotlinPoet() {
    val version = version("kotlinpoet", "1.12.0")

    library("kotlinpoet", "com.squareup", "kotlinpoet")
        .versionRef(version)
    library("kotlinpoet-ksp", "com.squareup", "kotlinpoet-ksp")
        .versionRef(version)
}

fun VersionCatalogBuilder.kava() {
    kavaVersions()
    kavaLibraries()

    bundle("kava", listOf("kava-core", "kava-annotations"))
}

fun VersionCatalogBuilder.kavaVersions() {
    version("kava-annotations", "1.0.4")
    version("kava-common", "1.0.2")
    version("kava-processor", "1.0.1")
    version("kava-core", "1.0.0")
}

fun VersionCatalogBuilder.kavaLibraries() {
    library("kava-annotations", "io.github.merlinths", "kava-annotations")
        .versionRef("kava-annotations")
    library("kava-common", "io.github.merlinths", "kava-common")
        .versionRef("kava-common")
    library("kava-processor", "io.github.merlinths", "kava-processor")
        .versionRef("kava-processor")
    library("kava-core", "io.github.merlinths", "kava-core")
        .versionRef("kava-core")
}