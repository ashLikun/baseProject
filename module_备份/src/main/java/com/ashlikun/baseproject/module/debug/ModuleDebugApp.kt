package com.ashlikun.baseproject.module.debug

import com.ashlikun.baseproject.libcore.BaseApplication
import com.ashlikun.baseproject.module.main.ModuleApp

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/25 10:49
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：用于单独运行登录模块的Application正式打包的时候是没有这个类
 */

class ModuleDebugApp : BaseApplication() {

    @Override
    fun onCreate() {
        addApplication(CommonApp())
        addApplication(ModuleApp())
        super.onCreate()
    }
}
