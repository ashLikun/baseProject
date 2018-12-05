package com.ashlikun.libcore.utils

import com.ashlikun.libarouter.ARouterManage

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/18　10:47
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
object AppUtils {
    @JvmStatic
    fun getApp() = ARouterManage.get().homeService.getApp()
}
