package com.ashlikun.baseproject.libcore.utils.extend

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.fragment.BaseFragment
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.core.registerForActivityResultX
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.utils.ui.extend.getActivity
import com.ashlikun.utils.ui.extend.resString
import com.ashlikun.utils.ui.extend.toastInfo
import permissions.dispatcher.PermissionUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2019/4/18　17:40
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Activity相关扩展方法
 */


/**
 * 关闭activity动画
 */
fun Activity.noExitAnim() {
    overridePendingTransition(R.anim.activity_open_enter, 0)
}

fun Context.requestPermission(
    permission: Array<String>,
    message: String? = null,
    title: String? = null,
    isShowDialog: Boolean = true,
    isFirstShowDialog: Boolean = false,
    denied: (() -> Unit)? = null,
    success: (() -> Unit)
): ActivityResultLauncher<Array<String>> {
    return (this.getActivity() as ComponentActivity).requestPermission(permission, message, title, isShowDialog, isFirstShowDialog, denied, success)
}

/**
 * 请求权限
 * @param isShowDialog 是否显示对话框
 * @param isFirstShowDialog 是否先弹框，后再请求权限
 */
fun ComponentActivity.requestPermission(
    permission: Array<String>,
    message: String? = null,
    title: String? = null,
    isShowDialog: Boolean = true,
    isFirstShowDialog: Boolean = false,
    denied: (() -> Unit)? = null,
    success: (() -> Unit)
): ActivityResultLauncher<Array<String>> {
    var launcher = registerForActivityResultX(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.all { itt -> itt.value }) {
            success.invoke()
        } else {
            if (denied != null) denied?.invoke() else R.string.permission_denied.resString.toastInfo()
        }
    }

    //弹窗提示
    fun showRationaleDialog(message: String? = null) {

        MaterialDialog(this).show {
            cancelable(false)
            title(text = title ?: R.string.permission_rationale_title.resString)
            message(text = message ?: R.string.permission_rationale.resString)
            negativeButton(R.string.base_dialog_cancel) {
                if (denied != null) denied?.invoke() else R.string.permission_denied.resString.toastInfo()
                launcher.unregister()
            }
            positiveButton(R.string.base_dialog_confirm) { dia ->
                launcher.launch(permission)
            }
        }
    }

    //是否已经有权限
    if (PermissionUtils.hasSelfPermissions(this, *permission)) {
        success.invoke()
        launcher.unregister()
        return launcher
    } else {
        //弹窗提示
        if (isShowDialog && isFirstShowDialog) {
            showRationaleDialog(message)
        } else {
            //是否之前拒绝过
            if (isShowDialog && PermissionUtils.shouldShowRequestPermissionRationale(this, *permission)) {
                showRationaleDialog(message)
            } else {
                launcher.launch(permission)
            }
        }
    }
    return launcher
}


fun BaseFragment.showEmpty(text: String = R.string.loadswitch_empty_text.resString) {
    showEmpty(ContextData(title = text, buttonText = ""))
}

fun BaseActivity.showEmpty(text: String = R.string.loadswitch_empty_text.resString) {
    showEmpty(ContextData(title = text, buttonText = ""))
}

fun LoadSwitchService.showEmpty(text: String = R.string.loadswitch_empty_text.resString) {
    showEmpty(ContextData(title = text, buttonText = ""))
}

fun BaseViewModel.showEmpty(text: String = R.string.loadswitch_empty_text.resString) {
    showEmpty(ContextData(title = text, buttonText = ""))
}