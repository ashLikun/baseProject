package com.ashlikun.baseproject.core.arouter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.libarouter.constant.ARouterPath;
import com.ashlikun.libarouter.service.IHomeService;

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
    public void startHome(int index, String canshu) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("name", canshu);
        ARouter.getInstance().build(ARouterPath.HOME)
                .with(bundle)
                .withFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();
    }
}
