package com.ashlikun.libarouter.jump;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.libarouter.constant.RouterPath;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:36
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：路由跳转的工具类
 */
public class RouterJump {
    public static void startHome(int index, String canshu) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("name", canshu);
        ARouter.getInstance().build(RouterPath.HOME)
                .with(bundle)
                .withFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();
    }

    public static void startTest() {
        ARouter.getInstance().build(RouterPath.TEST)
                .navigation();
    }

    /**
     * 返回登录页面
     */
    public static void startLogin() {
        ARouter.getInstance().build(RouterPath.LOGIN)
                .navigation();
    }
}
