package com.ashlikun.baseproject.libcore.router

import android.app.Activity
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.core.LogisticsCenter
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 作者　　: 李坤
 * 创建时间: 2018/11/27　11:14
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：路由的一些工具
 */
object RouterUtils {
    /**
     * 获取path对应的目标Class,可能为null
     */
    fun getRouterClass(path: String): Class<*>? {
        val postcard = ARouter.getInstance().build(path)
        try {
            LogisticsCenter.completion(postcard)
            return postcard.destination
        } catch (e: Exception) {

        }
        return null
    }

    /**
     * 获取path对应的目标Class,可能为null
     */
    fun getActivityClass(path: String): Class<out Activity>? {
        val c = getRouterClass(path)
        return c as Class<out Activity>?
    }

    /**
     * 获取path对应的目标Class,可能为null
     */
    fun getFragmentClass(path: String): Class<out Fragment>? {
        val c = getRouterClass(path)
        return c as Class<out Fragment>?
    }
}

