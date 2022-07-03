package com.ashlikun.baseproject.libcore.router.service

import android.app.Activity

import com.alibaba.android.arouter.facade.template.IProvider
import com.ashlikun.utils.ui.fCActivity

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:43
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：登录模块提供的接口
 */
interface ILoginService : IProvider {
    /**
     * 是否登录
     *
     * @return
     */
    fun isLogin(): Boolean

    fun isLogin(isToLogin: Boolean): Boolean
    fun isLogin(isToLogin: Boolean, isShowToast: Boolean): Boolean

    /**
     * 获取token
     *
     * @return
     */
    fun getToken(): String

    fun getUserId(): String
    fun exitLogin()
    fun startLogin()
    fun exit()
    fun exitShowDialog(activity: Activity)
    fun getUserName(): String
    //当前是否正在登录页面
    fun isCurrentLogin(activity: Activity? = fCActivity): Boolean
}
