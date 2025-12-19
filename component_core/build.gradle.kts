plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}
val sdk = rootProject.extra["sdk"] as Map<*, *>  // SDK配置
val config = rootProject.extra["android"] as Map<*, *>  // 工程配置
val libs = rootProject.extra["dependencies"] as Map<String, String> // 库依赖

android {
    namespace = "com.ashlikun.baseproject.libcore"
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

    /**
     * java插件引入了一个概念叫做SourceSets 通过修改SourceSets中的属性
     * 可以指定哪些源文件（或文件夹下的源文件）要被编译，哪些源文件要被排除。
     */
    sourceSets {
        getByName("main") {
            res.srcDirs(
                "src/main/res",
                "src/main/res-core"
            )
            //so文件
            jniLibs.srcDirs("libs")
        }
    }
    sourceSets {
        // ksp 插件
        all {
            java.srcDir("build/generated/ksp/${name}/kotlin/")
        }
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = false
    }
    flavorDimensions.addAll(listOf(config["flavorDimensions"] as String))
}
ksp {
    arg("AROUTER_MODULE_NAME", project.name)
}
dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    ksp(libs["aroutercompiler"]!!)
    ksp(libs["glideCompiler"]!!)
    api(libs["coreKtx"]!!)
    //Kotlin 反射相关
    api(libs["kotlinReflect"]!!)
    //kotlinCoroutines
    api(libs["kotlinCoroutines"]!!)
    api(libs["appcompat"]!!)
    api(libs["materialDesign"]!!)
    api(libs["lifecycleViewmodelKtx"]!!)
    api(libs["lifecycleRuntimeKtx"]!!)
    api(libs["lifecycleLiveDataKtx"]!!)
    //约束布局
    api(libs["constraintLayout"]!!)
    //协调布局
    api(libs["coordinatorLayout"]!!)
    //recyclerview
    api(libs["recyclerview"]!!)
    //Fragment
    api(libs["Fragment"]!!)
    //Activity
    api(libs["Activity"]!!)


    //mvvm
    api(libs["CustomMvvm"]!!)
    api(libs["multidex"]!!)


    //alibaba的路由框架
    api(libs["arouterapi"]!!)
    //基础的框架
    // Frame 系列库
    val frameVersion = rootProject.extra["frameVersion"].toString()
    api("com.gitee.ashlikun.frame:stickyrecyclerview:$frameVersion")
    api("com.gitee.ashlikun.frame:flatbutton:$frameVersion")
    api("com.gitee.ashlikun.frame:numberprogressbar:$frameVersion")
    api("com.gitee.ashlikun.frame:segmentcontrol:$frameVersion")
    api("com.gitee.ashlikun.frame:charbar:$frameVersion")
    api("com.gitee.ashlikun.frame:supertoobar:$frameVersion")
    api("com.gitee.ashlikun.frame:wheelview3d:$frameVersion")
    api("com.gitee.ashlikun.frame:badgeview:$frameVersion")
    api("com.gitee.ashlikun.frame:animcheckbox:$frameVersion")
    //简单的PopupWindow
    api(libs["EasyPopupWindow"]!!)
    //事件总线
    api(libs["EventBus"]!!)
    //数据库
    api(libs["LiteOrm"]!!)
    //MMKV
    api(libs["MMKV"]!!)
    //保活的策略,上架需要FOREGROUND_SERVICE_DATA_SYNC
//    api(libs["KeepAlive"]!!)
    //封装的RecycleView
    api(libs["XRecycleView"]!!)
    //自定义的下拉刷新
    api(libs["RefreshLayout"]!!)
    //封装的viewpager，banner，启动页
    api(libs["XViewPager"]!!)
    //公共的工具
    api(libs["CommonUtils"]!!)
    //webview封装
    api(libs["XWebView"]!!)
    //X5 内核的app不支持在海外Google Play上架。
//    api(libs["tbssdk"]!!)
    //极光推送
    api(libs["jpush"]!!)
    //极光推送
    api(libs["jcore"]!!)
    //wechat
    api(libs["wechat"]!!)
    //公共的Adapter
    api(libs["CommonAdapter"]!!)
    //TabLayout
    api(libs["XTabLayout"]!!) {
        exclude(group = "com.android.support")
    }
    //功能更加丰富的TabLayout
    api(libs["XTabLayout2"]!!)
    //阿里的vlayout
    api(libs["vlayout"]!!)
    //习惯的对话框
    api(libs["CustomDialog"]!!)
    //第三方MD风格对话框
    api(libs["MaterialDialogInput"]!!)
    api(libs["MaterialDialogBottomSheets"]!!)
    //圆形的进度条
    api(libs["CircleProgressView"]!!)
    //带圆角，边框的的ImageView
    api(libs["RoundedImageView"]!!)
    //布局切换
    api(libs["LoadSwitch"]!!)
    //兼容的View textview
    api(libs["CompatView"]!!)
    //错误捕获
    api(libs["AppCrashDispose"]!!)
    //自己引用反射
    api(libs["FreeReflection"]!!)
    //app内存溢出捕获 LeakCanary (仅调试)
    debugImplementation(libs["leakcanaryDebug"]!!)
    //shimmer 文字
    api(libs["shimmer"]!!)
    //导航tab
    api(libs["BottomNavigation"]!!)
    //okhttp
    api(libs["okhttp"]!!)
    api(libs["gson"]!!)
    api(libs["MultiTypeGson"]!!)

    //基于Okhttp封装的
    api(libs["OkHttpUtils"]!!)

    //图片加载
    api(libs["GlideUtils"]!!)
    api(libs["glide"]!!)
    api(libs["glideTransformations"]!!)
    //日期选择
    api(libs["DateTimePicker"]!!)
    //自定义键盘
    api(libs["kingkeyboard"]!!)
    //lottie 动画
    api(libs["lottie"]!!)
    //Android官方流布局
    api(libs["flexbox"]!!)
    //图片查看
    api(libs["PhotoView"]!!)
    //照片处理
    api(libs["PhotoHandler"]!!)
    //输入验证码
    api(libs["VerificationCodeInput"]!!)
    //腾讯Bugly
    api(libs["BugLy"]!!)
    //zxingLib
    api(libs["zxingCore"]!!)
    api(libs["zxingLib"]!!)
    api(libs["zxingGrayScaleLib"]!!)
    //进度条
    api(libs["RangeSeekBar"]!!)
}

