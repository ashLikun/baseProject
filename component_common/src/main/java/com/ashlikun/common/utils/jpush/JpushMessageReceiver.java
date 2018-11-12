package com.ashlikun.common.utils.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ashlikun.common.mode.javabean.JpushJsonData;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/27　9:26
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：极光推送的广播
 */

public class JpushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null || intent.getExtras() == null) {
            return;
        }
        String action = intent.getAction();
        JpushJsonData data = JpushJsonData.getJpushData(intent);
//        if (data != null && !JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action)) {
//            data.save();
//        }
//        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(action)) {
//            notifiation(context, data);
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(action)) {
//            notifiationOpened(context, data);
//            JpushJsonData.addOrRemove(false);
//        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/11/16 10:22
     * <p>
     * 方法功能： /**
     * 处理cn.jpush.android.intent.NOTIFICATION 广播
     * Required 用户接收SDK通知栏信息的intent
     */
    void notifiation(Context context, JpushJsonData data) {

    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/11/16 10:23
     * <p>
     * 方法功能：处理cn.jpush.android.intent.NOTIFICATION_OPENED广播
     * Required 用户打开自定义通知栏的intent
     */

    void notifiationOpened(Context context, JpushJsonData data) {
        JpushUtils.handlePush(context, data);
    }


}
