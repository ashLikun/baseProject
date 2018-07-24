package com.ashlikun.module.login;

import android.app.Activity;
import android.content.Context;

import com.ashlikun.libarouter.service.ILoginService;
import com.ashlikun.module.login.mode.javaben.UserData;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:59
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：登录模块提供的接口
 */
public class LoginServiceIml implements ILoginService {
    Context context;

    @Override
    public boolean isLogin() {
        return UserData.isLogin();
    }

    @Override
    public String getToken() {
        if (UserData.getUserData() != null) {
            return UserData.getUserData().getToken();
        }
        return "";
    }

    @Override
    public void exitLogin(Activity activity) {
        UserData.exitLogin(activity);
    }

    @Override
    public void exit() {
        UserData.exit();
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }
}
