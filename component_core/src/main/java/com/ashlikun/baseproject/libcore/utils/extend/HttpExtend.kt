package com.ashlikun.baseproject.libcore.utils.extend

import com.ashlikun.baseproject.libcore.utils.http.HttpCodeApp
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.ui.modal.SuperToast

/**
 * 作者　　: 李坤
 * 创建时间: 2019/3/1　12:21
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：http的相关扩展方法
 */
/**
 * 返回值的错误转化成 ContextData
 */
fun HttpResponse.contextData() = ContextData(title = message, errCode = code)
fun HttpResponse.isTokenError() = code == HttpCodeApp.TOKEN_ERROR
fun HttpResponse.isTokenExpire() = code == HttpCodeApp.ACCESS_TOKEN_EXPIRE
fun HttpResponse.isNoLogin() = code == HttpCodeApp.NO_LOGIN
fun HttpResponse.toast(default: String? = null, isShowInfo: ((code: Int) -> Boolean)? = null) {
    showToast(default, isShowInfo)
}

fun HttpResponse.message(default: String) = StringUtils.dataFilter(message, default)


fun HttpResponse.showToast(default: String? = null, isShowInfo: ((code: Int) -> Boolean)? = null) {
    val info = isShowInfo?.invoke(code) ?: isSucceed
    if (info) {
        SuperToast.showInfoMessage(message(default ?: ""))
    } else {
        SuperToast.showErrorMessage(message(default ?: ""))
    }
}
