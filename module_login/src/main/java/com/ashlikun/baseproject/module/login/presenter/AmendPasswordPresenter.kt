package com.ashlikun.baseproject.module.login.presenter

import android.os.Bundle
import com.ashlikun.baseproject.module.login.iview.IBLoginView
import com.ashlikun.baseproject.module.login.mode.ApiLogin
import com.ashlikun.core.BasePresenter
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.ui.SuperToast
import com.lingyun.client.libcore.utils.http.HttpCallBack
import com.lingyun.client.libcore.utils.http.HttpCallbackHandle

/**
 * @author　　: 李坤
 * 创建时间: 2018/11/20 14:59
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：忘记密码的业务
 */

class AmendPasswordPresenter : BasePresenter<IBLoginView.IAmendPasswordView>() {
    var phone: String = ""
    var code: String = ""
    var password1: String = ""
    var password2: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/10 13:45
     *
     *
     * 方法功能：
     */
    fun updateUserPwd() {
        if (!StringUtils.isEquals(password1, password2)) {
            SuperToast.showWarningMessage("两次密码不一致")
            return
        }
        val buider = HttpCallbackHandle[this]
        val callBack = object : HttpCallBack<HttpResult<*>>(buider) {
            override fun onSuccess(result: HttpResult<*>) {
                super.onSuccess(result)
                if (result.isSucceed) {
                    SuperToast.showInfoMessage(result.getMessage())
                    view.finish()
                } else {
                    SuperToast.showErrorMessage(result.getMessage())
                }
            }
        }
        ApiLogin.api.upDataPassword(phone, password1, code, callBack)
    }


    fun sendMsg() {
        val buider = HttpCallbackHandle[this]
        val callBack = object : HttpCallBack<HttpResult<*>>(buider) {
            override fun onSuccess(result: HttpResult<*>) {
                super.onSuccess(result)
                if (result.isSucceed) {
                } else {
                    SuperToast.showErrorMessage(result.getMessage())
                }

            }
        }
        ApiLogin.api.sendMsg(phone, callBack)
    }
}
