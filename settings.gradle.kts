pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "MyDictionary"
include (":app")
include(":CoreAndroid")
include(":CoreCompose")
include(":WordGroupScreen")
include(":CreateWordGroupScreen")
include(":Database")
include(":ViewGroupWordsScreen")
include(":EditWordScreen")
include(":TTSManager")
