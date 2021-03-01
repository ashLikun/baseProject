package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.baseproject.libcore.mode.javabean.HttpListResult
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.request.HttpRequest
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.utils.other.MainHandle
import com.namei.jinjihu.libcore.utils.http.*
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.ashlikun.baseproject.libcore.R

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
     * 模板 异步
     */
    fun test(handle: HttpUiHandle,
             success: OnSuccess<HttpResult<String>>): ExecuteCall? {
        return "index".requestPost()
                .execute(handle, success)
    }

    /**
     * 模板 同步
     */
    suspend fun testSync(handle: HttpUiHandle): HttpResult<List<String>>? {
        return "index".requestPost()
                .syncExecute(handle) {}
    }
}

/**
 * 同步请求
 * @param data 为了方便获取请求的返回值Type，调用的地方不用具体实现
 */
suspend fun <T> HttpRequest.syncExecute(handle: HttpUiHandle?, resultType: Type): T {
    return suspendCancellableCoroutine { continuation ->
        var call: ExecuteCall? = null
        continuation.invokeOnCancellation {
            //当协程被取消的时候，取消网络请求
            call?.cancel()
        }
        val callback = SimpleHttpCallback<T>(handle)
        if (tag == null && handle != null) {
            tag(handle?.getTag())
        }
        callback.resultType = resultType
        callback.success = {
            continuation.resume(it)
        }
        callback.error = {
            continuation.resumeWithException(it)
        }
        call = execute(callback)
    }
}

/**
 * 同步请求
 * @param data 为了方便获取请求的返回值Type，调用的地方不用具体实现
 */
suspend fun <T> HttpRequest.syncExecute(handle: HttpUiHandle?, data: OnSuccess<T>): T {
    return syncExecute(handle, getType(data.javaClass)!!)
}

/**
 * 同步请求 非协程
 * @param data 为了方便获取请求的返回值Type，调用的地方不用具体实现
 */
fun <T> HttpRequest.syncExecute2(handle: HttpUiHandle?, data: OnSuccess<T>): T? {
    MainHandle.post { handle?.start() }
    if (tag == null) {
        tag(handle?.getTag())
    }
    try {
        val resultType = getType(data.javaClass)!!
        val result = syncExecute(resultType) as T
        val res = HttpManager.handelResult(result)

        if (res == null) {
            //成功时候对data为null的处理
            if (result is HttpResponse && result.isSucceed) {
                if (result is HttpResult<*>) {
                    if (result.data == null) {
                        result.data = HttpCallBack.getListOrArrayOrObject(resultType)
                    }
                } else if (result is HttpListResult<*>) {
                    if (result.data == null) {
                        (result as HttpListResult<in Any>).data = HttpCallBack.getListOrArrayOrObject(resultType)
                    }
                }
            }
            MainHandle.get().posts { handle?.success(result as Any) }
            return result
        } else {
            //不显示toast
            handle?.isErrorToastShow = false
            MainHandle.post { handle?.success(result as Any) }
            return null
        }
    } catch (error: HttpException) {
        MainHandle.post {
            handle?.error((ContextData().setErrCode(error.code())
                    .setTitle(error.message())
                    .setResId(R.drawable.material_service_error)))
        }
        return null
    } finally {
        MainHandle.post {
            handle?.completed()
        }
    }
}

/**
 * kotlin方式的请求
 */
fun <T> HttpRequest.execute(handle: HttpUiHandle,
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
    if (tag == null) {
        tag(handle.getTag())
    }
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
fun getType(mClass: Class<*>): Type? {
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