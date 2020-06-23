package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.baseproject.libcore.utils.http.HttpCallBack
import com.ashlikun.okhttputils.http.callback.ProgressCallBack
import com.ashlikun.utils.other.LogUtils

abstract class HttpProgressCallBack<ResultType>(private val buider: HttpCallbackHandle = HttpCallbackHandle.get()) : HttpCallBack<ResultType>(buider), ProgressCallBack {
    /**
     * 下载或者上传 回调的频率  ms
     */
    var rateVar: Long = 500

    fun onStart(isCompress: Boolean) {
        LogUtils.e("onStart2")
        super.onStart()

        onLoading(0, 100, false, true, isCompress)
    }

    override fun onStart() {
        onStart(false)
        LogUtils.e("onStart")
    }

    override fun getRate(): Long {
        return rateVar
    }

    override fun onLoading(progress: Long, total: Long, done: Boolean, isUpdate: Boolean) {
        LogUtils.e("onLoading")
        onLoading(progress, total, done, isUpdate, false)
    }

    fun onLoading(progress: Long, total: Long, done: Boolean, isUpdate: Boolean, isCompress: Boolean) {
        if (done) {
            buider.dismissUi()
            return
        }
        LogUtils.e("onLoading")
        buider.run {
            if (isShowProgress) {
                val percentage = (progress * 100.0 / total).toInt()

            }
        }
    }
}

