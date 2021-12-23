package com.ashlikun.baseproject.common.utils.jump

import android.content.Context
import android.content.Intent
import com.ashlikun.baseproject.common.mode.javabean.JpushJsonData
import com.ashlikun.baseproject.common.utils.jpush.JpushUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2018/8/27　9:40
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：拉起app跳转的管理器
 */
object PullJumpManage {
    /**
     * 缓存的数据，一般首页处理
     */
    private var cacheData: JpushJsonData? = null

    /**
     * 处理缓存的跳转数据,一般在首页处理
     *
     * @param context
     */
    fun handleCachePush(context: Context) {
        jump(context, cacheData)
    }

    private fun jump(context: Context, data: JpushJsonData?) {
        cacheData = null
        //统一跳转逻辑
        data?.let {
            JpushUtils.handlePush(context, data)
        }
    }

    /**
     * 检查这个意图是否正确
     *
     * @param intent 启动时候的意图
     */
    fun check(intent: Intent?): Boolean {
        intent?.data?.run {
            return intent.data?.host?.equals("com.baseproject") ?: false
        }
        return false

    }

    fun save(intent: Intent?) {
        if (!check(intent)) {
            return
        }
        intent?.data?.run {
            val json = getQueryParameter("data") ?: ""
            val data = JpushJsonData.jsonParse(json)
            if (data != null) {
                //缓存这个数据
                cacheData = data
            }
        }

    }
}
