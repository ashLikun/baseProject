package com.ashlikun.baseproject.libcore.mode.javabean

import com.ashlikun.okhttputils.http.ClassUtils
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.google.gson.annotations.SerializedName
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 作者　　: 李坤
 * 创建时间: 2020/5/13　15:53
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：list形式的底层数据  和这个一样[HttpResult]
 */
open class HttpListResult<T> : HttpResponse() {
    //用来模仿Data

    @SerializedName("list")
    var data: T? = null

    /**
     * 新建的数据缓存,是给 HttpResult的data赋值
     */
    open val newData: T by lazy {
        ClassUtils.getListOrArrayOrObject(getHttpListResult(currentType)) as T
    }

    /**
     * 空判断，如果是null，就会初始化一个
     */
    open val dataX: T
        get() = (data ?: newData)

    /**
     * 获取HttpListResult类型
     */
    fun getHttpListResult(type: Type): Type? {
        when (type) {
            is ParameterizedType -> {
                //已经是的
                if (type.rawType == HttpListResult::class.java) {
                    return type
                }
                return getHttpListResult(type.rawType)
            }
            is Class<*> -> {
                return if (type == HttpListResult::class.java) {
                    type as Class<HttpListResult<*>>
                } else if (type.genericSuperclass != null && type.genericSuperclass != Any::class.java) {
                    //genericSuperclass 获取带泛型类型
                    getHttpListResult(type.genericSuperclass!!)
                } else {
                    null
                }
            }
            else -> {
                return null
            }
        }
    }

    override fun toString(): String {
        return "HttpListResult{" +
                "json='" + json + '\'' +
                ", httpcode=" + httpCode +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}'
    }
}