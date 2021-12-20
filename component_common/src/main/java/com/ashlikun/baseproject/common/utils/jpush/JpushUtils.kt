package com.ashlikun.baseproject.common.utils.jpush

import android.app.Application
import android.content.Context
import cn.jpush.android.api.JPushInterface
import com.ashlikun.baseproject.common.mode.javabean.JpushJsonData
import com.ashlikun.baseproject.common.utils.jump.PullJumpManage
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.main.ActivityUtils
import com.ashlikun.utils.ui.modal.SuperToast

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
    var JPUSH_TAGS_SET_ID = 10088
    var JPUSH_TAGS_DELETE_ID = 10089

    /**
     * 推送点击后缓存的数据
     */
    private var cacheData: JpushJsonData? = null

    fun init(application: Application) {
        JPushInterface.setDebugMode(AppUtils.isDebug())
        JPushInterface.init(application)
        RouterManage.login()?.run {
            if (isLogin()) setAlias() else deleteAlias()
        }
    }

    fun deleteAlias() {
        JPushInterface.deleteAlias(AppUtils.app(), JPUSH_ALIAS_DELETE_ID)
    }

    fun setAlias() {
        RouterManage.login()?.run {
            if (isLogin()) {
                JPushInterface.setAlias(AppUtils.app(), JPUSH_ALIAS_SET_ID, getUserId())
            }
        }
    }

    fun setTags(tags: Set<String>) {
        RouterManage.login()?.run {
            if (isLogin()) {
                JPushInterface.setTags(AppUtils.app(), JPUSH_TAGS_SET_ID, tags)
            }
        }
    }

    fun deleteTags() {
        JPushInterface.deleteAlias(AppUtils.app(), JPUSH_TAGS_DELETE_ID)
    }

    fun clearAliasAndTags() {
        deleteAlias()
        deleteTags()
    }

    /**
     * 处理缓存的跳转数据,一般在首页处理
     *
     * @param context
     */
    fun handleCachePush(context: Context) {
        if (cacheData != null) {
            handlePush(context, cacheData!!)
            cacheData = null
        }
        //处理拉起App数据
        PullJumpManage.handleCachePush(context)
    }

    /**
     * 处理极光推送的通知
     *
     * @param context
     * @param data
     */
    fun handlePush(context: Context, data: JpushJsonData?) {
        if (data == null || data.type.isEmpty()) {
            SuperToast.get("无效的跳转").info()
            return
        }
        val runStatus = ActivityUtils.appBackgoundToForeground(context)
        if (runStatus == 2) {
            //app未启动
            cacheData = data
            RouterJump.startApp()
        } else {
            //跳转到对应的页面
            skip(context, data)

        }
    }

    fun showParamsError(): Unit {
        SuperToast.get("参数错误").info()
    }

    /**
     * 跳转到对应的页面
     *
     * @param context
     * @param data
     */
    fun skip(context: Context, data: JpushJsonData) {
        when (data.type) {
            //1跳转类型
            "1" -> {
                val id = data.getStringIdParams()
                if (!id.isNullOrEmpty()) {
                } else {
                    showParamsError()
                }
            }
        }
    }
}
