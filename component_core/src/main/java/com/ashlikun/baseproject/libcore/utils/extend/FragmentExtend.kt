package com.ashlikun.baseproject.libcore.utils.extend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.libcore.utils.permisson.PermissonFragment
import com.ashlikun.baseproject.libcore.utils.permisson.PermissonResult

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

fun Fragment.requestPermission(permission: Array<String>, showRationaleMessage: String = ""
                               , denied: (() -> Unit)? = null
                               , success: (() -> Unit)) {
    PermissonFragment.request(this, permission, showRationaleMessage).observe(this, Observer {
        when (it) {
            PermissonResult.SUCCESS -> success.invoke()
            PermissonResult.DENIED -> denied?.invoke()
        }
    })
}