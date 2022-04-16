package com.ashlikun.baseproject.libcore.mode

import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.baseproject.libcore.utils.http.requestGet
import com.ashlikun.baseproject.libcore.utils.http.syncExecute
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.retrofit.Retrofit

/**
 * 作者　　: 李坤
 * 创建时间: 2021/2/24　16:02
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */

interface ApiCore : ApiBase {
    companion object {
        val api: ApiCore by lazy { Retrofit.get().create(ApiCore::class.java) }
    }

    suspend fun testx(handle: HttpUiHandle
    ): HttpResponse? {
        return "index".requestGet()
                .syncExecute(handle)
    }

}