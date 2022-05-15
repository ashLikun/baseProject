package com.ashlikun.baseproject.libcore.utils.extend

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.core.registerForActivityResultX
import permissions.dispatcher.PermissionUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2019/4/18　17:40
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Fragment相关扩展方法
 */

/**
 * @param isDeniedShowDialog 拒绝过是否显示对话框提示,false:会回调denied方法
 */
fun Fragment.requestPermission(
    permission: Array<String>,
    showRationaleMessage: String? = null,
    isDeniedShowDialog: Boolean = true,
    denied: (() -> Unit)? = null,
    success: (() -> Unit)
): ActivityResultLauncher<Array<String>> {
    return requireActivity().requestPermission(
        permission,
        showRationaleMessage,
        isDeniedShowDialog,
        denied,
        success
    )
}

