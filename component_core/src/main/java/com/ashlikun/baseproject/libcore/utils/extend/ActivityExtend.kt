package com.ashlikun.baseproject.libcore.utils.extend

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.fragment.BaseFragment
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.loadswitch.LoadSwitchService
import com.ashlikun.utils.other.ClassUtils
import com.ashlikun.utils.ui.extend.hineIme
import com.ashlikun.utils.ui.extend.showIme
import com.ashlikun.utils.ui.status.StatusBarCompat
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


/**
 * 请求权限
 */
fun ComponentActivity.requestPermission(permission: Array<String>, showRationaleMessage: String? = null, denied: (() -> Unit)? = null, success: (() -> Unit)): ActivityResultLauncher<Array<String>> {

    val launcher = registerForActivityResultX(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.all { itt -> itt.value }) {
            success.invoke()
        } else {
            denied?.invoke()
        }
    }

    //弹窗提示
    fun showRationaleDialog(showRationaleMessage: String? = null) {
        AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("权限申请")
                .setMessage(showRationaleMessage ?: getString(R.string.permission_rationale))
                .setPositiveButton("确认") { dialoog, which ->
                    launcher.launch(permission)
                }
                .setNegativeButton("取消") { dialog, which ->
                    denied?.invoke()
                }
                .show()
    }
    //是否已经有权限
    if (PermissionUtils.hasSelfPermissions(this, *permission)) {
        success.invoke()
        return launcher
    } else {
        //是否之前拒绝过
        if (PermissionUtils.shouldShowRequestPermissionRationale(this, *permission)) {
            showRationaleDialog(showRationaleMessage)
        } else {
            //请求权限
            launcher.launch(permission)
        }
    }
    return launcher
}


fun BaseFragment.showEmpty(text: String = "什么都没有呢") {
    showEmpty(ContextData(text).setButtonShow(false))
}

fun BaseActivity.showEmpty(text: String = "什么都没有呢") {
    showEmpty(ContextData(text).setButtonShow(false))
}

fun LoadSwitchService.showEmpty(text: String = "什么都没有呢") {
    showEmpty(ContextData(text).setButtonShow(false))
}

fun BaseViewModel.showEmpty(text: String = "什么都没有呢") {
    showEmpty(ContextData(text).setButtonShow(false))
}