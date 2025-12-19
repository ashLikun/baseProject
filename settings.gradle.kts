pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/central")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/google/")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin/")
        }
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/central")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/google/")
        }
        maven {
            url = uri("https://maven.aliyun.com/repository/gradle-plugin/")
        }
        maven {
            url = uri("https://www.jitpack.io")
        }

        flatDir {
            dirs("libs", "./app/libsAndAar", "../vinkaCommonModules/component_ble/lib")
        }
        mavenCentral()
        google()
    }
}
rootProject.name = "baseproject"

include(":app")
include(":module_main")
include(":module_user")
include(":module_other")
include(":component_common")
include(":component_core")
include(":module_login")
