package com.ashlikun.baseproject.libcore

import android.content.res.Configuration
import android.view.Gravity
import androidx.multidex.MultiDexApplication
import com.ashlikun.appcrash.AppCrashConfig
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.libcore.utils.http.HttpManager
import com.ashlikun.baseproject.libcore.utils.other.AppCrashEventListener
import com.ashlikun.baseproject.libcore.utils.other.CacheUtils
import com.ashlikun.baseproject.libcore.utils.other.initBugly
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.loadswitch.LoadSwitch
import com.ashlikun.okhttputils.http.download.DownloadManager
import com.ashlikun.orm.LiteOrmUtil
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.file.FileUtils
import com.ashlikun.utils.ui.SuperToast
import com.didichuxing.doraemonkit.DoraemonKit
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

        //布局切换管理器
        LoadSwitch.BASE_EMPTY_LAYOUT_ID = R.layout.base_load_empty
        LoadSwitch.BASE_RETRY_LAYOUT_ID = R.layout.base_load_retry
        LoadSwitch.BASE_LOADING_LAYOUT_ID = R.layout.base_load_loading

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

        //app工具
        AppUtils.init(this)
        AppUtils.setDebug(BuildConfig.DEBUG)
        //缓存工具
        CacheUtils.init(resources.getString(R.string.app_name_letter))
        //异常捕获
        AppCrashConfig.Builder.create(this)
                .eventListener(AppCrashEventListener())
                .isDebug(AppUtils.isDebug())
                .apply()
        //开发助手
        DoraemonKit.install(this, FileUtils.getMetaValue(this, "DOKIT_PID"))
        //数据库
        LiteOrmUtil.init(this)
        LiteOrmUtil.setVersionCode(BuildConfig.VERSION_CODE)
        LiteOrmUtil.setIsDebug(BuildConfig.DEBUG)
        //路由
        RouterManage.init(AppUtils.getApp(), AppUtils.isDebug())
        //http
        HttpManager.get()
        DownloadManager.initPath(CacheUtils.appFilePath)
        GlideUtils.setDEBUG(BuildConfig.DEBUG)
        //toast库
        SuperToast.setGravity(Gravity.CENTER)
        //腾讯Bugly
        initBugly()
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
