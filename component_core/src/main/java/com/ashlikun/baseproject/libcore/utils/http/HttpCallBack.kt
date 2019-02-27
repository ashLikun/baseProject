package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.baseproject.libcore.R
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.callback.AbsCallback
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.MainHandle
import com.ashlikun.utils.ui.SuperToast
import java.lang.reflect.*
import java.lang.reflect.Array

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:12
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：http请求的回调
 */

open class HttpCallBack<ResultType> constructor(private val buider: HttpCallbackHandle = HttpCallbackHandle.get())
    : AbsCallback<ResultType>() {
    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:40
     * 邮箱　　：496546144@qq.com
     *
     * 方法功能：请求开始
     */
    override fun onStart() {
        buider.goSetEnableView(false)
        if (buider.swipeRefreshLayout?.isRefreshing == true) {
            return
        }
        if (buider.loadSwitchService?.isLoadingCanShow == true) {
            buider.showLoading()
        } else {
            buider.showDialog()
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:43
     * 邮箱　　：496546144@qq.com
     *
     * 方法功能：请求完成
     */
    override fun onCompleted() {
        LogUtils.e("onCompleted")
        buider.goSetEnableView(true)
        dismissUi()
    }

    /**
     * 销毁页面的状态
     */
    open fun dismissUi() {
        buider.run {
            swipeRefreshLayout?.isRefreshing = false
            val isNoRun = basePresenter?.view == null && context == null
            if (isNoRun) {
                return
            }
            //如果count > 0，说明当前页面还有请求，就不销毁对话框
            if (count() > 0) {
                //是否等待,Okhttp内部在子线程里面执行的，这里延时1秒检测
                //可能会一直占用内存不释放，所以这里false
                MainHandle.get().postDelayed({
                    if (count() <= 0) {
                        dismissDialog()
                    }
                }, 1000)
                return
            }
            dismissDialog()
        }
    }

    /**
     * 销毁对话框
     */
    fun dismissDialog() {
        buider.hintProgress()
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/27 15:24
     *
     * 方法功能：处理完后的错误数据
     */

    open fun onError(data: ContextData) {
        if (data.title != null) {
            LogUtils.e(data.title)
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:43
     * 邮箱　　：496546144@qq.com
     *
     * 方法功能：请求出错
     */
    override fun onError(error: HttpException) {
        LogUtils.wtf(error)
        val data = ContextData()
        with(error) {
            data.title = message()
            data.errCode = code()
        }
        data.resId = R.drawable.material_service_error
        buider.run {
            SuperToast.showErrorMessage("${data.title}(错误码:)${data.errCode}")
            statusChangListener?.failure()
            if (loadSwitchService != null && isFirstRequest()) {
                loadSwitchService?.showRetry(data)
            }
        }
        onError(data)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/9/15 17:15
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：运行与子线程
     */

    override fun onSuccessSubThread(result: ResultType) {

    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     *
     *
     * 方法功能：请求成功
     */
    override fun onSuccess(result: ResultType) {
        onSuccess(result, true)
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/9/6 14:17
     * 邮箱　　：496546144@qq.com
     * 方法功能：接口请求成功了，但是处理code
     *
     * @return true:没问题
     * false:有问题
     */
    override fun onSuccessHandelCode(result: ResultType): Boolean {
        val res = HttpManager.handelResult(result)
        if (!res) {
            //如果code全局处理的时候错误了，那么是不会走success的，这里就得自己处理UI设置为错误状态
            onSuccess(result, true)
        }
        return res
    }


    /**
     * 作者　　: 李坤
     * 创建时间: 2017/7/3 13:44
     * 邮箱　　：496546144@qq.com
     *
     * 方法功能： 是否对错误信息处理
     */
    open fun onSuccess(result: ResultType, isHanderError: Boolean) {
        LogUtils.e("onSuccess")
        buider.run {
            statusChangListener?.complete()
            loadSwitchService?.let {
                if (result is HttpResponse && isHanderError) {
                    if (result.isSucceed) {
                        loadSwitchService?.showContent()
                    } else {
                        loadSwitchService?.showRetry(ContextData(result.getCode(), result.getMessage()))
                    }
                } else {
                    loadSwitchService?.showContent()
                }
            }
        }
    }

    fun getTag(): Any? = buider.getTag()
    /**
     * 获取当前泛型内部data->list或者数组
     */
    protected fun getListOrArray(): Any? {
        try {
            var res = classToListOrArray(this::class.java.genericSuperclass)
            return res?.newInstance()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun classToListOrArray(superClass: Type?): Class<*>? {
        if (superClass is ParameterizedType && superClass.actualTypeArguments?.isNullOrEmpty() == false) {
            var type1 = superClass.actualTypeArguments[0]
            return when {
                isTypeToListOrArray(type1) -> getRawType(type1)
                type1 is Class<*> -> classToListOrArray(type1.genericSuperclass)
                else -> classToListOrArray(type1)
            }
        } else if (superClass is Class<*>) {
            return classToListOrArray(superClass.genericSuperclass)
        }
        return null
    }

    /**
     * 这个type类型是否是List或者数组
     */
    private fun isTypeToListOrArray(type: Type?): Boolean {
        return when (type) {
            is Class<*> -> type == List::class.java || type == Array::class.java
            is ParameterizedType -> type.rawType == List::class.java || type.rawType == Array::class.java
            is GenericArrayType -> true
            is WildcardType -> isTypeToListOrArray(type.upperBounds[0])
            else -> false
        }
    }
// type不能直接实例化对象，通过type获取class的类型，然后实例化对象

    protected fun getRawType(type: Type?): Class<*> {
        when (type) {
            is Class<*> -> return type
            is ParameterizedType -> {
                //防止list为null
                if (type.rawType == List::class.java) {
                    return ArrayList::class.java
                }
                val parameterizedType = type as ParameterizedType?
                val rawType = parameterizedType!!.rawType
                return rawType as Class<*>
            }
            is GenericArrayType -> {
                val componentType = type.genericComponentType
                return Array.newInstance(getRawType(componentType), 0).javaClass
            }
            is TypeVariable<*> -> return Any::class.java
            is WildcardType -> return getRawType(type.upperBounds[0])
            else -> {
                val className = if (type == null) "null" else type.javaClass.name
                throw IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <$type> is of type $className")
            }
        }
    }
}

