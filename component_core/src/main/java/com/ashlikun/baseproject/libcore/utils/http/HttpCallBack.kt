package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.baseproject.libcore.R
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.callback.AbsCallback
import com.ashlikun.utils.other.LogUtils
import com.google.gson.Gson
import okhttp3.Response

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:12
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：http请求的回调
 */


open class HttpCallBack<ResultType> constructor(val handle: HttpUiHandle?)
    : AbsCallback<ResultType>() {

    /**
     * 重写数据转换
     */
    override fun convertResponse(response: Response, gosn: Gson): ResultType {
        return super.convertResponse(response, gosn)
    }

    /**
     * 请求开始
     */
    override fun onStart() {
        handle?.start()
    }

    /**
     * 请求完成
     */
    override fun onCompleted() {
        handle?.completed()
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/27 15:24
     *
     * 处理完后的错误数据
     */

    open fun onError(data: ContextData) {
        handle?.error(data)
    }

    /**
     * 请求出错
     */
    override fun onError(error: HttpException) {
        LogUtils.wtf(error)
        onError(ContextData().setErrCode(error.code)
                .setTitle(error.message)
                .setResId(R.drawable.material_service_error))
    }

    /**
     * 运行与子线程
     */

    override fun onSuccessSubThread(result: ResultType) {

    }

    /**
     * 请求成功
     */
    override fun onSuccess(result: ResultType) {
        onSuccess(result, false)
    }

    /**
     * 接口请求成功了，但是处理code
     * @return true:没问题 false:有问题
     */
    override fun onSuccessHandelCode(result: ResultType): Boolean {
        return onSuccessHandelCode2(result) == null
    }

    open fun onSuccessHandelCode2(result: ResultType): HttpException? {
        //全局处理结果
        val res = HttpManager.handelResult(result)
        if (res != null) {
            //不显示toast
            handle?.isErrorToastShow = false
            //如果code全局处理的时候错误了，那么是不会走success的，这里就得自己处理UI设置为错误状态
            handle?.error(ContextData().setErrCode(res.exception.code)
                    .setTitle(res.exception.message)
                    .setResId(R.drawable.material_service_error))
        }
        return res
    }

    /**
     *  是否对错误信息处理
     */
    open fun onSuccess(result: ResultType, isHanderError: Boolean) {
        handle?.success(result as Any)
    }

}


