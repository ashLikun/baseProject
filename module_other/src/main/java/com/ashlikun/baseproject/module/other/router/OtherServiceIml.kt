package com.ashlikun.baseproject.module.other.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.router.service.IOtherService

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

}
