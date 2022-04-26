package com.ashlikun.baseproject.libcore.utils.http

import android.app.Activity
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import com.ashlikun.baseproject.libcore.constant.EventBusKey
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.libcore.utils.http.interceptor.DefaultInterceptor
import com.ashlikun.baseproject.libcore.utils.other.AppConfig
import com.ashlikun.baseproject.libcore.utils.other.CacheUtils
import com.ashlikun.baseproject.libcore.utils.other.postBugly
import com.ashlikun.livedatabus.busForever
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.HttpUtils
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.http.response.IHttpResponse
import com.ashlikun.okhttputils.retrofit.Retrofit
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.DeviceUtil
import com.ashlikun.utils.other.MainHandle
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.other.file.FileUtils
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.modal.SuperToast
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * 作者　　: 李坤
 * 创建时间: 16:19 Administrator
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：项目的http管理
 */
class HttpManager private constructor() {
    init {
        HttpResponse.SUCCEED = 0
        HttpResponse.ERROR = 1
        OkHttpUtils.init(AppUtils.app, getOkHttpClientBuilder().build())
        OkHttpUtils.setOnDataParseError { code, exception, response, json ->
            val requestStr = HttpUtils.getRequestToString(response.request)
            val responseStr = HttpUtils.getResponseToString(response)
            RuntimeException(
                "request:\n$requestStr\nresponse:\n$responseStr \n$json",
                exception
            ).postBugly()
        }
        Retrofit.get().init(createRequest = {
            HttpRequestParam.create(it.url).parseGson(it)
        }) { request, result, params ->
            request.syncExecute<Any>(params?.find { it is HttpUiHandle } as? HttpUiHandle,
                result.resultType)
        }
        Retrofit.get().onProxyStart = { method, args ->
            (args?.find { it is HttpUiHandle } as? HttpUiHandle)?.start()
        }
        EventBusKey.LOGIN.busForever {
            setCommonParams()
        }
        EventBusKey.EXIT_LOGIN.busForever {
            setCommonParams()
        }
        setCommonParams()
    }

    fun setCommonParams() {
        //公共参数
        OkHttpUtils.get().commonParams = mutableMapOf(
            "uid" to (RouterManage.login()?.getUserId() ?: ""),
            "sessionid" to (RouterManage.login()?.getToken() ?: ""),
            "os" to "android",
            "osBrand" to StringUtils.dataFilter(
                DeviceUtil.systemModel,
                DeviceUtil.deviceBrand
            ),
            "osVersion" to "${DeviceUtil.systemVersion}",
            "devid" to DeviceUtil.soleDeviceId,
            "appVersionCode" to AppUtils.versionCode,
            "appVersion" to AppUtils.versionName,
            "appKey" to "5fb39a50c59a9"
        )
    }

    fun getCacheDir(): File {
        return File(CacheUtils.appCachePath, "HttpCache")
    }

    fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        //1：手动创建一个OkHttpClient并设置超时时间
        val builder = OkHttpClient.Builder()
        builder.retryOnConnectionFailure(true)//链接失败重新链接
        builder.connectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)//链接超时
        builder.readTimeout(OkHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)//读取超时
        builder.writeTimeout(OkHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)//写入超时
        //设置缓存目录
        val cacheDirectory = getCacheDir()
        //50M缓存
        val cache = Cache(cacheDirectory, (50 * 1024 * 1024).toLong())
        //设置缓存
        builder.cache(cache)
        //公共拦截器
        builder.addInterceptor(DefaultInterceptor())
        //防止抓包
        if (!AppUtils.isDebug) {
            builder.proxy(Proxy.NO_PROXY)
        }
        return builder
    }

    fun getCacheSize(): Double {
        try {
            return FileUtils.getFileOrFilesSize(getCacheDir().path, FileUtils.SIZETYPE_B)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0.0
    }

    companion object {
        //正式环境，注解使用
        const val URL_PROD = "https://api-sip.510gow.com"

        //测试环境，注解使用
        const val URL_TEST = "https://tapi-sip.510gow.com"

        //基础path
        const val BASE_PATH = "/interface?"
        const val ACTION = "action"

        //服务器地址，任何地方代码使用
        val BASE_URL = if (AppConfig.isBeta || AppConfig.isDebug) URL_TEST else URL_PROD


        /**
         * 退出对话框是否显示,防止多次显示
         */
        private var IS_LOGIN_OUT_DIALOG_SHOW = false

        private val INSTANCE by lazy {
            HttpManager()
        }

        @JvmStatic
        fun get(): HttpManager {
            return INSTANCE
        }

        /**
         * 创建Url,这里会替换环境的HOST
         */
        @JvmStatic
        fun createUrl(
            url: String? = null,
            action: String? = null,
            path: String = BASE_PATH
        ): String {
            return if (url.isNullOrEmpty()) BASE_URL + path + "action=" + action
            else url.replace(URL_PROD, BASE_URL).replace(URL_TEST, BASE_URL)
        }


        /**
         * 处理成功后的数据code
         * @return 如果错误会返回[HttpHandelResultException]
         * */
        @JvmStatic
        fun <T> handelResult(responseBody: T): HttpHandelResultException? {
            var response: IHttpResponse? = null
            if (responseBody is IHttpResponse) {
                response = responseBody
            } else if (responseBody is String) {
                response = HttpResponse()
                response.setOnGsonErrorData(responseBody as String)
            }
            var httpException: HttpException? = null
            response?.let {
                when {
                    response.isSucceed -> return null

                    response.code == HttpCodeApp.TOKEN_ERROR -> {

                        RouterManage.login()?.exit()
                        val activity = ActivityManager.foregroundActivity
                        if (activity != null && !activity.isFinishing) {
                            if (Looper.getMainLooper() != Looper.myLooper()) {
                                MainHandle.post {
                                    showTokenErrorDialog(
                                        activity,
                                        response.message,
                                        response.code
                                    )
                                }
                            } else {
                                showTokenErrorDialog(activity, response.message, response.code)
                            }
                        } else {
                            SuperToast.get(response.message).error()
                            if (response.code == HttpCodeApp.TOKEN_ERROR) {
                                RouterManage.login()?.exitLogin()
                                RouterManage.login()?.startLogin()
                            }
                        }
                        httpException = HttpException(response.code, response.message)
                    }
                    response.code == HttpCodeApp.NO_LOGIN -> {

                        RouterManage.login()?.exit()
                        val activity = ActivityManager.foregroundActivity
                        if (activity != null && !activity.isFinishing) {
                            if (Looper.getMainLooper() != Looper.myLooper()) {
                                MainHandle.post {
                                    showTokenErrorDialog(
                                        activity,
                                        response.message,
                                        response.code
                                    )
                                }
                            } else {
                                showTokenErrorDialog(activity, response.message, response.code)
                            }
                        } else {
                            SuperToast.get(response.message).error()
                            if (response.code == HttpCodeApp.NO_LOGIN) {
                                RouterManage.login()?.exitLogin()
                                RouterManage.login()?.startLogin()
                            }
                        }
                        httpException = HttpException(response.code, response.message)
                    }
                    response.code == HttpCodeApp.SIGN_ERROR -> {
                        SuperToast.get("签名错误").error()
                        httpException = HttpException(response.code, response.message)
                    }
                    else -> null
                }
            }
            if (httpException != null) {
                return HttpHandelResultException(httpException!!)
            }
            return null
        }

        fun showTokenErrorDialog(activity: Activity, message: String, code: Int) {
            if (IS_LOGIN_OUT_DIALOG_SHOW) {
                return
            }
            IS_LOGIN_OUT_DIALOG_SHOW = true
            AlertDialog.Builder(activity)
                .setCancelable(false)
                .setTitle("账号异常")
                .setOnDismissListener { IS_LOGIN_OUT_DIALOG_SHOW = false }
                .setMessage(message)
                .setPositiveButton("知道了") { dialoog, which ->
                    if (code == HttpCodeApp.TOKEN_ERROR) {
                        RouterManage.login()?.exitLogin()
                        RouterManage.login()?.startLogin()
                    }
                    if (code == HttpCodeApp.NO_LOGIN) {
                        RouterManage.login()?.startLogin()
                    }
                }
                .show()
        }
    }


}

/**
 * 接口成功处理code时候的错误
 */
class HttpHandelResultException(var exception: HttpException) :
    HttpException(20009, "全局错误", exception)