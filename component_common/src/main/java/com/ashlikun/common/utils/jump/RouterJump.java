package com.ashlikun.common.utils.jump;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.baseproject.libcore.constant.RouterKey;
import com.ashlikun.baseproject.libcore.constant.RouterPath;
import com.ashlikun.utils.ui.ActivityManager;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:36
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：路由跳转的工具类
 */
public class RouterJump {
    public static Activity topActivity() {
        return ActivityManager.getInstance().currentActivity();
    }

    public static void startHome() {
        startHome(-1);
    }

    /**
     * 启动首页，如果已经启动会清空上面的activity
     *
     * @param index -1:默认页
     */
    public static void startHome(int index) {
        ARouter.getInstance().build(RouterPath.HOME)
                .withInt(RouterKey.FLAG_INDEX, index)
                .withFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .greenChannel()
                .navigation(topActivity());
    }

    /**
     * 返回登录页面
     */
    public static void startLogin() {
        ARouter.getInstance().build(RouterPath.LOGIN)
                .greenChannel()
                .navigation(topActivity());
    }
}
