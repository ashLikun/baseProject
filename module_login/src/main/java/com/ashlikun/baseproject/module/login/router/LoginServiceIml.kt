package com.ashlikun.baseproject.module.login.router

import android.app.Activity
import android.content.Context

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.libarouter.service.ILoginService
import com.ashlikun.baseproject.module.login.mode.javabean.UserData

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:59
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：登录模块提供的接口
 */
@Route(path = RouterPath.SERVICE_LOGIN)
class LoginServiceIml : ILoginService {
    internal var context: Context? = null
    override fun init(context: Context) {
        this.context = context
    }

    override fun isLogin(): Boolean {
        return UserData.isLogin()
    }

    override fun getToken(): String {
        return UserData.getUserData()?.token ?: ""
    }

    override fun exitLogin(activity: Activity) {
        UserData.exitLogin(activity)
    }

    override fun exit() {
        UserData.exit()
    }

    override fun getUserId(): String {
        return UserData.getUserData()?.id ?: ""
    }


}
