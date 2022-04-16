package com.ashlikun.baseproject.common.utils.jump

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.baseproject.common.mode.javabean.ImageData
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.extend.ARouterNavigation
import com.ashlikun.baseproject.libcore.utils.extend.withMap
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.modal.SuperToast
import java.io.Serializable

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:36
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：路由跳转的工具类
 */
object RouterJump {


    fun topActivity() = ActivityManager.get().currentActivity()

    /**
     * 启动任何页面
     */
    fun start(
            path: String,
            params: Map<String, Any?>? = null,
            greenChannel: Boolean = false,
            flags: Int? = null,
            navigation: ARouterNavigation = {
                it.navigation(topActivity())
            }
    ): Any? {
        val aouter = ARouter.getInstance().build(path)
                .withMap(params)
        if (flags != null) {
            aouter.withFlags(flags)
        }
        if (greenChannel) {
            aouter.greenChannel()
        }
        return navigation.invoke(aouter)
    }


    /**
     * 启动App
     */
    fun startApp() = start(
            path = RouterPath.WELCOME,
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
    )

    /**
     * 启动引导页
     */
    fun startLaunch() = start(path = RouterPath.LAUNCH)

    /**
     * 启动首页，如果已经启动会清空上面的activity
     * [Intent.FLAG_ACTIVITY_REORDER_TO_FRONT]:如果已经启动了四个Activity：A，B，C和D，在D Activity里，想再启动一个Actvity B，但不变成A,B,C,D,B，而是希望是A,C,D,B
     * @param index -1:默认页
     */
    fun startHome(index: Int = -1) = start(
            path = RouterPath.HOME, mapOf(RouterKey.FLAG_INDEX to index),
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    )

    /**
     * 返回登录页面
     */
    fun startLogin() {
        ARouter.getInstance().build(RouterPath.LOGIN)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .navigation(topActivity())
    }

    /**
     * 启动测试页面
     */
    fun startTest() {
        ARouter.getInstance().build(RouterPath.TEST)
                .navigation(topActivity())
    }

    /**
     * 查看大图
     * 前一个页面请调用 statusBar.setFitsSystemWindows()保证页面不抖动
     * @param isShowDownload 是否显示下载
     */
    fun startLockImage(
            position: Int,
            listDatas: ArrayList<ImageData>?,
            isShowDownload: Boolean = false
    ) {
        if (listDatas == null || listDatas.isEmpty()) {
            SuperToast.showErrorMessage("没有对应的图片")
            return
        }
        ARouter.getInstance().build(RouterPath.IMAGE_LOCK)
                .withParcelableArrayList(RouterKey.FLAG_DATA, listDatas)
                .withInt(RouterKey.FLAG_POSITION, position)
                .withBoolean(RouterKey.FLAG_SHOW_DOWNLOAD, isShowDownload)
                .navigation(topActivity())
    }

    /**
     * 启动H5
     */
    fun startH5(url: String?, title: String? = null, otherParams: Map<String, Any?>? = null) {
        if (url.isNullOrEmpty()) {
            return
        }
        ARouter.getInstance().build(RouterPath.ACTIVITY_H5)
                .withString(RouterKey.FLAG_URL, url)
                .withString(RouterKey.FLAG_TITLE, title)
                .withSerializable(RouterKey.FLAG_DATA, otherParams as Serializable?)
                .navigation(topActivity())
    }
}
