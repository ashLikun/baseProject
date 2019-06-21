package com.ashlikun.baseproject.module.debug;

import com.ashlikun.baseproject.libcore.BaseApplication;
import com.ashlikun.baseproject.module.login.ModuleApp;
import com.ashlikun.baseproject.common.CommonApp;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/12/14　15:50
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class ModuleDebugApp extends BaseApplication {
    @Override
    public void onCreate() {
        addApplication(new CommonApp());
        addApplication(new ModuleApp());
        super.onCreate();
    }
}
