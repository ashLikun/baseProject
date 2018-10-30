package com.ashlikun.baseproject.module.main.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.baseproject.libcore.constant.RouterPath;
import com.ashlikun.baseproject.libcore.libarouter.service.IHomeService;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/4/4 0004　下午 4:07
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@Route(path = RouterPath.SERVICE_HOME)
public class HomeServiceIml implements IHomeService {
    Context mContext;


    @Override
    public void init(Context context) {
        mContext = context;
    }



}
