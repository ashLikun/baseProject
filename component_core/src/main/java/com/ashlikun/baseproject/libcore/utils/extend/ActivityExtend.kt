package com.ashlikun.baseproject.libcore.utils.extend

import android.content.Context
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.core.BasePresenter
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.fragment.BaseFragment
import com.ashlikun.core.iview.IBaseView
import com.ashlikun.loadswitch.ContextData

/**
 * 作者　　: 李坤
 * 创建时间: 2019/4/18　17:40
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Activity相关扩展方法
 */
/**
 * 请求权限
 */
fun Context.requestPermission(permission: Array<String>, showRationaleMessage: String? = null
                          , denied: (() -> Unit)? = null
                          , success: (() -> Unit)) {
    RouterManage.other()?.requestPermission(permission, showRationaleMessage, denied, success)
}

fun BaseFragment.showEmpty(text: String = "什么都没有呢") {
    showEmpty(ContextData(text).setButtonShow(false))
}

fun BaseActivity.showEmpty(text: String = "什么都没有呢") {
    showEmpty(ContextData(text).setButtonShow(false))
}

fun BasePresenter<out IBaseView>.showEmpty(text: String = "什么都没有呢") {
    view.showEmpty(ContextData(text).setButtonShow(false))
}