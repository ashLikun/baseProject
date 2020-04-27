package com.ashlikun.baseproject.libcore.utils.permisson

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.solver.ArrayLinkedVariables
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.baseproject.libcore.constant.RouterPath
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.PermissionUtils
import java.lang.ref.WeakReference

/**
 * 作者　　: 李坤
 * 创建时间: 2020/4/26　16:29
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：请求权限的Fragment
 */
class PermissonFragment : Fragment() {
    companion object {
        private const val REQUEST_CODE = 888
        const val TAG = "x_permissions"
        fun request(activity: FragmentActivity, permissions: Array<String>, showRationaleMessage: String? = null) = request(activity.supportFragmentManager, permissions, showRationaleMessage)
        fun request(fragment: Fragment, permissions: Array<String>, showRationaleMessage: String? = null) = request(fragment.childFragmentManager, permissions, showRationaleMessage)

        @Synchronized
        fun getFragment(fragmentManager: FragmentManager) =
                if (fragmentManager.findFragmentByTag(TAG) == null) PermissonFragment().run {
                    fragmentManager.beginTransaction().add(this, TAG).commitNow()
                    this
                } else fragmentManager.findFragmentByTag(TAG) as PermissonFragment

        private fun request(fragmentManager: FragmentManager, permissions: Array<String>, showRationaleMessage: String? = null): MutableLiveData<PermissonResult> {
            var fragment = getFragment(fragmentManager)
            fragment.request(permissions, showRationaleMessage)
            return fragment!!.liveData
        }

    }

    lateinit var liveData: MutableLiveData<PermissonResult>

    lateinit var QUEST_PREMISSON: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //旋转屏幕时保存Fragment状态
        retainInstance = true
    }

    /**
     * @param showRationaleMessage 弹窗的文案
     */
    fun request(permissions: Array<String>, showRationaleMessage: String? = null) {
        if (permissions.isNullOrEmpty()) {
            onSuccess()
            return
        }
        QUEST_PREMISSON = permissions
        liveData = MutableLiveData()
        if (PermissionUtils.hasSelfPermissions(context, *QUEST_PREMISSON)) {
            onSuccess()
        } else {
            //是否之前拒绝过
            if (PermissionUtils.shouldShowRequestPermissionRationale(this, *QUEST_PREMISSON)) {
                showRationaleDialog(MyPermissionRequest(this), showRationaleMessage)
            } else {
                //请求权限
                requestPermissions(QUEST_PREMISSON, REQUEST_CODE)
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
    private fun showRationaleDialog(request: MyPermissionRequest, showRationaleMessage: String?) {
        MaterialDialog(context!!)
                .cancelable(false)
                .show {
                    message(text = showRationaleMessage ?: getString(R.string.permission_rationale))
                    positiveButton {
                        request.proceed()
                    }
                    negativeButton {
                        request.cancel()
                    }
                }
    }

    private class MyPermissionRequest(target: PermissonFragment) : PermissionRequest {
        private val weakTarget: WeakReference<PermissonFragment> = WeakReference(target)

        override fun proceed() {
            val target = weakTarget.get() ?: return
            target.requestPermissions(target.QUEST_PREMISSON, REQUEST_CODE)
        }

        override fun cancel() {
            val target = weakTarget.get() ?: return
            target.onDenied()
        }
    }

    private fun onSuccess() {
        liveData.value = PermissonResult.SUCCESS
    }

    private fun onDenied() {
        liveData.value = PermissonResult.DENIED
    }


}

enum class PermissonResult {
    //成功
    SUCCESS,

    //拒绝
    DENIED
}