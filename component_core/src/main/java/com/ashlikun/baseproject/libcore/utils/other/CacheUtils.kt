package com.ashlikun.baseproject.libcore.utils.other

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
    var rootName = ""
    //app缓存路径，内部
    val appCachePath: String by lazy {
        check("${AppUtils.getApp().cacheDir.path}")
    }
    //app文件路径，内部
    val appFilePath: String by lazy {
        check("${AppUtils.getApp().filesDir.path}")
    }
    //appsd卡缓存路径
    val appSDCachePath: String by lazy {
        check("${Environment.getExternalStorageDirectory().path}/$rootName/cache")
    }
    //appsd卡文件路径
    val appSDFilePath: String by lazy {
        check("${Environment.getExternalStorageDirectory().path}/$rootName/file")
    }
    //app sd卡路径
    val appSDPath: String by lazy {
        check("${Environment.getExternalStorageDirectory().path}/$rootName")
    }

    fun check(path: String): String {
        val file = File(path)
        if (file.exists() || file.mkdirs()) {
        }
        return path
    }

    /**
     * @param rootName 跟目录名称，一般为app名称
     */
    fun init(rootName: String) {
        CacheUtils.rootName = rootName
    }
}
