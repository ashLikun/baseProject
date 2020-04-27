package com.ashlikun.baseproject.libcore.utils.extend

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.libcore.utils.permisson.PermissonFragment
import com.ashlikun.baseproject.libcore.utils.permisson.PermissonResult
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.fragment.BaseFragment
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.utils.ui.StatusBarCompat

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

fun Activity.setStatusBarVisible(show: Boolean, statusBar: StatusBarCompat? = null) {
    if (show) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    } else {
        var uiFlags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        window.decorView.systemUiVisibility = uiFlags
    }
    statusBar?.setStatusDarkColor()
}


/**
 * 请求权限
 */
fun FragmentActivity.requestPermission(permission: Array<String>, showRationaleMessage: String? = null
                                       , denied: (() -> Unit)? = null
                                       , success: (() -> Unit)) {
    PermissonFragment.request(this, permission, showRationaleMessage).observe(this, Observer {
        when (it) {
            PermissonResult.SUCCESS -> success.invoke()
            PermissonResult.DENIED -> denied?.invoke()
        }
    })
}

fun BaseFragment.showEmpty(text: String = "什么都没有呢") {
    showEmpty(ContextData(text).setButtonShow(false))
}

fun BaseActivity.showEmpty(text: String = "什么都没有呢") {
    showEmpty(ContextData(text).setButtonShow(false))
}

fun BaseViewModel.showEmpty(text: String = "什么都没有呢") {
    showEmpty(ContextData(text).setButtonShow(false))
}