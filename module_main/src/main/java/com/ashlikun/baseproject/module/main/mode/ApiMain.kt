package com.ashlikun.baseproject.module.main.mode

import com.ashlikun.baseproject.libcore.utils.http.*
import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.okhttputils.retrofit.ACTION
import com.ashlikun.okhttputils.retrofit.Field
import com.ashlikun.okhttputils.retrofit.Retrofit

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：请求mode类
 */

interface ApiMain {
    companion object {
        val api: ApiMain by lazy { Retrofit.get().create(ApiMain::class.java) }
    }

    suspend fun testx(success: OnSuccess<HttpResult<String>>, handle: HttpUiHandle
    ): ExecuteCall? {
        return "index".requestGet()
                .syncExecute(handle, success)
    }

    @ACTION("getNewToken")
    suspend fun test(
            @Field(key = "news_id")
            tikit: Int,
            handle: HttpUiHandle? = null,
    ): HttpResponse

}
