package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.cache.CacheEntity
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.http.response.HttpResult

/**
 * 作者　　: 李坤
 * 创建时间: 2018/12/26　13:33
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：实现HttpCallBack
 */
open class SimpleHttpCallback<T> constructor(buider: HttpCallbackHandle = HttpCallbackHandle.get())
    : HttpCallBack<T>(buider) {
    var success: ((result: T) -> Unit)? = null
    /**
     * 成功后的ui处理
     * @return 是否对错误信息处理
     */
    var successHanderError: ((result: T) -> Boolean)? = null
    /**
     * 子线程执行,对结果进一步处理
     */
    var successSubThread: ((result: T) -> Unit)? = null
    /**
     * 缓存返回成功
     */
    var cacheSuccess: ((entity: CacheEntity, result: T) -> Unit)? = null
    /**
     * 成功后处理code
     */
    var successHandelCode: ((result: T) -> Boolean)? = null
    var completed: (() -> Unit)? = null
    var start: (() -> Unit)? = null
    var error: ((error: HttpException) -> Unit)? = null
    var errorData: ((data: ContextData) -> Unit)? = null

    override fun onSuccess(result: T) {
        super.onSuccess(result)
        if (result is HttpResult<*>) {
            when {
                result.isSucceed -> {
                    if ((result as HttpResult<*>).data == null) {
                        (result as HttpResult<*>).data = getListOrArray()
                    }
                    when {
                        (result as HttpResult<*>).data != null -> success?.invoke(result)
                        else -> onError(HttpException(HttpCodeApp.NO_DATA_ERROR, HttpCodeApp.NO_DATA_ERROR_MSG))
                    }
                }
                else -> success?.invoke(result)
            }
        } else {
            success?.invoke(result)
        }
    }

    override fun onSuccess(result: T, isHanderError: Boolean) {
        if (successHanderError?.invoke(result) != false) {
            super.onSuccess(result, isHanderError)
        }
    }

    override fun onSuccessSubThread(result: T) {
        super.onSuccessSubThread(result)
        successSubThread?.invoke(result)
    }

    override fun onCacheSuccess(entity: CacheEntity, result: T) {
        super.onCacheSuccess(entity, result)
        cacheSuccess?.invoke(entity, result)
    }

    override fun onSuccessHandelCode(result: T): Boolean {
        return successHandelCode?.invoke(result) != false && super.onSuccessHandelCode(result)
    }

    override fun onError(error: HttpException) {
        super.onError(error)
        this.error?.invoke(error)
    }

    override fun onError(data: ContextData) {
        super.onError(data)
        errorData?.invoke(data)
    }

    override fun onCompleted() {
        super.onCompleted()
        completed?.invoke()
    }

    override fun onStart() {
        super.onStart()
        start?.invoke()
    }
}