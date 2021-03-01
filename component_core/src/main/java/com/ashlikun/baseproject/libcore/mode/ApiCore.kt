package com.ashlikun.baseproject.libcore.mode

import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.okhttputils.retrofit.ACTION
import com.ashlikun.okhttputils.retrofit.Field
import com.ashlikun.okhttputils.retrofit.Retrofit

/**
 * 作者　　: 李坤
 * 创建时间: 2021/2/24　16:02
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
interface ApiCore {
    companion object {
        val api: ApiCore by lazy { Retrofit.get().create(ApiCore::class.java) }
    }

    @ACTION("getNewToken")
    suspend fun getNewToken(
            @Field(key = "news_id")
            tikit: Int,
            handle: HttpUiHandle? = null,
    ): HttpResponse
}