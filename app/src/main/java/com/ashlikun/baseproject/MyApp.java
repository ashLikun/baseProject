package com.ashlikun.baseproject;

import android.content.res.Configuration;

import com.ashlikun.baseproject.libcore.BaseApplication;
import com.ashlikun.common.CommonApp;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　15:45
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：打包时候的Application
 */
public class MyApp extends BaseApplication {


    @Override
    public void onCreate() {
        //创建，每次新建一个module时候都要在这里添加
        addApplication(new CommonApp());
        addApplication(new com.ashlikun.baseproject.module.main.ModuleApp());
        addApplication(new com.ashlikun.baseproject.module.login.ModuleApp());
        addApplication(new com.ashlikun.baseproject.module.other.ModuleApp());
        super.onCreate();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
