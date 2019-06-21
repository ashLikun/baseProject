package com.ashlikun.baseproject.libcore.utils.extend

import androidx.fragment.app.Fragment
import com.ashlikun.baseproject.libcore.libarouter.RouterManage

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
fun Fragment.requestPermission(permission: Array<String>, showRationaleMessage: String? = null
                               , denied: (() -> Unit)? = null
                               , success: (() -> Unit)) {
    RouterManage.other()?.requestPermission(permission, showRationaleMessage, denied, success)
}