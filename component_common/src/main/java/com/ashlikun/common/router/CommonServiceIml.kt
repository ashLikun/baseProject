package com.nmlg.common.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.nmlg.renrenying.libcore.constant.RouterPath
import com.nmlg.renrenying.libcore.libarouter.service.ICommonService

/**
 * 作者　　: 李坤
 * 创建时间: 2018/4/4 0004　下午 4:07
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：公共模块的接口，一般是给core用的
 */
@Route(path = RouterPath.SERVICE_COMMON)
class CommonServiceIml : ICommonService {
    internal var mContext: Context? = null


    override fun init(context: Context) {
        mContext = context
    }


}
