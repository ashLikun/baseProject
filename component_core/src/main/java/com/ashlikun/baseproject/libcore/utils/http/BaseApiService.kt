package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.callback.Callback
import com.ashlikun.okhttputils.http.request.HttpRequest
import com.ashlikun.okhttputils.http.response.HttpResult
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:33
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */

open class BaseApiService {
    //模板
    fun test(handle: HttpCallbackHandle,
             success: OnSuccess<HttpResult<String>>): ExecuteCall? {
        return "index".requestGet()
                .execute(handle, success)
    }

    /**
     * kotlin方式的请求
     */
    fun <T> HttpRequest.execute(handle: HttpCallbackHandle,
                                success: OnSuccess<T>? = null,
                                error: OnError? = null,
                                errorData: OnErrorData? = null,
                                successSubThread: OnSuccess<T>? = null,
                                cacheSuccess: OnCacheSuccess<T>? = null,
                                successHandelCode: OnSuccessHander<T>? = null,
                                completed: OnArgs? = null,
                                start: OnArgs? = null
    ): ExecuteCall {
        val callback = SimpleHttpCallback<T>(handle)
        //由于SimpleHttpCallback的泛型这里无法直接指定，只能通过其他参数获取
        val type = success?.javaClass ?: successSubThread?.javaClass
        ?: cacheSuccess?.javaClass ?: successHandelCode?.javaClass
        if (type != null) {
            callback.resultType = getType(type)
        }
        callback.success = success
        callback.cacheSuccess = cacheSuccess
        callback.successHandelCode = successHandelCode
        callback.successSubThread = successSubThread
        callback.completed = completed
        callback.start = start
        callback.error = error
        callback.errorData = errorData
        return execute(callback)
    }

    /**
     * 获取回调里面的泛型
     */
    open fun getType(mClass: Class<*>): Type? {
        val types = mClass.genericSuperclass
        var parentypes: Array<Type?>? //泛型类型集合
        if (types is ParameterizedType) {
            parentypes = types.actualTypeArguments
        } else {
            parentypes = mClass.genericInterfaces
            parentypes?.forEach {
                if (it is ParameterizedType) {
                    parentypes = it.actualTypeArguments
                    return@forEach
                }
            }
        }
        if (parentypes.isNullOrEmpty()) {
            Throwable("BaseApiService  ->>>  回调 不能没有泛型，请查看execute方法几个回调是否有泛型是否有泛型")
        } else {
            return parentypes!![0]
        }
        return null
    }
}
