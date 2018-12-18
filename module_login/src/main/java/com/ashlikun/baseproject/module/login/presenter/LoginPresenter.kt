package com.ashlikun.baseproject.module.login.presenter


import android.os.Bundle
import android.support.v4.content.FileProvider
import com.ashlikun.baseproject.libcore.utils.http.HttpCallBack
import com.ashlikun.baseproject.module.login.iview.IBLoginView
import com.ashlikun.baseproject.module.login.mode.ApiLogin
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.core.BasePresenter
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.utils.ui.SuperToast

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
        val buider = HttpCallBack.Buider[this].setShowLoadding(true)
        val callBack = object : HttpCallBack<HttpResult<UserData>>(buider) {
            override fun onSuccess(result: HttpResult<UserData>) {
                super.onSuccess(result)
                if (result.isSucceed && result.getData() != null) {
                    result.getData().save()
                    view.login(result.getData())
                } else {
                    SuperToast.showErrorMessage(result.getMessage())
                }
            }
        }
        ApiLogin.api.login(phone, password, callBack)
    }


//    fun onLoginClick(v: View) {
//        if (view.checkData()) {
//            login()
//        }
//    }
}
