package com.ashlikun.baseproject.libcore

import android.content.res.Configuration
import android.view.Gravity
import androidx.multidex.MultiDexApplication
import com.ashlikun.appcrash.AppCrashConfig
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.libcore.utils.CacheUtils
import com.ashlikun.baseproject.libcore.utils.LeakCanaryUtils
import com.ashlikun.baseproject.libcore.utils.http.HttpManager
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.loadswitch.LoadSwitch
import com.ashlikun.okhttputils.http.download.DownloadManager
import com.ashlikun.orm.LiteOrmUtil
import com.ashlikun.utils.AppUtils
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
        //缓存工具
        CacheUtils.init(resources.getString(R.string.app_name_letter))
        AppUtils.setDebug(BuildConfig.DEBUG)
        //异常捕获
        AppCrashConfig.Builder.create(this)
                .isDebug(BuildConfig.DEBUG)
                .apply()
        //内存溢出检测
        LeakCanaryUtils.init(this)
        //开发助手
        DoraemonKit.install(this)
        //数据库
        LiteOrmUtil.init(this)
        LiteOrmUtil.setVersionCode(BuildConfig.VERSION_CODE)
        LiteOrmUtil.setIsDebug(BuildConfig.DEBUG)
        //路由
        RouterManage.init(AppUtils.getApp(), AppUtils.isDebug())
        //http
        HttpManager.get()
        DownloadManager.initPath(CacheUtils.appFilePath)
        //glide图片库
        GlideUtils.setBaseUrl(HttpManager.BASE_URL)
        GlideUtils.setDEBUG(BuildConfig.DEBUG)
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

}
