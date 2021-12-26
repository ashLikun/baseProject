package com.ashlikun.baseproject.libcore.utils.other

import com.ashlikun.gson.StringNullAdapter
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.file.PathUtils
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

    //app缓存路径，内部
    val appCachePath = StringNullAdapter.NULL
        get() = check(if (field.isNullOrEmpty()) "${AppUtils.app.cacheDir.path}" else field)

    //app文件路径，内部
    val appFilePath = StringNullAdapter.NULL
        get() = check(if (field.isNullOrEmpty()) "${AppUtils.app.filesDir.path}" else field)

    //appsd卡缓存路径(Android/data/pageName/cache)删除应用会删除这个目录，不用动态申请权限
    val appSDCachePath = StringNullAdapter.NULL
        get() = check(
                if (field.isNullOrEmpty()) {
                    PathUtils.externalAppCache
                } else field
        )

    //appsd卡文件路径(Android/data/pageName/files)删除应用会删除这个目录，不用动态申请权限
    val appSDFilePath = StringNullAdapter.NULL
        get() = check(
                if (field.isNullOrEmpty()) {
                    PathUtils.externalAppFiles
                } else field
        )

    //appsd卡DCIM路径(Android/data/pageName/files/DCIM)删除应用会删除这个目录，不用动态申请权限
    val appSDDcimPath = StringNullAdapter.NULL
        get() = check(
                if (field.isNullOrEmpty()) {
                    PathUtils.externalAppDcim
                } else field
        )

    //appsd卡下载路径(Android/data/pageName/files/DCIM)删除应用会删除这个目录，不用动态申请权限
    val appSDDownloadPath = StringNullAdapter.NULL
        get() = check(
                if (field.isNullOrEmpty()) {
                    PathUtils.externalAppDownload
                } else field
        )

    //app sd 缓存路径，删除应用会删除这个目录，不用动态申请权限
    val appSdCacheVideoPath = StringNullAdapter.NULL
        get() = check(if (field.isNullOrEmpty()) "${appSDCachePath}/video" else field)

    //sd卡dcim路径，需要权限
    val sdDcimPath = StringNullAdapter.NULL
        get() = check(
                if (field.isNullOrEmpty()) {
                    PathUtils.externalDcim + File.separator + rootName
                } else field
        )


    //sd卡路径，需要权限,不建议使用
    val sdPath = StringNullAdapter.NULL
        get() = check(
                if (field.isNullOrEmpty()) {
                    PathUtils.externalStorage
                } else field
        )


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
        val appDir = File(sdDcimPath)
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        file.createNewFile()
        return File(appDir, fileName)
    }
}
