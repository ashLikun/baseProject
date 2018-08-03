package com.ashlikun.baseproject.module.debug;

import com.ashlikun.baseproject.libcore.BaseApplication;
import com.ashlikun.baseproject.module.main.ModuleApp;
import com.ashlikun.common.CommonApp;

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/25 10:49
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：用于单独运行登录模块的Application正式打包的时候是没有这个类
 */

public class ModuleDebugApp extends BaseApplication {

    @Override
    public void onCreate() {
        addApplication(new CommonApp());
        addApplication(new ModuleApp());
        super.onCreate();
    }
}
