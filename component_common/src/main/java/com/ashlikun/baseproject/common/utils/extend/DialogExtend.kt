package com.ashlikun.baseproject.common.utils.extend

import androidx.appcompat.app.AlertDialog

/**
 * 作者　　: 李坤
 * 创建时间: 2020/4/28　18:03
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
fun AlertDialog.Builder.setPositiveButtonX(
    text: String = "确定",
    function: ((dialog: AlertDialog) -> Unit)? = null
): AlertDialog.Builder {
    return setPositiveButton(text) { dialog, which ->
        function?.invoke(dialog as AlertDialog)
    }
}

fun AlertDialog.Builder.setNegativeButtonX(
    text: String = "取消",
    function: ((dialog: AlertDialog) -> Unit)? = null
): AlertDialog.Builder {
    return setNegativeButton(text) { dialog, which ->
        function?.invoke(dialog as AlertDialog)
    }
}