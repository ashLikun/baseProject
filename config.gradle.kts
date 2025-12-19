val kotlin_version = "2.0.0"
val coroutine_version = "1.7.1"
val ksp_version = "${kotlin_version}-1.0.24"
val dokit_version = "3.7.1"

val frameVersion = "1.2.8"
val lifecycleVersion = "2.9.2"
val jpushVersion = "5.2.4"
val jpushCoreVersion = "3.3.6"
val applicationId = "com.ashlikun.baseproject"

extra["kotlin_version"] = kotlin_version
extra["coroutine_version"] = coroutine_version
extra["ksp_version"] = ksp_version
extra["dokit_version"] = dokit_version
extra["frameVersion"] = frameVersion
extra["lifecycleVersion"] = lifecycleVersion
extra["jpushVersion"] = jpushVersion
extra["jpushCoreVersion"] = jpushCoreVersion

extra["android"] = mapOf(
    "applicationId" to applicationId,
    "compileSdk" to 35,
    "versionCode" to 1,
    "versionName" to "1.0.0",
    "targetSdk" to 32,
    "minSdk" to 24,
    "flavorDimensions" to "flavorDimensions"
)

/**
 * 这里配置打包不同的参数,可以替换AndroidManifest文件中任何${KEY}格式的占位符
 *  <meta-data
 *     android:name="KEY_NAME"
 *     android:value="${KEY}"></meta-data>
 */
extra["sdk"] = mapOf(
    "default" to mapOf(
        "JPUSH_PKGNAME" to applicationId,
        "JPUSH_APPKEY" to "001b8ca1b4962d68bfb65049",//JPush上注册的包名对应的appkey.
        "JPUSH_CHANNEL" to "developer-default", //暂时填写默认值即可.
        "BUGLY_APPKEY" to "f28e3effd0", // 腾讯bugly
        "DOKIT_PID" to "f2ab59a864aabba32dd5ebeddac76c83", // 滴滴的开发助手,https://www.dokit.cn/
    ),
    "debugBuild" to mapOf(
        "type" to "type",
    ),
    "betaBuild" to mapOf(
        "type" to "type",
    ),
    "releasBuild" to mapOf(
        "type" to "type"
    ),
)

