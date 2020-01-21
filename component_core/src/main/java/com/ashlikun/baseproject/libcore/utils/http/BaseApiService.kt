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
    /**
     * java方式的请求
     */
    fun execute(param: HttpRequestParam, callback: Callback<*>): ExecuteCall {
        if (param.tag == null) {
            if (callback is HttpCallBack<*>) {
                param.tag(callback.getTag())
            }
        }
        return OkHttpUtils.request(param)
                .execute(callback)
    }

    /**
     * kotlin方式的请求
     */
    fun <T> execute(param: HttpRequestParam, callbackHandle: HttpCallbackHandle,
                    success: OnSuccess<T>? = null,
                    errorData: OnErrorData? = null,
                    error: OnError? = null,
                    successSubThread: OnSuccess<T>? = null,
                    cacheSuccess: OnCacheSuccess<T>? = null,
                    successHandelCode: OnSuccessHander<T>? = null,
                    completed: OnArgs? = null,
                    start: OnArgs? = null
    ): ExecuteCall {
        val callback: SimpleHttpCallback<T> = object : SimpleHttpCallback<T>(callbackHandle) {}
        callback::class.java
        callback.success = success
        callback.successSubThread = successSubThread
        callback.cacheSuccess = cacheSuccess
        callback.successHandelCode = successHandelCode
        callback.completed = completed
        callback.start = start
        callback.error = error
        callback.errorData = errorData
        return execute(param, callback)
    }
}
