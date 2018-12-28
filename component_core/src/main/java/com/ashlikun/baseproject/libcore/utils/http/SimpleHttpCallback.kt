package com.lingyun.client.libcore.utils.http

import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.cache.CacheEntity
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.ui.SuperToast

/**
 * 作者　　: 李坤
 * 创建时间: 2018/12/26　13:33
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：实现HttpCallBack
 */
class SimpleHttpCallback<ResultType>(private val buider: HttpCallbackHandle = HttpCallbackHandle.get())
    : HttpCallBack<ResultType>(buider) {
    var success: ((result: ResultType) -> Unit)? = null
    /**
     * 接口成功了但是code错误
     */
    var noSuccess: ((result: ResultType) -> Unit)? = null
    /**
     * 成功后的ui处理
     * @return 是否对错误信息处理
     */
    var successHanderError: ((result: ResultType) -> Boolean)? = null
    /**
     * 子线程执行,对结果进一步处理
     */
    var successSubThread: ((result: ResultType) -> Unit)? = null
    /**
     * 缓存返回成功
     */
    var cacheSuccess: ((entity: CacheEntity, result: ResultType) -> Unit)? = null
    /**
     * 成功后处理code
     */
    var successHandelCode: ((result: ResultType) -> Boolean)? = null
    var completed: (() -> Unit)? = null
    var start: (() -> Unit)? = null
    var error: ((error: HttpException) -> Unit)? = null
    var errorData: ((data: ContextData) -> Unit)? = null

    override fun onSuccess(result: ResultType) {
        super.onSuccess(result)
        if (result is HttpResponse) {
            if (result.isSucceed) {
                success?.invoke(result)
            } else if (noSuccess == null) {
                SuperToast.showErrorMessage(result.getMessage())
            } else {
                noSuccess?.invoke(result)
            }
        } else {
            success?.invoke(result)
        }

    }

    override fun onSuccess(result: ResultType, isHanderError: Boolean) {
        if (successHanderError?.invoke(result) != false) {
            super.onSuccess(result, isHanderError)
        }
    }

    override fun onSuccessSubThread(result: ResultType) {
        super.onSuccessSubThread(result)
        successSubThread?.invoke(result)
    }

    override fun onCacheSuccess(entity: CacheEntity, result: ResultType) {
        super.onCacheSuccess(entity, result)
        cacheSuccess?.invoke(entity, result)
    }

    override fun onSuccessHandelCode(result: ResultType): Boolean {
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