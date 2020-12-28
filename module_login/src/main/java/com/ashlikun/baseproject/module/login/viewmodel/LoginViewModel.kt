package com.ashlikun.baseproject.module.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ashlikun.baseproject.common.utils.extend.showToast
import com.ashlikun.baseproject.module.login.mode.ApiLogin
import com.ashlikun.baseproject.libcore.utils.http.HttpCallbackHandle
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
    val userData: MutableLiveData<UserData> by lazy {
        get(UserData.javaClass) as MutableLiveData<UserData>
    }

    /**
     *用户登录	UserLogin	Mobile：手机号码
     * PassWord：密码
     */
    fun login() = launch {
        val handle = HttpCallbackHandle[this]
        ApiLogin.api.login(handle, phone, password)?.also {
            if (it.isSucceed) {
                it.getData().save()
                userData.postValue(it.getData())
            } else {
                it.showToast()
            }
        }
    }
}
