package com.ashlikun.baseproject.module.other.mode

import com.ashlikun.baseproject.libcore.mode.ApiBase
import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.baseproject.libcore.utils.http.requestGet
import com.ashlikun.baseproject.libcore.utils.http.syncExecute
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.retrofit.Retrofit


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：请求mode类
 */

interface ApiOther : ApiBase {
    companion object {
        val api: ApiOther by lazy { Retrofit.get().create(ApiOther::class.java) }
    }

    suspend fun testx(handle: HttpUiHandle
    ): HttpResponse? {
        return "index".requestGet()
                .syncExecute(handle)
    }


}
