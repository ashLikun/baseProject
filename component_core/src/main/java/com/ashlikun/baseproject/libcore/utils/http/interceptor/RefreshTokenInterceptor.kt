package com.ashlikun.baseproject.libcore.utils.http.interceptor

import com.ashlikun.baseproject.libcore.R
import com.ashlikun.baseproject.libcore.router.RouterManage
import com.ashlikun.baseproject.libcore.utils.extend.isTokenExpire
import com.ashlikun.baseproject.libcore.utils.http.HttpCodeApp
import com.ashlikun.gson.GsonHelper
import com.ashlikun.okhttputils.http.HttpUtils
import com.ashlikun.okhttputils.http.OkHttpManage
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.ui.extend.resString
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


/**
 * @author　　: 李坤
 * 创建时间: 2022/4/19 10:03
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：token过期刷新
 */
class RefreshTokenInterceptor : Interceptor {
    private var refreshStart = false
    private var refreshTokenResponseCache: HttpResponse? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        var oldResponse = chain.proceed(chain.request())
        val json = HttpUtils.getResponseColneBody(oldResponse)
        if (!json.isNullOrEmpty()) {
            runCatching {
                //是否过期
                if (HttpResponse().apply { setOnGsonErrorData(json) }.isTokenExpire()) {
                    var isUseCache = false
                    //刷新token要防止并发
                    if (refreshStart) {
                        isUseCache = true
                        LogUtils.e("刷新token：等待同步")
                    }
                    synchronized(this) {
                        if (!isUseCache || refreshTokenResponseCache?.isSucceed != true) {
                            LogUtils.e("刷新token：开始")
                            refreshStart = true
                            refreshTokenResponseCache = null
                            refreshTokenResponseCache = RouterManage.login()?.goRefreshToken()
                            LogUtils.e("刷新token：返回${refreshTokenResponseCache?.json}")
                            refreshStart = false
                        } else {
                            LogUtils.e("刷新token：使用缓存：${refreshTokenResponseCache?.json}")
                        }
                    }
                    oldResponse = reRequest(chain, oldResponse, refreshTokenResponseCache) ?: oldResponse
                }
            }.onFailure {
                refreshStart = false
                it.printStackTrace()
            }
        }
        return oldResponse
    }

    /**
     * 重新请求
     */
    private fun reRequest(chain: Interceptor.Chain, oldResponse: Response, refreshTokenResponse: HttpResponse?): Response? {
        //去刷新token
        if (refreshTokenResponse?.isSucceed == true) {
            //插入token，重新请求
            var newRequest = chain.request().newBuilder()
            val token = RouterManage.login()?.getToken() ?: ""
            //设置请求头
            newRequest.header("accessToken", token)
            OkHttpManage.get().commonHeaders["accessToken"] = token
            val newResponse = chain.proceed(newRequest.build())
            val jsonNew = HttpUtils.getResponseColneBody(newResponse)
            //再次判断是否过期
            if (HttpResponse().apply { setOnGsonErrorData(jsonNew) }.isTokenExpire()) {
                //退出登录
                return oldResponse.newBuilder()
                    .body(GsonHelper.getGsonNotNull().toJson(HttpResponse().apply {
                        code = HttpCodeApp.TOKEN_ERROR
                        message = R.string.login_no.resString
                    }).toResponseBody(oldResponse.body?.contentType()))
                    .build()
            } else {
                return newResponse
            }
        } else if (refreshTokenResponse != null) {
            //把刷新token的数据返回
            return oldResponse.newBuilder()
                .body(GsonHelper.getGsonNotNull().toJson(refreshTokenResponse).toResponseBody(oldResponse.body?.contentType()))
                .build()
        } else {
            return null
        }
    }
    /**
     * 替换某个参数
     *
     *    var request: Request.Builder? = null
    var oldResponse = chain.proceed(chain.request())
    //解析数据
    val httpResponse = HttpResponse()
    httpResponse.setOnGsonErrorData(HttpUtils.getResponseColneBody(oldResponse))
    if (httpResponse.isTokenError()) {
    //token 失效，重新获取
    if (updateToken()) {
    try {
    request = HttpUtils.setRequestParams(chain.request(), "token", RouterManage.login()?.getToken())
    } catch (e: InterruptedException) {
    e.printStackTrace()
    }
    }
    }
    if (request != null) {
    return chain.proceed(request.build())
    }
    return oldResponse
     */
}
