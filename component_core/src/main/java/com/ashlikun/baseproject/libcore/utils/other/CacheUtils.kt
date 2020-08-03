package com.ashlikun.baseproject.libcore.utils.other

import android.os.Environment
import com.ashlikun.gson.StringNullAdapter
import com.ashlikun.utils.AppUtils
import java.io.File

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/18　10:50
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：get属性是为了清空目录的时候再次使用可能No such file or directory异常
 */
object CacheUtils {
    var rootName = ""

    //内部缓存根目录
    val appCacheRoot = StringNullAdapter.NULL
        get() = check(if (field.isNullOrEmpty()) "${AppUtils.getApp().cacheDir.path}" else field)

    //app缓存路径，内部
    val appCachePath = StringNullAdapter.NULL
        get() = check(if (field.isNullOrEmpty()) "${AppUtils.getApp().cacheDir.path}/$rootName/cache" else field)

    //app文件路径，内部
    val appFilePath = StringNullAdapter.NULL
        get() = check(if (field.isNullOrEmpty()) "${AppUtils.getApp().filesDir.path}/$rootName/file" else field)

    //appsd卡缓存路径
    val appSDCachePath = StringNullAdapter.NULL
        get() = check(if (field.isNullOrEmpty()) "${Environment.getExternalStorageDirectory().path}/$rootName/cache" else field)

    //appsd卡文件路径
    val appSDFilePath = StringNullAdapter.NULL
        get() = check(if (field.isNullOrEmpty()) "${Environment.getExternalStorageDirectory().path}/$rootName/file" else field)

    //app sd卡路径
    val appSDPath = StringNullAdapter.NULL
        get() = check(if (field.isNullOrEmpty()) "${Environment.getExternalStorageDirectory().path}/$rootName" else field)

    fun check(path: String): String {
        val file = File(path)
        if (file.exists() || file.mkdirs()) {
        }
        return path
    }

    /**
     * @param rootName 跟目录名称，一般为app名称
     */
    @JvmStatic
    fun init(rootName: String) {
        CacheUtils.rootName = rootName
    }

    /**
     * 获取一个新的图片文件
     */
    @JvmStatic
    fun newImage(): File {
        // 首先保存图片
        val storePath = appSDFilePath + File.separator + "photo"
        val appDir = File(storePath)
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        file.createNewFile()
        return File(appDir, fileName)
    }
}
