package com.lingyun.client.libcore.utils.http

import com.ashlikun.core.iview.IProgressView
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


    override fun dismissUi() {
        super.dismissUi()
        buider.run {
            if (isShowProgress) {
                if (baseActivity != null && baseActivity is IProgressView) {
                    (baseActivity as IProgressView).dismissProgressDialog()
                } else if (basePresenter?.view is IProgressView) {
                    (basePresenter?.view as IProgressView).dismissProgressDialog()
                }
            }
        }
        LogUtils.e("dismissUi")
    }

    override fun onLoading(progress: Long, total: Long, done: Boolean, isUpdate: Boolean) {
        LogUtils.e("onLoading")
        onLoading(progress, total, done, isUpdate, false)
    }

    fun onLoading(progress: Long, total: Long, done: Boolean, isUpdate: Boolean, isCompress: Boolean) {
        if (done) {
            dismissUi()
            return
        }
        LogUtils.e("onLoading")
        buider.run {
            if (isShowProgress) {
                val percentage = (progress * 100.0 / total).toInt()
                if (baseActivity is IProgressView) {
                    (baseActivity as IProgressView).upLoading(percentage, done, isUpdate, isCompress)
                } else if (basePresenter?.view is IProgressView) {
                    (basePresenter?.view as IProgressView).upLoading(percentage, done, isUpdate, isCompress)
                }
            }
        }
    }
}
