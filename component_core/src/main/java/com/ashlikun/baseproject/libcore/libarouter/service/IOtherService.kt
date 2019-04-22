package com.ashlikun.baseproject.libcore.libarouter.service

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:43
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：Other模块的接口
 */
interface IOtherService : IProvider {

    /**
     * 请求权限
     */
    fun requestPermission(permission: Array<String>, showRationaleMessage: String? = null
                          , denied: (() -> Unit)? = null
                          , success: (() -> Unit))
}
