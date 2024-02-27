package com.ashlikun.baseproject

import android.content.Context
import android.content.res.Configuration
import com.ashlikun.baseproject.libcore.BaseApplication
/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　15:45
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：打包时候的Application
 */
class MyApp : BaseApplication() {

    override fun attachBaseContext(base: Context) {
        //创建，每次新建一个module时候都要在这里添加
        addApplication(com.ashlikun.baseproject.common.CommonApp())
        addApplication(com.ashlikun.baseproject.module.main.ModuleApp())
        addApplication(com.ashlikun.baseproject.module.user.ModuleApp())
        addApplication(com.ashlikun.baseproject.module.login.ModuleApp())
        addApplication(com.ashlikun.baseproject.module.other.ModuleApp())
        super.attachBaseContext(base)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onTerminate() {
        super.onTerminate()
    }


    override fun onLowMemory() {
        super.onLowMemory()
    }

}
