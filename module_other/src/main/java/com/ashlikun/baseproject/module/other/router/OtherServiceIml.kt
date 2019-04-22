package com.nmlg.common.router

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.libarouter.service.IOtherService
import com.ashlikun.baseproject.module.other.view.PermissonActivity
import com.ashlikun.common.utils.jump.RouterJump

/**
 * 作者　　: 李坤
 * 创建时间: 2018/4/4 0004　下午 4:07
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：Other模块的接口
 */
@Route(path = RouterPath.SERVICE_OTHER)
class OtherServiceIml : IOtherService {
    internal var mContext: Context? = null


    override fun init(context: Context) {
        mContext = context
    }

    override fun requestPermission(permission: Array<String>, showRationaleMessage: String?
                                   , denied: (() -> Unit)?
                                   , success: () -> Unit) {
        var topActivity = RouterJump.topActivity()
        if (topActivity != null) {
            var intent = Intent(RouterJump.topActivity(), PermissonActivity::class.java)
            intent.putExtra(RouterKey.FLAG_PERMISSION, permission)
            intent.putExtra(RouterKey.FLAG_DATA, showRationaleMessage)
            PermissonActivity.setOnPermisson(success, denied)
            RouterJump.topActivity()?.startActivity(intent)
        }
    }

}
