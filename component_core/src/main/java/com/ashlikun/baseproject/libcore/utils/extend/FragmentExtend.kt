package com.ashlikun.baseproject.libcore.utils.extend

import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment

/**
 * 作者　　: 李坤
 * 创建时间: 2019/4/18　17:40
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Fragment相关扩展方法
 */

/**
 * @param isShowDialog 拒绝过是否显示对话框提示,false:会回调denied方法
 */
fun Fragment.requestPermission(
    permission: Array<String>,
    message: String? = null,
    title: String? = null,
    isShowDialog: Boolean = true,
    isFirstShowDialog: Boolean = false,
    denied: (() -> Unit)? = null,
    success: (() -> Unit)
): ActivityResultLauncher<Array<String>> {
    return requireActivity().requestPermission(permission, message, title, isShowDialog, isFirstShowDialog, denied, success)
}

fun Fragment.requestAllFilePermission(denied: (() -> Unit)? = null, success: () -> Unit) = requireActivity().requestAllFilePermission(denied, success)