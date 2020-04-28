package com.ashlikun.baseproject.libcore.utils.http

import android.app.Activity
import android.os.Looper
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.libcore.utils.http.interceptor.DefaultInterceptor
import com.ashlikun.baseproject.libcore.utils.other.CacheUtils
import com.ashlikun.baseproject.libcore.utils.other.postBugly
import com.ashlikun.okhttputils.http.HttpUtils
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.other.MainHandle
import com.ashlikun.utils.other.file.FileUtils
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.SuperToast
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
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
        OkHttpUtils.init(getOkHttpClientBuilder().build())
        OkHttpUtils.setOnDataParseError { code, exception, response, json ->
            val requestStr = HttpUtils.getRequestToString(response.request)
            val responseStr = HttpUtils.getResponseToString(response)
            RuntimeException("request:\n$requestStr\nresponse:\n$responseStr \n$json", exception).postBugly()
        }
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
        const val BASE_URL = "http://ym.yoohfit.com"
        const val BASE_PATH = "/tools/apptool.ashx"
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
                    response.getCode() == HttpCodeApp.SIGN_ERROR -> {
                        SuperToast.get("签名错误").error()
                        return false
                    }
                    else -> return true
                }
            }
            return true
        }

        public fun showTokenErrorDialog(activity: Activity, message: String, code: Int) {
            if (IS_LOGIN_OUT_DIALOG_SHOW) {
                return
            }
            IS_LOGIN_OUT_DIALOG_SHOW = true
            MaterialDialog(activity)
                    .cancelable(false)
                    .onDismiss { IS_LOGIN_OUT_DIALOG_SHOW = false }
                    .show {
                        message(text = message)
                        positiveButton(text = "知道了") {
                            if (code == HttpCodeApp.TOKEN_ERROR) {
                                RouterManage.login()?.exitLogin()
                                RouterManage.login()?.startLogin()
                            }
                        }
                    }
        }
    }


}

