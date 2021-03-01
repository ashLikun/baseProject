package com.ashlikun.baseproject.libcore.mode.javabean

import com.ashlikun.okhttputils.http.response.HttpResponse
import com.google.gson.annotations.SerializedName

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


    override fun toString(): String {
        return "HttpResult{" +
                "json='" + json + '\'' +
                ", httpcode=" + httpcode +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}'
    }
}