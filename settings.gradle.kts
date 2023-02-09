rootProject.name = "Kava"

include("common")
include("core")
include("annotations")
include("processor")
include("demo")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}