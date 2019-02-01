package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.okhttputils.http.cache.CacheEntity
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
    fun <ResultType> execute(param: HttpRequestParam, callbackHandle: HttpCallbackHandle,
                             success: ((result: ResultType) -> Unit)? = null,
                             noSuccess: ((result: ResultType) -> Unit)? = null,
                             errorData: ((data: ContextData) -> Unit)? = null,
                             error: ((error: HttpException) -> Unit)? = null,
                             successHanderError: ((result: ResultType) -> Boolean)? = null,
                             successSubThread: ((result: ResultType) -> Unit)? = null,
                             cacheSuccess: ((entity: CacheEntity, result: ResultType) -> Unit)? = null,
                             successHandelCode: ((result: ResultType) -> Boolean)? = null,
                             completed: (() -> Unit)? = null,
                             start: (() -> Unit)? = null
    ): ExecuteCall {
        val callback: SimpleHttpCallback<ResultType> = SimpleHttpCallback<ResultType>(callbackHandle)
        callback.success = success
        callback.noSuccess = noSuccess
        callback.successHanderError = successHanderError
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
