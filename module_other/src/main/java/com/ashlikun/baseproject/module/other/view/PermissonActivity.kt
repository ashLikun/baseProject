package com.ashlikun.baseproject.module.other.view

import android.content.Intent
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.core.activity.BaseActivity
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.PermissionUtils
import java.lang.ref.WeakReference

/**
 * 作者　　: 李坤
 * 创建时间: 2019/4/18　16:27
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：一个单独的页面请求权限
 */
/**
 * 成功,拒绝
 */
typealias OnPermisson = () -> Unit


class PermissonActivity : BaseActivity() {
    companion object {
        private var onSuccess: OnPermisson? = null
        private var onDenied: OnPermisson? = null

        fun setOnPermisson(onSuccess: OnPermisson, onDenied: OnPermisson? = null) {
            PermissonActivity.onSuccess = onSuccess
            if (onDenied != null) {
                PermissonActivity.onDenied = onDenied
            }
        }
    }

    var QUEST_PREMISSON: Array<String>? = null
    /**
     * 弹窗的文案
     */
    var showRationaleMessage: String? = null


    override fun getLayoutId() = R.layout.other_activity_permission
    override fun getStatusBarColor() = R.color.translucent
    override fun isStatusTranslucent() = true
    override fun parseIntent(intent: Intent?) {
        super.parseIntent(intent)
        QUEST_PREMISSON = intent?.getStringArrayExtra(RouterKey.FLAG_PERMISSION)
        showRationaleMessage = intent?.getStringExtra(RouterKey.FLAG_DATA)
    }

    override fun initView() {
        if (QUEST_PREMISSON.isNullOrEmpty()) {
            onSuccess()
            return
        }
        if (PermissionUtils.hasSelfPermissions(this, *QUEST_PREMISSON!!)) {
            onSuccess()
        } else {
            //是否之前拒绝过
            if (PermissionUtils.shouldShowRequestPermissionRationale(this, *QUEST_PREMISSON!!)) {
                showRationaleForCamera(MyPermissionRequest(this))
            } else {
                ActivityCompat.requestPermissions(this, QUEST_PREMISSON!!, REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> if (PermissionUtils.verifyPermissions(*grantResults)) {
                onSuccess()
            } else {
                onDenied()
            }
        }
    }

    /**
     * 弹窗提示
     */
    private fun showRationaleForCamera(request: PermissonActivity.MyPermissionRequest) {
        MaterialDialog(this)
                .cancelable(false)
                .show {
                    message(res = R.string.permission_rationale, text = showRationaleMessage)
                    positiveButton {
                        request.proceed()
                    }
                    negativeButton {
                        request.cancel()
                    }
                }
    }

    private class MyPermissionRequest(target: PermissonActivity) : PermissionRequest {
        private val weakTarget: WeakReference<PermissonActivity> = WeakReference(target)

        override fun proceed() {
            val target = weakTarget.get() ?: return
            ActivityCompat.requestPermissions(target, (target.QUEST_PREMISSON!!), target.REQUEST_CODE)
        }

        override fun cancel() {
            val target = weakTarget.get() ?: return
            target.onDenied()
        }
    }

    private fun onSuccess() {
        onSuccess?.invoke()
        finish()
    }

    private fun onDenied() {
        onDenied?.invoke()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        onSuccess = null
        onDenied = null
    }
}