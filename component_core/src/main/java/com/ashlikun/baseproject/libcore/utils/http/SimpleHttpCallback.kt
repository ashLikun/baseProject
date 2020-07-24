package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.baseproject.libcore.utils.http.HttpCallBack

import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.cache.CacheEntity
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.baseproject.libcore.javabean.HttpListResult
import com.ashlikun.baseproject.libcore.javabean.HttpPageResult
import com.ashlikun.okhttputils.http.response.HttpResponse


/**
 * 成功的回调
 */
typealias OnSuccess<T> = (result: T) -> Unit

/**
 * 成功后的处理
 */
typealias OnSuccessHander<T> = (result: T) -> Boolean

/**
 * 缓存返回成功
 */
typealias OnCacheSuccess<T> = (entity: CacheEntity, result: T) -> Unit

/**
 * 无参
 */
typealias OnArgs = () -> Unit

/**
 * 错误
 */
typealias OnError = (error: HttpException) -> Unit

/**
 * 处理后的错误
 */
typealias OnErrorData = (data: ContextData) -> Unit

/**
 * 作者　　: 李坤
 * 创建时间: 2018/12/26　13:33
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：实现HttpCallBack
 */
open class SimpleHttpCallback<T> constructor(buider: HttpCallbackHandle = HttpCallbackHandle.get())
    : HttpCallBack<T>(buider) {
    var success: OnSuccess<T>? = null

    /**
     * 子线程执行,对结果进一步处理
     */
    var successSubThread: OnSuccess<T>? = null
    var cacheSuccess: OnCacheSuccess<T>? = null

    /**
     * 成功后处理code
     */
    var successHandelCode: OnSuccessHander<T>? = null
    var completed: OnArgs? = null
    var start: OnArgs? = null
    var error: OnError? = null
    var errorData: OnErrorData? = null

    //自动处理  Code(接口是成功的)错误，布局切换
    var isAutoHanderError: Boolean = true

    override fun onSuccess(result: T, isHanderError: Boolean) {
        super.onSuccess(result, isAutoHanderError)
    }

    override fun onSuccess(result: T) {
        super.onSuccess(result)
        if (result is HttpResult<*> || result is HttpListResult<*>) {
            when {
                (result as HttpResponse).isSucceed -> {
                    //成功时候对data为null的处理
                    if (result is HttpResult<*>) {
                        if (result.data == null) {
                            result.data = getListOrArrayOrObject()
                        }
                        when {
                            result.data != null -> success?.invoke(result)
                            else -> onError(HttpException(HttpCodeApp.NO_DATA_ERROR, HttpCodeApp.NO_DATA_ERROR_MSG))
                        }
                    } else if (result is HttpListResult<*>) {
                        if (result.data == null) {
                            (result as HttpListResult<in Any>).data = getListOrArrayOrObject()
                        }
                        when {
                            result.data != null -> success?.invoke(result)
                            else -> onError(HttpException(HttpCodeApp.NO_DATA_ERROR, HttpCodeApp.NO_DATA_ERROR_MSG))
                        }
                    }
                }
                else -> success?.invoke(result)
            }
        } else {
            success?.invoke(result)
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