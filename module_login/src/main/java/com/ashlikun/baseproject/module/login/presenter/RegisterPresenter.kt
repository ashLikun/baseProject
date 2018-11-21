package com.ashlikun.baseproject.module.login.presenter

import com.ashlikun.baseproject.libcore.utils.http.HttpCallBack
import com.ashlikun.baseproject.module.login.iview.IBLoginView
import com.ashlikun.baseproject.module.login.mode.ApiLogin
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.core.BasePresenter
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.utils.ui.SuperToast

/**
 * @author　　: 李坤
 * 创建时间: 2018/11/20 15:21
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：注册的业务类
 */

class RegisterPresenter : BasePresenter<IBLoginView.IRegisterView>() {
    var phone = ""
    var password = ""
    var password2 = ""
    var code = ""
    var registerType = 1

    fun goRegister() {
        val buider = HttpCallBack.Buider[this]
        val callBack = object : HttpCallBack<HttpResult<UserData>>(buider) {
            override fun onSuccess(result: HttpResult<UserData>) {
                super.onSuccess(result, false)
                if (result.isSucceed && result.getData() != null) {
                    result.getData().save()
                    view.receiverUserData(result.getData())
                    SuperToast.showInfoMessage(result.getMessage())
                } else {
                    SuperToast.showWarningMessage(result.getMessage())
                }
            }
        }
        ApiLogin.api.register(registerType, phone, password, code, callBack)
    }

    fun sendMsg() {
        val buider = HttpCallBack.Buider[this]
        val callBack = object : HttpCallBack<HttpResult<*>>(buider) {
            override fun onSuccess(result: HttpResult<*>) {
                super.onSuccess(result)
                if (result.isSucceed) {
                    //                    new CountdownUtils.Bulider()
                    //                            .setMsg(mvpView.getContext().getResources().getString(R.string.get_yanzenma))
                    //                            .bulid()
                    //                            .start(sendButton);
                } else {
                    SuperToast.showErrorMessage(result.getMessage())
                }

            }
        }
        ApiLogin.api.sendMsg(phone, callBack)
    }

//    fun onClickView(view: View) {
//        if (getView().checkData()) {
//            if (!StringUtils.isEquals(password, password2)) {
//                SuperToast.showWarningMessage(AppUtils.getApp().resources.getString(R.string.login_inputCheckHint))
//            } else {
//                goRegister()
//            }
//        }
//    }
}
