package com.ashlikun.baseproject.libcore.utils.extend

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.utils.other.ClassUtils
import permissions.dispatcher.PermissionUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2019/4/18　17:40
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Fragment相关扩展方法
 */
/**
 * 请求权限
 */
fun <I, O> Fragment.registerForActivityResultX(
        contract: ActivityResultContract<I, O>,
        callback: (O) -> Unit): ActivityResultLauncher<I> {
    var oldStatus: Lifecycle.State? = null
    //反射修改字段
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
        oldStatus = lifecycle.currentState
        ClassUtils.setFieldValue(lifecycle, "mState", Lifecycle.State.CREATED)
    }
    //这段注册代码源码里面做了限制，必须在onStart之前，所以反射修改字段，骗过注册
    val launcher = registerForActivityResult(contract) {
        callback.invoke(it)
    }
    if (oldStatus != null) {
        ClassUtils.setFieldValue(lifecycle, "mState", oldStatus)
    }
    return launcher
}

fun Fragment.requestPermission(permission: Array<String>, showRationaleMessage: String? = null, denied: (() -> Unit)? = null, success: (() -> Unit)): ActivityResultLauncher<Array<String>> {
    val launcher = registerForActivityResultX(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.all { itt -> itt.value }) {
            success.invoke()
        } else {
            denied?.invoke()
        }
    }

    //弹窗提示
    fun showRationaleDialog(showRationaleMessage: String? = null) {
        AlertDialog.Builder(context!!)
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
    if (PermissionUtils.hasSelfPermissions(context!!, *permission)) {
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