package com.namei.jinjihu.libcore.utils.http

import com.ashlikun.baseproject.libcore.utils.http.HttpCallBack
import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.okhttputils.http.callback.ProgressCallBack
import com.ashlikun.utils.other.LogUtils

abstract class HttpProgressCallBack<ResultType>(private var buider2: HttpUiHandle = HttpUiHandle.get()) : HttpCallBack<ResultType>(buider2), ProgressCallBack {
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
            handle?.dismissUi()
            return
        }
        LogUtils.e("onLoading")
        handle?.run {
            if (isShowProgress) {
                val percentage = (progress * 100.0 / total).toInt()

            }
        }
    }
}

