package com.nmlg.renrenying.libcore.utils.extend

import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.ui.SuperToast

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
fun HttpResponse.contextData() = ContextData(code, message)

fun HttpResponse.showToast() {
    if (isSucceed) {
        SuperToast.showInfoMessage(message)
    } else {
        SuperToast.showErrorMessage(message)
    }
}