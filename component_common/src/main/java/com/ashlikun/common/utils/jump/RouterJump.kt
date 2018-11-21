package com.ashlikun.common.utils.jump

import android.app.Activity
import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.common.mode.javabean.ImageData
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.SuperToast
import java.util.*

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:36
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：路由跳转的工具类
 */
object RouterJump {
    fun topActivity(): Activity {
        return ActivityManager.getInstance().currentActivity()
    }

    /**
     * 启动引导页
     */
    fun startLaunch() {
        ARouter.getInstance().build(RouterPath.LAUNCH)
                .greenChannel()
                .navigation(topActivity())
    }

    /**
     * 启动首页，如果已经启动会清空上面的activity
     *
     * @param index -1:默认页
     */
    @JvmOverloads
    fun startHome(index: Int = -1) {
        ARouter.getInstance().build(RouterPath.HOME)
                .withInt(RouterKey.FLAG_INDEX, index)
                .withFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .greenChannel()
                .navigation(topActivity())
    }

    /**
     * 返回登录页面
     */
    fun startLogin() {
        ARouter.getInstance().build(RouterPath.LOGIN)
                .greenChannel()
                .navigation(topActivity())
    }

    /**
     * 查看大图
     * 前一个页面请调用 statusBar.setFitsSystemWindows()保证页面不抖动
     */
    fun startLockImage(position: Int, listDatas: ArrayList<ImageData>?) {
        if (listDatas == null || listDatas.isEmpty()) {
            SuperToast.showErrorMessage("没有对应的图片")
            return
        }
        ARouter.getInstance().build(RouterPath.IMAGE_LOCK)
                .withParcelableArrayList(RouterKey.FLAG_DATA, listDatas)
                .withInt(RouterKey.FLAG_POSITION, position)
                .greenChannel()
                .navigation(topActivity())
    }
}
