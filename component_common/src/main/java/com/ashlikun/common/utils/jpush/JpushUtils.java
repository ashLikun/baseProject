package com.ashlikun.common.utils.jpush;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.baseproject.libcore.constant.RouterPath;
import com.ashlikun.common.mode.javabean.JpushJsonData;
import com.ashlikun.utils.AppUtils;
import com.ashlikun.utils.main.ActivityUtils;
import com.ashlikun.utils.other.SharedPreUtils;
import com.ashlikun.utils.ui.SuperToast;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/10/27　13:18
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class JpushUtils {
    public static int JPUSH_ALIAS_SET_ID = 10086;
    public static int JPUSH_ALIAS_DELETE_ID = 10087;
    /**
     * 推送点击后缓存的数据
     */
    private static JpushJsonData cacheData;

    public static void init(Application application) {
//        JPushInterface.setDebugMode(BuildConfig.DEBUG);
//        JPushInterface.init(application);
//        if (RouterManage.getLogin().isLogin()) {
//            setAlias();
//        } else {
//            deleteAlias();
//        }
    }

    public static void deleteAlias() {
//        JPushInterface.deleteAlias(AppUtils.getApp(), JPUSH_ALIAS_DELETE_ID);
    }

    public static void setAlias() {
//        if (RouterManage.getLogin().isLogin()) {
//            JPushInterface.setAlias(AppUtils.getApp(), JPUSH_ALIAS_SET_ID, RouterManage.getLogin().getUserId());
//        }
    }

    public static int getNumber() {
        return SharedPreUtils.getInt(AppUtils.getApp(), "JPUSH_NUMBER");
    }

    /**
     * 处理缓存的跳转数据,一般在首页处理
     *
     * @param context
     */
    public static void handleCachePush(Context context) {
        if (cacheData != null) {
            skip(context, cacheData);
            cacheData = null;
        }
    }

    /**
     * 处理极光推送的通知
     *
     * @param context
     * @param data
     */
    public static void handlePush(Context context, JpushJsonData data) {
        if (data == null || data.type <= 0) {
            SuperToast.get("无效的推送").info();
            return;
        }
        int runStatus = ActivityUtils.appBackgoundToForeground(context);
        if (runStatus == 2) {
            //app未启动
            cacheData = data;
            ARouter.getInstance().build(RouterPath.WELCOME)
                    .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .navigation(context);
        } else {
            //跳转到对应的页面
            skip(context, data);
        }
    }

    /**
     * 跳转到对应的页面
     *
     * @param context
     * @param data
     */
    public static void skip(Context context, JpushJsonData data) {
    }
}
