package com.ashlikun.libarouter.jump

import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.libarouter.constant.ARouterPath

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/19　13:36
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：路由跳转的工具类
 */
object ARouterJump {

    @JvmStatic
    fun startHome(index: Int, canshu: String) {
        val bundle = Bundle()
        bundle.putInt("index", index)
        bundle.putString("name", canshu)
        ARouter.getInstance().build(ARouterPath.HOME)
                .with(bundle)
                .withFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation()
    }

    @JvmStatic
    fun startTest() {
        ARouter.getInstance().build(ARouterPath.TEST)
                .navigation()
    }

    /**
     * 返回登录页面
     */
    @JvmStatic
    fun startLogin() {
        ARouter.getInstance().build(ARouterPath.LOGIN)
                .navigation()
    }
}