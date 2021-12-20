package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.baseproject.libcore.R
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.HttpException
import com.ashlikun.okhttputils.http.callback.AbsCallback
import com.ashlikun.utils.other.LogUtils
import com.google.gson.Gson
import okhttp3.Response
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


open class HttpCallBack<ResultType> constructor(val handle: HttpUiHandle?)
    : AbsCallback<ResultType>() {

    /**
     * 重写数据转换
     */
    override fun convertResponse(response: Response?, gosn: Gson?): ResultType {
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
        onError(ContextData().setErrCode(error.code())
                .setTitle(error.message())
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
            handle?.error(ContextData().setErrCode(res.exception.code())
                    .setTitle(res.exception.message())
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

    companion object {
        /**
         * 获取当前泛型内部data->list或者数组  或者 对象
         */
        fun getListOrArrayOrObject(resultType: Type): Any? {
            try {
                var res = classToListOrArrayOrObject(resultType)
                return res?.newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        fun classToListOrArrayOrObject(superClass: Type?): Class<*>? {
            if (superClass == null) {
                return null
            }
            if (superClass is ParameterizedType && !superClass.actualTypeArguments.isNullOrEmpty()) {
                var type1 = superClass.actualTypeArguments[0]
                return when {
                    isTypeToListOrArray(type1) -> getRawType(type1)
                    //返回对象
                    type1 is Class<*> -> type1
                    //找父类
                    else -> classToListOrArrayOrObject(type1)
                }
            } else if (superClass is Class<*>) {
                //找父类
                return classToListOrArrayOrObject(superClass.genericSuperclass)
            }
            return null
        }

        /**
         * 这个type类型是否是List或者数组
         */
        private fun isTypeToListOrArray(type: Type?): Boolean {
            return when (type) {
                is Class<*> -> type == List::class.java || type == Array::class.java
                is ParameterizedType -> {
                    if (type.rawType is Class<*>) {
                        if (List::class.java.isAssignableFrom((type.rawType as Class<*>))) {
                            true
                        } else Array::class.java.isAssignableFrom((type.rawType as Class<*>))
                    } else type == List::class.java || type == ArrayList::class.java || type == kotlin.Array<Any>::class.java
                }
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
                    if (type.rawType is Class<*>) {
                        if (List::class.java.isAssignableFrom((type.rawType as Class<*>))) {
                            return ArrayList::class.java
                        }
                    } else if (type == List::class.java || type == ArrayList::class.java || type == kotlin.Array<Any>::class.java) {
                        return kotlin.Array<Any>::class.java
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
                    val className = type?.javaClass?.name ?: "null"
                    throw IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <$type> is of type $className")
                }
            }
        }
    }
}


