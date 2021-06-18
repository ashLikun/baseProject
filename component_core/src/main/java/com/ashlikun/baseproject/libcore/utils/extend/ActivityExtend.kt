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
import com.ashlikun.utils.ui.StatusBarCompat
import com.ashlikun.utils.ui.extend.hineIme
import com.ashlikun.utils.ui.extend.showIme
import permissions.dispatcher.PermissionUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2019/4/18　17:40
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Activity相关扩展方法
 */

/**
 * 当前窗口亮度
 * 范围为0~1.0,1.0时为最亮，-1为系统默认设置
 */
var Activity.windowBrightness
    get() = window.attributes.screenBrightness
    set(brightness) {
        //小于0或大于1.0默认为系统亮度
        window.attributes = window.attributes.apply {
            screenBrightness = if (brightness >= 1.0 || brightness < 0) -1.0F else brightness
        }
    }

/**
 * 防止activity退出的时候动画重叠
 */
fun Activity.finishNoAnim() {
    overridePendingTransition(0, 0)
    finish()
}

/**
 * 关闭activity动画
 */
fun Activity.noAnim() {
    overridePendingTransition(0, 0)
}

/**
 * 关闭activity动画
 */
fun Activity.noExitAnim() {
    overridePendingTransition(R.anim.activity_open_enter, 0)
}

fun Activity.setStatusBarVisible(show: Boolean, statusBar: StatusBarCompat? = null) {
    if (show) {
        window.showIme()
    } else {
        window.hineIme()
    }
    statusBar?.setStatusDarkColor()
}

fun <I, O> ComponentActivity.registerForActivityResultX(
        contract: ActivityResultContract<I, O>,
        callback: (O) -> Unit): ActivityResultLauncher<I> {
    var oldStatus: Lifecycle.State? = null
    //反射修改字段
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
        oldStatus = lifecycle.currentState
        ClassUtils.setField(lifecycle, "mState", Lifecycle.State.CREATED)
    }
    //这段注册代码源码里面做了限制，必须在onStart之前，所以反射修改字段，骗过注册
    val launcher = registerForActivityResult(contract) {
        callback.invoke(it)
    }
    if (oldStatus != null) {
        ClassUtils.setField(lifecycle, "mState", oldStatus)
    }
    return launcher
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

/**
 * 启动activity,用新api registerForActivityResult
 */
fun ComponentActivity.launchForActivityResult(intent: Intent, checkCode: Boolean = true, success: ((ActivityResult) -> Unit)): ActivityResultLauncher<Intent> {
    val launcher = registerForActivityResultX(ActivityResultContracts.StartActivityForResult()) {
        if (!checkCode || it.resultCode == Activity.RESULT_OK) {
            success.invoke(it)
        }
    }
    launcher.launch(intent)
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