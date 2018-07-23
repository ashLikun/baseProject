package com.ashlikun.libcore.utils;

import android.app.Application;

import com.ashlikun.libarouter.ARouterManage;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/18　10:47
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class AppUtils {
    public static Application getApp() {
        return ARouterManage.get().getHomeService().getApp();
    }
}
