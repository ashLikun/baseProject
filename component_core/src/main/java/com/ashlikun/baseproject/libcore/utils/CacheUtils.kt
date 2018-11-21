package com.ashlikun.baseproject.libcore.utils

import android.os.Environment

import com.ashlikun.utils.AppUtils

import java.io.File

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/18　10:50
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
object CacheUtils {
    //app缓存路径，内部
    var appCachePath: String = ""
    //app文件路径，内部
    var appFilePath: String = ""
    //appsd卡缓存路径
    var appSDCachePath: String = ""
    //appsd卡文件路径
    var appSDFilePath: String = ""

    /**
     * @param rootName 跟目录名称，一般为app名称
     */
    fun init(rootName: String) {
        val cacheStr = "/$rootName/cache"
        val fileStr = "/$rootName/file"
        appCachePath = AppUtils.getApp().cacheDir.path + cacheStr
        appFilePath = AppUtils.getApp().filesDir.path + fileStr
        appSDCachePath = Environment.getExternalStorageDirectory().path + cacheStr
        appSDFilePath = Environment.getExternalStorageDirectory().path + fileStr

        val file = File(appSDCachePath)
        if (file.exists() || file.mkdirs()) {
        }
        val file2 = File(appSDFilePath)
        if (file2.exists() || file2.mkdirs()) {
        }
    }
}
