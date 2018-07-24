package com.ashlikun.baseproject.arouter;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.libarouter.constant.ARouterPath;
import com.ashlikun.libarouter.service.IHomeService;
import com.ashlikun.libcore.BaseApplication;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/4/4 0004　下午 4:07
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@Route(path = ARouterPath.SERVICE_HOME)
public class HomeService implements IHomeService {
    Context mContext;


    @Override
    public void init(Context context) {
        mContext = context;
    }


    @Override
    public Application getApp() {
        return BaseApplication.myApp;
    }


}
