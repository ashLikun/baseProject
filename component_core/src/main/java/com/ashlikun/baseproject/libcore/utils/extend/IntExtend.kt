package com.ashlikun.baseproject.libcore.utils.extend

import com.ashlikun.utils.ui.SuperToast

/**
 * 作者　　: 李坤
 * 创建时间: 2020/11/11　13:33
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
inline fun Int?.isNullOrEmpty() = this == null || this == 0
fun Int?.checkNullOrEmpty(message:String = "参数错误"): Boolean {
    if (isNullOrEmpty()) {
        SuperToast.showErrorMessage(message)
        return false
    }
    return true
}