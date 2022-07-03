package com.ashlikun.baseproject.module.login.router

import android.app.Activity
import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.router.service.ILoginService
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.baseproject.module.login.view.activity.LoginActivity

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
    override fun startLogin() {
        RouterJump.startLogin()
    }

    override fun getUserName(): String = UserData.userData?.userName ?: ""
    override fun isCurrentLogin(activity: Activity?) =
        activity?.javaClass == LoginActivity::class.java

    internal var context: Context? = null
    override fun init(context: Context) {
        this.context = context
    }

    override fun isLogin(): Boolean {
        return UserData.isLogin()
    }

    override fun isLogin(isToLogin: Boolean): Boolean {
        return UserData.isLogin(isToLogin)
    }

    override fun isLogin(isToLogin: Boolean, isShowToast: Boolean): Boolean {
        return UserData.isLogin(isToLogin, isShowToast)
    }

    override fun getToken(): String {
        return UserData.userData?.token ?: ""
    }

    /**
     * 退出登录并且起首页
     */
    override fun exitLogin() {
        UserData.exitLogin()
    }

    /**
     * 只是退出登录
     */
    override fun exit() {
        UserData.exit()
    }

    /**
     * 退出登录显示对话框
     */
    override fun exitShowDialog(activity: Activity) {
        UserData.exit(activity)
    }

    override fun getUserId(): String {
        return UserData.userData?.id ?: ""
    }


}
