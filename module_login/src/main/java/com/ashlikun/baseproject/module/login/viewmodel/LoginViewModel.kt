package com.ashlikun.baseproject.module.login.viewmodel

import com.ashlikun.baseproject.libcore.utils.extend.showToast
import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.baseproject.module.login.mode.ApiLogin
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.core.mvvm.launch

/**
 * @author　　: 李坤
 * 创建时间: 2018/11/20 15:17
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：登陆业务
 */
class LoginViewModel : BaseViewModel() {
    var phone = ""
    var password = ""
    val userData = get<UserData>()

    /**
     *用户登录	UserLogin	Mobile：手机号码
     * PassWord：密码
     */
    fun login() = launch {
        val handle = HttpUiHandle[this]
        ApiLogin.api.login(handle, phone, password)?.also {
            if (it.isSucceed) {
                it.data?.run {
                    save()
                    userData.value = this
                }
            } else {
                it.showToast()
            }
        }
    }
}
