package com.ashlikun.baseproject.libcore.utils.http.interceptor

import com.ashlikun.baseproject.libcore.utils.http.HttpManager
import com.ashlikun.okhttputils.http.HttpUtils
import com.ashlikun.utils.other.LogUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 作者　　: 李坤
 * 创建时间: 2023/1/7　15:54
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：网络请求 日志拦截器
 */
internal class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val startTime = System.nanoTime()

        HttpUtils.getRequestToString(request)

        //request.url, chain.connection(), request.headers

        val response: Response = chain.proceed(request)
        if (HttpManager.isLog) {
            LogUtils.d(
                "********************************************Http请求开始********************************************\n" +
                        "*********************************************Request********************************************\n" +
                        "${HttpUtils.getRequestToString(request)}\n\n" +
                        "*********************************************Response*******************************************\n" +
                        "${HttpUtils.getResponseToString(response)}\n\n" +
                        "time : ${(System.nanoTime() - startTime) / 1e6} \n" +
                        "********************************************Http请求结束********************************************\n"
            )
        }
        return response
    }
}