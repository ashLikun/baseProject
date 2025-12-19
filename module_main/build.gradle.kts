plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}
val sdk = rootProject.extra["sdk"] as Map<*, *>  // SDK配置
val config = rootProject.extra["android"] as Map<*, *>  // 工程配置
val libs = rootProject.extra["dependencies"] as Map<String, String> // 库依赖
val modelName = "main"
android {
    namespace = "com.ashlikun.baseproject.module.${modelName}"
    compileSdk = config["compileSdk"] as Int
    defaultConfig {
        minSdk = config["minSdk"] as Int
        multiDexEnabled = true


        // 强制资源前缀
        resourcePrefix = "${modelName}_"
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
    /**
     * java插件引入了一个概念叫做SourceSets 通过修改SourceSets中的属性
     * 可以指定哪些源文件（或文件夹下的源文件）要被编译，哪些源文件要被排除。
     */
    sourceSets {
        getByName("main") {

        }
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
    implementation(project(":component_common"))
    ksp(libs["aroutercompiler"]!!)
    ksp(libs["glideCompiler"]!!)
}
