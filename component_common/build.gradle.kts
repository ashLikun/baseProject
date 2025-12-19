plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}
val sdk = rootProject.extra["sdk"] as Map<*, *>  // SDK配置
val config = rootProject.extra["android"] as Map<*, *>  // 工程配置
val libs = rootProject.extra["dependencies"] as Map<String, String> // 库依赖

android {
    namespace = "com.ashlikun.baseproject.common"
    compileSdk = config["compileSdk"] as Int

    defaultConfig {
        minSdk = config["minSdk"] as Int
        //向清单文件注入参数
        manifestPlaceholders.putAll(sdk["default"] as Map<String, Any>)
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            // 保存appid，因为当前模块本来的appid是清单文件的包名
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }

        create("beta") {
            // beta配置继承自release
            initWith(getByName("release"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        // ksp 插件
        all {
            java.srcDir("build/generated/ksp/${name}/kotlin/")
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = false
    }

    flavorDimensions.addAll(listOf(config["flavorDimensions"] as String))

}
ksp {
    arg("AROUTER_MODULE_NAME", project.name)
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // alibaba的路由框架
    implementation(libs["arouterapi"]!!)

    // 公共模块引用核心模块，其他模块只需引用本模块就可以
    api(project(":component_core"))

    ksp(libs["aroutercompiler"]!!)
    ksp(libs["glideCompiler"]!!)
}