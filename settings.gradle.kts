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
      //  maven(){ url = uri("https://jitpack.io") }


    }
}

rootProject.name = "chapter 14 Note Keeping App"
include(":app")
 