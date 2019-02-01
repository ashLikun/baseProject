package com.ashlikun.baseproject.module.login.presenter


import android.os.Bundle
import com.ashlikun.baseproject.module.login.iview.IBLoginView
import com.ashlikun.baseproject.module.login.mode.ApiLogin
import com.ashlikun.core.BasePresenter
import com.ashlikun.baseproject.libcore.utils.http.HttpCallbackHandle

/**
 * @author　　: 李坤
 * 创建时间: 2018/11/20 15:17
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：登陆业务
 */

class LoginPresenter : BasePresenter<IBLoginView.IloginView>() {
    var phone = ""
    var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/10 11:25
     *
     *
     * 方法功能：用户登录	UserLogin	Mobile：手机号码
     * PassWord：密码
     */
    fun login() {
        val callbackHandle = HttpCallbackHandle[this]
                .setShowLoadding(true)
        ApiLogin.api.login(phone, password, callbackHandle) { result ->
            result.getData().save()
            view.login(result.getData())
        }
    }


//    fun onLoginClick(v: View) {
//        if (view.checkData()) {
//            login()
//        }
//    }
}
