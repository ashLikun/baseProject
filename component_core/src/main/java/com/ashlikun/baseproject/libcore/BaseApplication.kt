package com.ashlikun.baseproject.libcore

import android.app.Activity
import android.app.Application
import android.content.res.Configuration
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import android.view.Gravity
import com.ashlikun.appcrash.config.CaocConfig
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.libcore.utils.CacheUtils
import com.ashlikun.baseproject.libcore.utils.http.HttpManager
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.loadswitch.LoadSwitch
import com.ashlikun.orm.LiteOrmUtil
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.SuperToast
import com.squareup.leakcanary.LeakCanary
import java.util.*

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


    override fun onCreate() {
        super.onCreate()
        initLib()
        CacheUtils.init(resources.getString(R.string.app_name_letter))
        //布局切换管理器
        LoadSwitch.BASE_EMPTY_LAYOUT_ID = R.layout.base_load_empty
        LoadSwitch.BASE_RETRY_LAYOUT_ID = R.layout.base_load_retry
        LoadSwitch.BASE_LOADING_LAYOUT_ID = R.layout.base_load_loading
        //activity创建管理器
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        for (a in applications) {
            a.onCreate()
        }
        RouterManage.init(AppUtils.getApp(), AppUtils.isDebug())
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
        //内存溢出检测
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this)
        }
        //app工具
        AppUtils.init(this)
        AppUtils.setDebug(BuildConfig.DEBUG)
        //异常捕获
        CaocConfig.Builder.create()
                .apply()
        //数据库
        LiteOrmUtil.init(this)
        LiteOrmUtil.setVersionCode(BuildConfig.VERSION_CODE)
        LiteOrmUtil.setIsDebug(BuildConfig.DEBUG)
        //glide图片库
        GlideUtils.setBaseUrl(HttpManager.BASE_URL)
        GlideUtils.setDebug(BuildConfig.DEBUG)
        //toast库
        SuperToast.setGravity(Gravity.CENTER)
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

    val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            ActivityManager.getInstance().pushActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {}

        override fun onActivityPaused(activity: Activity) {}

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

        override fun onActivityDestroyed(activity: Activity) {
            ActivityManager.getInstance().exitActivity(activity)
        }
    }

}
