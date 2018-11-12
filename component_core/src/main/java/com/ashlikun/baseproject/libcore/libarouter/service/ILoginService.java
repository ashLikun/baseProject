package com.ashlikun.baseproject.libcore.libarouter.service;

import android.app.Activity;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:43
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：登录模块提供的接口
 */
public interface ILoginService extends IProvider {
    /**
     * 是否登录
     *
     * @return
     */
    boolean isLogin();

    /**
     * 获取token
     *
     * @return
     */
    String getToken();

    void exitLogin(Activity activity);

    void exit();

    String getUserId();
}
