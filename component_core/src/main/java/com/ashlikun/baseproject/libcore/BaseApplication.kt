package com.ashlikun.baseproject.libcore

import android.content.Context
import android.content.res.Configuration
import android.view.Gravity
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.ashlikun.appcrash.AppCrashConfig
import com.ashlikun.baseproject.libcore.router.RouterManage
import com.ashlikun.baseproject.libcore.utils.http.HttpManager
import com.ashlikun.baseproject.libcore.utils.other.AppConfig
import com.ashlikun.baseproject.libcore.utils.other.AppCrashEventListener
import com.ashlikun.baseproject.libcore.utils.other.CacheUtils
import com.ashlikun.baseproject.libcore.utils.other.initBugly
//import com.ashlikun.baseproject.BuildConfig

import com.ashlikun.customdialog.BaseDialog
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.loadswitch.LoadSwitch
import com.ashlikun.okhttputils.http.OkHttpManage
import com.ashlikun.okhttputils.http.download.DownloadManager
import com.ashlikun.orm.LiteOrmUtil
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.ui.modal.SuperToast
import com.ashlikun.vlayout.VLayoutUtils

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/30 14:45
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Application公共的初始化
 */
open class BaseApplication : MultiDexApplication() {
    /**
     * 项目中全部module的app集合
     */
    private val applications = ArrayList<IApplication>()

    override fun attachBaseContext(base: Context) {
        //app工具
        AppUtils.init(this, base.resources.getString(R.string.app_name_letter), AppConfig.debug)
        AppUtils.attachBaseContext(base)
        var newContext = base
        applications.forEach {
            newContext = it.attachBaseContext(newContext)
        }
        super.attachBaseContext(newContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (AppUtils.isDebug) {
            /**
             * 17:07:33.824  D   主线程消息执行调试,注意频繁调用   >>>>> Dispatching to Handler (android.view.Choreographer$FrameHandler) {43b53cb} android.view.Choreographer$FrameDisplayEventReceiver@accb7a8: 0
             * 17:07:33.830  D   主线程消息执行调试,注意频繁调用   <<<<< Finished to Handler (android.view.Choreographer$FrameHandler) {43b53cb} android.view.Choreographer$FrameDisplayEventReceiver@accb7a8
             */
//            Looper.getMainLooper().setMessageLogging {
//                LogUtils.d(" 主线程消息执行调试,注意频繁调用   ${it}")
//            }
        }
        initLib()
        //布局切换管理器
//        LoadSwitch.BASE_EMPTY_LAYOUT_ID = R2.layout.base_load_empty
//        LoadSwitch.BASE_RETRY_LAYOUT_ID = R2.layout.base_load_retry
//        LoadSwitch.BASE_LOADING_LAYOUT_ID = R2.layout.base_load_loading

        //activity创建管理器
        for (a in applications) {
            a.onCreate()
        }
    }

    /**
     * 每次新建一个module时候都要在这里添加
     *
     * @param application
     */
    fun addApplication(application: IApplication) {
        if (!applications.contains(application)) {
            applications.add(application)
        }
    }

    private fun initLib() {
        LoadSwitch.init(this)
        //异常捕获
        AppCrashConfig.Builder.create(this)
            .eventListener(AppCrashEventListener())
            .isDebug(AppUtils.isDebug)
            .apply()

        //toast库
        SuperToast.setGravity(Gravity.CENTER)
        VLayoutUtils.isDebug = AppUtils.isDebug
        //开发助手
        if (AppUtils.isDebug) {
//            DoKit.Builder(this).customKits(listOf(LogConfig.MyLogKit())).productId(FileUtils.getMetaValue("dokit_pid")).build()
        }
        //数据库
        LiteOrmUtil.init(this, AppUtils.isDebug)
        LiteOrmUtil.setVersionCode(AppUtils.versionCode)
        LiteOrmUtil.setIsDebug(AppUtils.isDebug)
        //路由
        RouterManage.init(AppUtils.app, AppUtils.isDebug)
        //http
        HttpManager.get()
        DownloadManager.initPath(CacheUtils.appSDDownloadPath)
        GlideUtils.setDEBUG(AppUtils.isDebug)
        //Glide图片加载使用一个okHttpClient
        GlideUtils.init(this, OkHttpManage.get().okHttpClient.newBuilder().apply {
            interceptors().clear()
            networkInterceptors().clear()
        }.build())
        //腾讯Bugly
        initBugly()
        //x5内核
        initX5Tbs()
    }

    private fun initX5Tbs() {
        // 初始化X5内核时候的配置
//        QbSdk.initTbsSettings(
//            mapOf(
//                TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER to true,
//                TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE to true
//            )
//        )
//        QbSdk.initX5Environment(this, object : QbSdk.PreInitCallback {
//            override fun onCoreInitFinished() {
//                Log.e("QbSdk", "内核加载成功")
//            }
//
//            override fun onViewInitFinished(b: Boolean) {
//                if (b) {
//                    Log.e("腾讯X5", " onViewInitFinished 加载 成功 $b")
//                } else {
//                    Log.e("腾讯X5", " onViewInitFinished 加载 失败！！！使用原生安卓webview $b")
//                }
//            }
//        })
    }

    override fun onTerminate() {
        super.onTerminate()
        for (a in applications) {
            a.onTerminate()
        }
    }


    override fun onLowMemory() {
        super.onLowMemory()
        for (a in applications) {
            a.onLowMemory()
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        for (a in applications) {
            a.onTrimMemory(level)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        for (a in applications) {
            a.onConfigurationChanged(newConfig)
        }
    }

}
