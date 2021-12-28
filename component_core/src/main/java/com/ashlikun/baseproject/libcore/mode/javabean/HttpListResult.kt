package com.ashlikun.baseproject.libcore.mode.javabean

import com.ashlikun.okhttputils.http.ClassUtils
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.Response
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
     * 去除空,只有在success的时候才会强制赋值
     */
    open val dataX: T
        get() = data!!

    override fun <M> parseData(gson: Gson, json: String, type: Type, response: Response?): M {
        return (super.parseData(gson, json, type, response) as M).apply {
            this as HttpListResult<T>
            //防止data是null
            if (this.isSucceed) {
                this.data = (this.data ?: ClassUtils.getListOrArrayOrObject(type)) as T?
            }
        }
    }

    override fun toString(): String {
        return "HttpResult{" +
                "json='" + json + '\'' +
                ", httpcode=" + httpCode +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}'
    }
}