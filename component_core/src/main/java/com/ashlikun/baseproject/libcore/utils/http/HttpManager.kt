package com.ashlikun.baseproject.libcore.utils.http

import android.app.Activity
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.libcore.utils.other.CacheUtils
import com.ashlikun.baseproject.libcore.utils.other.postBugly
import com.ashlikun.okhttputils.http.HttpUtils
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.DeviceUtil
import com.ashlikun.utils.other.MainHandle
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.other.file.FileUtils
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.SuperToast
import com.ashlikun.baseproject.libcore.constant.EventBusKey
import com.ashlikun.baseproject.libcore.utils.http.interceptor.DefaultInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.net.URLEncoder
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
        OkHttpUtils.init(getOkHttpClientBuilder().build())
        OkHttpUtils.setOnDataParseError { code, exception, response, json ->
            val requestStr = HttpUtils.getRequestToString(response.request)
            val responseStr = HttpUtils.getResponseToString(response)
            RuntimeException("request:\n$requestStr\nresponse:\n$responseStr \n$json", exception).postBugly()
        }
        EventBus.get(EventBusKey.LOGIN).registerForever {
            setCommonParams();
        }
        EventBus.get(EventBusKey.EXIT_LOGIN).registerForever {
            setCommonParams();
        }
        setCommonParams()
    }

    fun setCommonParams() {
        //公共参数
        OkHttpUtils.getInstance().commonParams = mapOf(
                "uid" to (RouterManage.login()?.getUserId() ?: ""),
                "sessionid" to (RouterManage.login()?.getToken() ?: ""),
                "os" to "android",
                "sdkVersion" to "${DeviceUtil.getSystemVersion()}",
                "osVersion" to StringUtils.dataFilter(DeviceUtil.getSystemModel(), DeviceUtil.getDeviceBrand()),
                "devid" to DeviceUtil.getSoleDeviceId(),
                "appVersion" to AppUtils.getVersionName())
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
        return builder
    }

    fun getCacheSize(): Double {
        try {
            return FileUtils.getFileSize(getCacheDir()).toDouble()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0.0
    }

    companion object {
        const val BASE_URL = "https://api-sip.510gow.com"
        const val BASE_PATH = "/interface?"

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
         * 处理成功后的数据code
         *
         * @param responseBody
         * @param <T>
         * @return
         * */
        @JvmStatic
        fun <T> handelResult(responseBody: T): Boolean {
            var response: HttpResponse? = null
            if (responseBody is HttpResponse) {
                response = responseBody
            } else if (responseBody is String) {
                response = HttpResponse()
                response.setOnGsonErrorData(responseBody as String)
            }
            response?.let {
                when {
                    response.isSucceed -> return true

                    response.code == HttpCodeApp.TOKEN_ERROR -> {

                        RouterManage.login()?.exit()
                        val activity = ActivityManager.getForegroundActivity()
                        if (activity != null && !activity.isFinishing) {
                            if (Looper.getMainLooper() != Looper.myLooper()) {
                                MainHandle.get().post { showTokenErrorDialog(activity, response.getMessage(), response.code) }
                            } else {
                                showTokenErrorDialog(activity, response.getMessage(), response.code)
                            }
                        } else {
                            SuperToast.get(response.getMessage()).error()
                            if (response.getCode() == HttpCodeApp.TOKEN_ERROR) {
                                RouterManage.login()?.exitLogin()
                                RouterManage.login()?.startLogin()
                            }
                        }
                        return false
                    }
                    response.code == HttpCodeApp.NO_LOGIN -> {

                        RouterManage.login()?.exit()
                        val activity = ActivityManager.getForegroundActivity()
                        if (activity != null && !activity.isFinishing) {
                            if (Looper.getMainLooper() != Looper.myLooper()) {
                                MainHandle.get().post { showTokenErrorDialog(activity, response.getMessage(), response.code) }
                            } else {
                                showTokenErrorDialog(activity, response.getMessage(), response.code)
                            }
                        } else {
                            SuperToast.get(response.getMessage()).error()
                            if (response.getCode() == HttpCodeApp.NO_LOGIN) {
                                RouterManage.login()?.exitLogin()
                                RouterManage.login()?.startLogin()
                            }
                        }
                        return false
                    }
                    response.getCode() == HttpCodeApp.SIGN_ERROR -> {
                        SuperToast.get("签名错误").error()
                        return false
                    }
                    else -> return true
                }
            }
            return true
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

