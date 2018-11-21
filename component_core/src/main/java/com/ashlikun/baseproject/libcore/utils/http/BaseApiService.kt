package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.okhttputils.http.callback.Callback

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:33
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */

open class BaseApiService {
    fun execute(param: HttpRequestParam, callback: Callback<*>): ExecuteCall {
        if (param.tag == null) {
            if (callback is HttpCallBack<*>) {
                param.tag(callback.getTag())
            }
        }
        return OkHttpUtils.request(param)
                .execute(callback)
    }
}
