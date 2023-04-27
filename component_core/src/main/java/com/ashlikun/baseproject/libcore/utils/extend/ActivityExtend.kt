package com.ashlikun.baseproject.libcore.utils.extend

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.afollestad.materialdialogs.MaterialDialog
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.fragment.BaseFragment
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.core.registerForActivityResultX
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.utils.other.PermissionStatus
import com.ashlikun.utils.other.PermissionUtils
import com.ashlikun.utils.other.PermisstionSettingUtils
import com.ashlikun.utils.ui.extend.getActivity
import com.ashlikun.utils.ui.extend.resString
import com.ashlikun.utils.ui.extend.toastInfo

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
        PermissionUtils.savePerFlag(it)
        if (it.all { itt -> itt.value }) {
            success.invoke()
        } else {
            if (denied != null) denied?.invoke() else R.string.permission_denied.resString.toastInfo()
        }
    }
    val status = PermissionUtils.getStatus(this, *permission)

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
                if (status == PermissionStatus.REFUSE_PERMANENT) {
                    //永久拒绝,跳转到系统设置
                    PermisstionSettingUtils.start()
                } else {
                    launcher.launch(permission)
                }
            }
        }
    }

    //是否已经有权限
    if (status == PermissionStatus.SUCCESS) {
        success.invoke()
        launcher.unregister()
        return launcher
    } else if (isShowDialog && isFirstShowDialog) {
        //弹窗提示
        showRationaleDialog(message)
    } else if (isShowDialog && status == PermissionStatus.REFUSE) {
        //之前拒绝过,弹窗提升
        showRationaleDialog(message)
    } else if (status == PermissionStatus.REFUSE_PERMANENT) {
        //永久拒绝,跳转到系统设置
        PermisstionSettingUtils.start()
    } else {
        //未请求过
        launcher.launch(permission)
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