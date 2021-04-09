package com.ashlikun.baseproject.libcore.utils.http

import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.http.response.HttpResult

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:33
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */

open class BaseApiService {
    /**
     * 模板 异步
     */
    suspend fun testx(handle: HttpUiHandle
    ): HttpResponse? {
        return "index".requestGet()
                .syncExecute(handle) {}
    }

    /**
     * 协程  模板 同步
     */
    suspend fun testSync(handle: HttpUiHandle): HttpResult<List<String>>? {
        return "index".requestPost()
                .syncExecute(handle) {}
    }
}
