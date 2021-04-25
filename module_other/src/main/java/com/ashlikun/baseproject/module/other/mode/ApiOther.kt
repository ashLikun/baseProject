package com.ashlikun.baseproject.module.other.mode

import com.ashlikun.baseproject.libcore.utils.http.*
import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.okhttputils.retrofit.ACTION
import com.ashlikun.okhttputils.retrofit.Field
import com.ashlikun.okhttputils.retrofit.Retrofit
import com.ashlikun.orm.db.annotation.Default


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：请求mode类
 */

interface ApiOther {
    companion object {
        val api: ApiOther by lazy { Retrofit.get().create(ApiOther::class.java) }
    }

    suspend fun testx(handle: HttpUiHandle
    ): HttpResponse? {
        return "index".requestGet()
                .syncExecute(handle) {}
    }

    @ACTION("getNewToken")
    suspend fun test(
            @Field("news_id")
            tikit: Int,
            handle: HttpUiHandle? = null,
    ): HttpResponse

}