extra["dependencies"] = mapOf<String, String>(
    "coreKtx" to "androidx.core:core-ktx:1.16.0",
    "kotlinReflect" to "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version",
    // kotlin的协程
    "kotlinCoroutines" to "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version",
    "appcompat" to "androidx.appcompat:appcompat:1.7.1",
    "materialDesign" to "com.google.android.material:material:1.12.0",
    // 约束布局,如果升级2.2.0以上，必须 LoadSwitch使用对应版本
    "constraintLayout" to "androidx.constraintlayout:constraintlayout:2.2.1",
    // 协调布局
    "coordinatorLayout" to "androidx.coordinatorlayout:coordinatorlayout:1.3.0",
    // recyclerview
    "recyclerview" to "androidx.recyclerview:recyclerview:1.4.0",
    // 生命周期扩展 Viewmodel
    "lifecycleViewmodelKtx" to "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion",
    // 生命周期扩展 Activity
    "lifecycleRuntimeKtx" to "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion",
    // LiveData
    "lifecycleLiveDataKtx" to "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion",
    // Activity
    "Activity" to "androidx.activity:activity-ktx:1.10.1",
    // Fragment
    "Fragment" to "androidx.fragment:fragment-ktx:1.8.8",
    // 多个Dex
    "multidex" to "androidx.multidex:multidex:2.0.1",

    // 基本架构 1.0.6 适配viewmodel2.6.1之后，之前请用1.0.5
    "CustomMvvm" to "com.gitee.ashlikun.Framework:libraryMvvm:1.1.4",
    // webview封装
    "XWebView" to "com.gitee.ashlikun.XWebView:core:1.0.2",
    // alibaba的路由框架
    "arouterapi" to "com.alibaba:arouter-api:1.5.2",
    "aroutercompiler" to "com.github.ashLikun:ArouterKspCompiler:$ksp_version",
    // 简单的PopupWindow
    "EasyPopupWindow" to "com.gitee.ashlikun:EasyPopupWindow:1.0.1",
    // 事件总线
    "EventBus" to "com.gitee.ashlikun:LiveDataBus:1.0.4",
    // 数据库
    "LiteOrm" to "com.gitee.ashlikun:LiteOrm:1.0.1",
    // MMKV
    "MMKV" to "com.tencent:mmkv:2.2.2",

    // 保活的策略，上架需要FOREGROUND_SERVICE_DATA_SYNC
    "Keeplive" to "com.gitee.ashlikun:Keeplive:1.0.3",

    // 封装的RecycleViewFr
    "XRecycleView" to "com.gitee.ashlikun:XRecycleView:1.0.4",
    // 动画菜单,可以不用
    "AnimationMenu" to "com.gitee.ashlikun:AnimationMenu:1.0.0",
    // 自定义的下拉刷新
    "RefreshLayout" to "com.gitee.ashlikun:RefreshLayout:1.0.1",
    // 封装的viewpager，banner，启动页
    "XViewPager" to "com.gitee.ashlikun:XViewPager:1.0.0",
    "XViewPager2" to "com.gitee.ashlikun:XViewPager2:1.0.1",
    // 公共的工具
    "CommonUtils" to "com.gitee.ashlikun:common-utils:4.9.17",
    // 公共的Adapter
    "CommonAdapter" to "com.gitee.ashlikun:CommonAdapter:1.0.2",
    // Google官方改造
    "XTabLayout" to "com.gitee.ashlikun:XTabLayout:1.0.1",
    // 功能更加丰富
    "XTabLayout2" to "com.gitee.ashlikun:XTabLayout2:1.0.0",
    // 阿里的vlayout
    "vlayout" to "com.gitee.ashlikun:vLayout:1.0.0",
    // 习惯的对话框
    "CustomDialog" to "com.gitee.ashlikun:CustomDialog:1.0.13",
    // 第三方MD风格对话框 https://github.com/afollestad/material-dialogs
    "MaterialDialog" to "com.afollestad.material-dialogs:core:3.3.0",
    "MaterialDialogInput" to "com.afollestad.material-dialogs:input:3.3.0",
    // https://github.com/afollestad/material-dialogs/blob/main/documentation/BOTTOMSHEETS.md
    "MaterialDialogBottomSheets" to "com.afollestad.material-dialogs:bottomsheets:3.3.0",
    // 圆形的进度条
    "CircleProgressView" to "com.gitee.ashlikun:CircleProgressView:1.0.0",
    // LayoutManage
    "XLayoutManager" to "com.gitee.ashlikun:XLayoutManager:1.0.0",
    // 布局切换
    "LoadSwitch" to "com.gitee.ashlikun:LoadSwitch:2.2.0",
    // 兼容的View
    "CompatView" to "com.gitee.ashlikun:CompatView:1.0.0",
    // 错误捕获
    "AppCrashDispose" to "com.gitee.ashlikun:AppCrashDispose:1.0.0",
    "FreeReflection" to "com.github.tiann:FreeReflection:3.1.0",
    // app内存溢出捕获
    "leakcanaryDebug" to "com.squareup.leakcanary:leakcanary-android:2.14",
    // 滴滴的开发助手 https://github.com/didi/DoKit
    "doraemonkitDebug" to "io.github.didi.dokit:dokitx:$dokit_version",
    // 文件同步模块
    "doraemonFtDebug" to "io.github.didi.dokit:dokitx-ft:$dokit_version",
    // 一机多控模块
    "doraemonMcDebug" to "io.github.didi.dokit:dokitx-mc:$dokit_version",
    "doraemonkitRelease" to "io.github.didi.dokit:dokitx-no-op:$dokit_version",

    // shimmer 效果
    "shimmer" to "io.supercharge:shimmerlayout:2.1.0",
    // 导航tab
    "BottomNavigation" to "com.gitee.ashlikun:BottomNavigation:1.0.5",
    // rxjava
    "rxandroid" to "io.reactivex.rxjava2:rxandroid:2.1.1",
    "rxjava" to "io.reactivex.rxjava2:rxjava:2.2.6",
    "RxLife" to "com.gitee.ashlikun:RxLife:1.0.2",
    // okhttp
    "okhttp" to "com.squareup.okhttp3:okhttp:4.10.0",
    "gson" to "com.google.code.gson:gson:2.10",
    // 不同type的json解析,依赖于gson
    "MultiTypeGson" to "com.gitee.ashlikun:MultiTypeGson:1.0.0",
    // 基于Okhttp封装的
    "OkHttpUtils" to "com.gitee.ashlikun:OkHttpUtils:1.0.11",
    // 图片加载
    "GlideUtils" to "com.gitee.ashlikun:GlideUtils:1.0.4",
    "glide" to "com.github.bumptech.glide:glide:4.16.0",
    "glideCompiler" to "com.github.bumptech.glide:compiler:4.16.0",
    "glideTransformations" to "jp.wasabeef:glide-transformations:4.3.0", // 4.2.0 glide4.11.0
    "glidegpuimage" to "jp.co.cyberagent.android:gpuimage:2.1.0", // 2.0.4
    // 日期选择 https://github.com/loperSeven/DateTimePicker
    "DateTimePicker" to "com.github.loperSeven:DateTimePicker:0.6.3",
    // 自定义键盘 https://github.com/jenly1314/KingKeyboard
    "kingkeyboard" to "com.github.jenly1314:kingkeyboard:1.0.4",
    // lottie 动画
    "lottie" to "com.airbnb.android:lottie:6.4.0",
    // Android官方流布局
    "flexbox" to "com.google.android.flexbox:flexbox:3.0.0",
    // 图片查看
    "PhotoView" to "com.gitee.ashlikun:PhotoView:1.0.0",
    // 照片处理
    "PhotoHandler" to "com.gitee.ashlikun:PhotoHandler:1.0.5",
    // 输入验证码
    "VerificationCodeInput" to "com.gitee.ashlikun:verify-code-view:1.0.5",
    // 带圆角，边框的的ImageView
    "RoundedImageView" to "com.makeramen:roundedimageview:2.3.0",
    // 评分控件 exclude group: 'com.android.support'
    "MaterialRatingBar" to "com.github.ashLikun:MaterialRatingBar:2.0.0",
    "CalendarView" to "com.github.ashLikun:CalendarView:2.0.0",
    // 微信分享，登录sdk
    "wechat" to "com.tencent.mm.opensdk:wechat-sdk-android-without-mta:6.8.0",
    // 扫码
    "zxingCore" to "com.google.zxing:core:3.5.3",
    "zxingLib" to "com.gitee.ashlikun.camera-x:zxingLib:1.1.8",
    "zxingGrayScaleLib" to "com.gitee.ashlikun.camera-x:zxingGrayScaleLib:1.1.8",
    "EasyPay" to "com.github.ashLikun:EasyPay:2.0.4",
    // 腾讯X5浏览器 44136  http://soft.imtt.qq.com/browser/tes/feedback.html 显示000000表示加载的是系统内核，显示大于零的数字表示加载了x5内核（该数字是x5内核版本号）
    "tbssdk" to "com.tencent.tbs:tbssdk:44181",
    // 极光推送
    "jpush" to "cn.jiguang.sdk:jpush-google:$jpushVersion",
    // 极光推送
    "jcore" to "cn.jiguang.sdk:jcore-google:$jpushCoreVersion",
    // 腾讯Bugly
    "BugLy" to "com.tencent.bugly:crashreport:4.1.9.2",

    "shareQQ" to "com.tencent.tauth:qqopensdk:3.53.0",
    "shareSina" to "io.github.sinaweibosdk:core:11.11.1@aar",

    // 友盟统计与分享
    // 友盟基础组件库（所有友盟业务SDK都依赖基础组件库）
    "umsdkCommon" to "com.umeng.umsdk:common:9.5.4",
    "umsdkAsms" to "com.umeng.umsdk:asms:1.6.3",
    // 分享核心库，必选
    "umsdkShareCore" to "com.umeng.umsdk:share-core:7.1.9",
    // 分享面板功能，可选
    "umsdkShareBoard" to "com.umeng.umsdk:share-board:7.1.9",
    // 在线依赖目前支持QQ、微信、新浪微博、支付宝、钉钉；若需支持其他平台，请选择手动下载SDK
    // 微信完整版
    "umsdkShareWx" to "com.umeng.umsdk:share-wx:7.1.9",
    // java WebSocket 这个库可以实现服务端
    "javaWebSocket" to "org.java-websocket:Java-WebSocket:1.5.1",

    // RangeSeekBar 进度条 https://github.com/Jay-Goo/RangeSeekBar/wiki
    "RangeSeekBar" to "com.github.Jay-Goo:RangeSeekBar:v3.0.0",
    // 播放器
    "EasyMedia" to "com.gitee.ashlikun.EasyMedia:BaseMedia:1.0.1",
    "EasyMediaExo3" to "com.gitee.ashlikun.EasyMedia:ExoPlay3:1.0.1",
    // Ijkplay
    "ijkplayerEx" to "com.gitee.ashlikun.EasyMedia:ijkplayer-ex_so-live:1.0.1",
    // ExoPlay
    "media3Common" to "androidx.media3:media3-common:1.8.0",
    "media3Exo" to "androidx.media3:media3-exoplayer:1.8.0",
    "media3Rtsp" to "androidx.media3:media3-exoplayer-rtsp:1.8.0",


)