package com.ashlikun.libarouter.jump;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.libarouter.constant.ARouterPath;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/4/9 0009　下午 1:10
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：全局的跳转
 */

public class ARouterJump {

    public static void startHome(int index, String canshu) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("name", canshu);
        ARouter.getInstance().build(ARouterPath.HOME)
                .with(bundle)
                .withFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();
    }

    public static void startTest() {
        ARouter.getInstance().build(ARouterPath.TEST)
                .navigation();
    }
}
