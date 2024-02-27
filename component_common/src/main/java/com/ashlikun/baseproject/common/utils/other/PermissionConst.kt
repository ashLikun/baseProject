package com.ashlikun.baseproject.common.utils.other

import android.Manifest
import android.os.Build
import com.ashlikun.utils.AppUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2023/10/7　10:57
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
object PermissionConst {
    /**
     * 图片存储的权限
     */
    val PERMISSION_STORAGE_IMAGE by lazy {
        if (Build.VERSION.SDK_INT >= 33 && AppUtils.app.getApplicationInfo().targetSdkVersion >= 33)
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            //10 之前还是必须使用
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            //android 10.0 包含10，之后不需要存储权限，完全使用MediaStore API来实现
            emptyArray()
        }
    }
}