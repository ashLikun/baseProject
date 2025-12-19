import com.android.build.api.dsl.VariantDimension
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties
import java.io.FileInputStream
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

plugins {
    id("com.android.application")
    // 使用Kotlin插件
    id("kotlin-android")
    // ksp 插件
    id("com.google.devtools.ksp")
}

val config = rootProject.extra["android"] as Map<*, *> // 工程配置
val sdk = rootProject.extra["sdk"] as Map<*, *> // SDK配置
val libs = rootProject.extra["dependencies"] as Map<*, *> // 库依赖


// 上传蒲公英
apply(from = "./upLoadPgy.gradle")

// 滴滴开发助手插件,打开后每次升级其他第三方库，总是报类冲突
// apply(from = "../dokit.gradle")

fun releaseTime(): String {
    return SimpleDateFormat("yyyy-MM-dd").format(Date())
}

android {
    configureSigning(android)
    namespace = "com.ashlikun.baseproject"
    compileSdk = config["compileSdk"] as Int

    defaultConfig {
        applicationId = config["applicationId"] as String
        minSdk = config["minSdk"] as Int
        targetSdk = config["targetSdk"] as Int
        versionCode = config["versionCode"] as Int
        versionName = config["versionName"] as String
        signingConfig = signingConfigs.getByName("release")

        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        // 适配默认语言"en-rUS"
        resourceConfigurations.addAll(listOf("zh-rCN"))
        multiDexEnabled = true


        // 向清单文件注入参数
        manifestPlaceholders.putAll(sdk["default"] as Map<String, Any>)

        // 注入string，如果在代码中使用，就定义一个同key的string，这里会替换
        configureStringResources(this, "default")
    }

    buildTypes {
        create("beta") {
            // 构建的时候打印文本
            println("build - beta")

            // 注入string，如果在代码中使用，就定义一个同key的string，这里会替换
            versionNameSuffix = "-beta"

            // applicationIdSuffix- Beta版本
            // applicationIdSuffix = "beta"

            // 这里配置打包不同的参数
            manifestPlaceholders.putAll(sdk["betaBuild"] as Map<String, Any>)
            configureStringResources(this, "betaBuild")

            // app名称
            // resValue("string", "my_app_name", "@string/my_app_name_beta")

            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }

        getByName("release") {
            // 自定义属性 BuildConfig.LOG_DEBUG
            // buildConfigField("boolean", "LOG_DEBUG", "false")

            // 构建的时候打印文本
            println("build - release")

            isDebuggable = false
            // 设置是否混淆
            isMinifyEnabled = true
            // 移除无用的资源文件
            isShrinkResources = true
            // 设置混淆配置文件
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )

            // 如果签名配置不为空则设置签名信息
            signingConfig = signingConfigs.getByName("release")

            // 这里配置打包不同的参数
            manifestPlaceholders.putAll(sdk["releasBuild"] as Map<String, Any>)

            // 注入string，如果在代码中使用，就定义一个同key的string，这里会替换
            configureStringResources(this, "releasBuild")
        }

        getByName("debug") {
            // 自定义属性 BuildConfig.LOG_DEBUG
            // buildConfigField("boolean", "LOG_DEBUG", "false")

            // 构建的时候打印文本
            println("build - debug")

            isDebuggable = true
            // 设置是否混淆
            isMinifyEnabled = false
            // 移除无用的资源文件
            isShrinkResources = false
            // 设置混淆配置文件
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )

            // versionNameSuffix
            versionNameSuffix = "-debug"

            // applicationIdSuffix-测试版本使用debug作为后缀
            // applicationIdSuffix = "beta"

            // 这里配置打包不同的参数
            manifestPlaceholders.putAll(sdk["debugBuild"] as Map<String, Any>)

            // 注入string，如果在代码中使用，就定义一个同key的string，这里会替换
            configureStringResources(this, "debugBuild")
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    /**
     * 指定签名包得输入文件名称
     * ApplicationVariantImpl变量访问地址
     * http://dcow.io/android-gradle-plugin-docs/index.html?overview-summary.html
     *
     * https://developer.android.google.cn/studio/build/build-variants?hl=zh-cn#groovy
     */
    /**
     * 指定签名包得输入文件名称
     */
    applicationVariants.all {
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                outputFileName = if (buildType.isDebuggable) {
                    "App-v${versionName}-${releaseTime()}.apk"
                } else {
                    "App-v${versionName}-${releaseTime()}.apk"
                }
            }
        }
    }

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }

    sourceSets {
        getByName("main") {
            jniLibs.srcDirs("libs")
        }
        all {
            java.srcDir("build/generated/ksp/${name}/kotlin/")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}
// 注入string，如果在代码中使用，就定义一个同key的string，这里会替换
fun configureStringResources(obj: VariantDimension, sdkKey: String) {
    (sdk[sdkKey] as? Map<String, Any>)?.forEach { (key, value) ->
        obj.resValue("string", key, value.toString())
    }
}

//配置签名
fun configureSigning(android: BaseAppModuleExtension) {
    android.signingConfigs {
        create("release") {}
        val release = getByName("release")
        val debug = getByName("debug")
        val propFile = project.rootProject.file("local.properties")
        if (propFile.exists() && propFile.canRead()) {
            val props = Properties().apply { load(FileInputStream(propFile)) }
            if (props.containsKey("RELEASE_STORE_FILE") && props.containsKey("RELEASE_STORE_PASSWORD") && props.containsKey("RELEASE_KEY_ALIAS") && props.containsKey("RELEASE_KEY_PASSWORD")) {
                release.storeFile = project.rootProject.file(props.getProperty("RELEASE_STORE_FILE"))
                release.storePassword = props.getProperty("RELEASE_STORE_PASSWORD")
                release.keyAlias = props.getProperty("RELEASE_KEY_ALIAS")
                release.keyPassword = props.getProperty("RELEASE_KEY_PASSWORD")
            } else {
                println("Release signing properties missing, signing disabled for release")
            }
            if (props.containsKey("DEBUG_STORE_FILE") && props.containsKey("RELEASE_STORE_PASSWORD") && props.containsKey("RELEASE_KEY_ALIAS") && props.containsKey("RELEASE_KEY_PASSWORD")) {
                debug.storeFile = project.rootProject.file(props.getProperty("DEBUG_STORE_FILE"))
                debug.storePassword = props.getProperty("RELEASE_STORE_PASSWORD")
                debug.keyAlias = props.getProperty("RELEASE_KEY_ALIAS")
                debug.keyPassword = props.getProperty("RELEASE_KEY_PASSWORD")
            } else {
                println("Debug signing properties missing, signing disabled for debug")
            }
        }

    }
}
ksp {
    arg("AROUTER_MODULE_NAME", project.name)
}
// 解析各个工程build.gradle之前会执行。
beforeEvaluate {
    // 这里可以添加前置逻辑
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    // 公共组件
    implementation(project(":component_common"))

    ksp(libs["aroutercompiler"]!!)
    ksp(libs["glideCompiler"]!!)
    // 主模块
    implementation(project(":module_main"))

    // 用户模块
    implementation(project(":module_user"))

    // 登录模块
    implementation(project(":module_login"))

    // 其他模块
    implementation(project(":module_other"))
}