package com.ashlikun.baseproject.module.login.presenter

import android.os.Bundle
import com.ashlikun.baseproject.module.login.iview.IBLoginView
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.core.BasePresenter
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.ui.SuperToast
import com.ashlikun.baseproject.libcore.utils.http.HttpCallBack
import com.ashlikun.baseproject.libcore.utils.http.HttpCallbackHandle

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/31 0031  23:12
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：更新密码
 */
class UpDataPasswordPresenter : BasePresenter<IBLoginView.IUpDataPasswordView>() {
    var oldPassword = ""
    var password1 = ""
    var password2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/10 13:45
     *
     *
     * 方法功能：UpdateUserPwd	NewPwd：新密码
     * AgainPwd：确认密码
     */
    fun updateUserPwd() {
        if (!UserData.isLogin()) {
            return
        }
        if (!StringUtils.isEquals(password1, password2)) {
            SuperToast.showWarningMessage("两次密码不一致")
            return
        }
        val buider = HttpCallbackHandle[this]
                .setShowLoadding(false)
                .setLoadSwitchService(view.switchService)
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
        //        addHttpCall(ApiService.getApi().setPassword(oldPassword, password1, password2, httpCallBack));
    }
}
