package com.ashlikun.baseproject.libcore

import android.content.res.Configuration

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/30　14:40
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：其他模块需要实现这个接口才能走Application的初始化
 */
interface IApplication {
    fun onCreate()

    fun onTerminate()

    fun onLowMemory()

    fun onTrimMemory(level: Int)

    fun onConfigurationChanged(newConfig: Configuration)
}
