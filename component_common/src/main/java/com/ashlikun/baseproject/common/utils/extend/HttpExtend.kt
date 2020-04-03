package com.ogow.libs.utils.extend

import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.ui.SuperToast
import com.ashlikun.utils.ui.ToastUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2019/3/18　11:42
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：http相关的扩展函数
 */

fun HttpResponse.toast(default: String? = null) =
        if (isSucceed) SuperToast.showInfoMessage(message(default ?: ""))
        else SuperToast.showErrorMessage(message(default ?: ""))

fun HttpResponse.message(default: String) = StringUtils.dataFilter(getMessage(), default)
fun HttpResponse.getContextData() = ContextData(code, message("出错啦"))


////登录异常
//fun HttpResponse.isLogout() = code == HttpCodeApp.NO_DATA_ERROR || code == HttpCode.ERROR_CODE_LOGOUT_2
//
////sessionid错误
//fun HttpResponse.isSessionidError() = code == HttpCode.ERROR_CODE_SESSION
//
////签名失效,加密接口才会用到
//fun HttpResponse.isSignError() = code == HttpCode.ERROR_CODE_SIGN_INVALID