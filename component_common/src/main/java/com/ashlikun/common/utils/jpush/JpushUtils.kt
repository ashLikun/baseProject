package com.ashlikun.common.utils.jpush

import android.app.Application
import android.content.Context
import android.content.Intent

import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.common.mode.javabean.JpushJsonData
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.main.ActivityUtils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.SharedPreUtils
import com.ashlikun.utils.ui.SuperToast

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/27　13:18
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */

object JpushUtils {
    var JPUSH_ALIAS_SET_ID = 10086
    var JPUSH_ALIAS_DELETE_ID = 10087
    /**
     * 推送点击后缓存的数据
     */
    private var cacheData: JpushJsonData? = null

    val number: Int
        get() = SharedPreUtils.getInt(AppUtils.getApp(), "JPUSH_NUMBER")

    fun init(application: Application) {
//        JPushInterface.setDebugMode(BuildConfig.DEBUG)
//        JPushInterface.init(application)
        RouterManage.getLogin().run {
            if (isLogin()) else deleteAlias()
        }
    }

    fun deleteAlias() {
//        JPushInterface.deleteAlias(AppUtils.getApp(), JPUSH_ALIAS_DELETE_ID)
    }

    fun setAlias() {
        RouterManage.getLogin().run {
            if (isLogin()) {
//                JPushInterface.setAlias(AppUtils.getApp(), JPUSH_ALIAS_SET_ID, getUserId())
            }
        }
    }

    /**
     * 处理缓存的跳转数据,一般在首页处理
     *
     * @param context
     */
    fun handleCachePush(context: Context) {
        if (cacheData != null) {
            skip(context, cacheData!!)
            cacheData = null
        }
    }

    /**
     * 处理极光推送的通知
     *
     * @param context
     * @param data
     */
    fun handlePush(context: Context, data: JpushJsonData?) {
        if (data == null || data.type <= 0) {
            SuperToast.get("无效的推送").info()
            return
        }
        val runStatus = ActivityUtils.appBackgoundToForeground(context)
        if (runStatus == 2) {
            //app未启动
            cacheData = data
            ARouter.getInstance().build(RouterPath.WELCOME)
                    .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .navigation(context)
        } else {
            //跳转到对应的页面
            skip(context, data)
        }
    }

    /**
     * 跳转到对应的页面
     *
     * @param context
     * @param data
     */
    fun skip(context: Context, data: JpushJsonData) {
        LogUtils.e("$context$data")
    }
}
