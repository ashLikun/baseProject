package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.baseproject.libcore.mode.javabean.HttpListResult
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.cache.CacheEntity
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.okhttputils.http.response.IHttpResponse

/**
 * 成功的回调
 */
typealias OnSuccess<T> = (result: T) -> Unit

/**
 * 成功后的处理
 * exception : 父类的处理结果
 */
typealias OnSuccessHander<T> = (result: T, exception: HttpException?) -> HttpException?

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
open class SimpleHttpCallback<T> constructor(handle: HttpUiHandle?)
    : HttpCallBack<T>(handle) {
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


    override fun onSuccess(result: T) {
        super.onSuccess(result)
        if (result is HttpResult<*> || result is HttpListResult<*>) {
            when {
                (result as IHttpResponse).isSucceed -> {
                    //成功时候对data为null的处理
                    if (result is HttpResult<*>) {
                        if (result.data == null) {
                            result.data = getListOrArrayOrObject(resultType)
                        }
                        when {
                            result.data != null -> success?.invoke(result)
                            else -> onError(HttpException(HttpCodeApp.NO_DATA_ERROR, HttpCodeApp.NO_DATA_ERROR_MSG))
                        }
                    } else if (result is HttpListResult<*>) {
                        if (result.data == null) {
                            (result as HttpListResult<in Any>).data = getListOrArrayOrObject(resultType)
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

    override fun onSuccessHandelCode2(result: T): HttpException? {
        val superRes = super.onSuccessHandelCode2(result)
        return successHandelCode?.invoke(result, superRes) ?: superRes
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