package com.ashlikun.common.utils.jpush

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.common.mode.javabean.JpushJsonData

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/27　9:26
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：极光推送的广播
 */

class JpushMessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null || intent.extras == null) {
            return
        }
//        val action = intent.action
        val data = JpushJsonData.getJpushData(intent)
        data?.save()
//         if (data != null && JPushInterface.ACTION_NOTIFICATION_OPENED != action) {
//            data.save()
//        }
//        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == action) {
//            notifiation(context, data)
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == action) {
//            notifiationOpened(context, data)
//            JpushJsonData.addOrRemove(false)
//        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/11/16 10:22
     *
     *
     * 方法功能：
     * 处理cn.jpush.android.intent.NOTIFICATION 广播
     * Required 用户接收SDK通知栏信息的intent
     */
    internal fun notifiation(context: Context, data: JpushJsonData?) {
        LogUtils.e("$context$data")
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/11/16 10:23
     *
     *
     * 方法功能：处理cn.jpush.android.intent.NOTIFICATION_OPENED广播
     * Required 用户打开自定义通知栏的intent
     */

    internal fun notifiationOpened(context: Context, data: JpushJsonData?) {
        JpushUtils.handlePush(context, data)
    }


}
