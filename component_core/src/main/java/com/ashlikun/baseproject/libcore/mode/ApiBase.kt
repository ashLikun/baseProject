package com.ashlikun.baseproject.libcore.mode

import com.ashlikun.baseproject.libcore.utils.http.*
import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.okhttputils.retrofit.Action
import com.ashlikun.okhttputils.retrofit.FieldNo
import com.ashlikun.okhttputils.retrofit.Path
import com.ashlikun.okhttputils.retrofit.Url
import java.io.Serializable

/**
 * 作者　　: 李坤
 * 创建时间: 2021/5/12　15:19
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：必须有个方法或者变量，不然正式包的时候会丢失
 */
@Url(url = HttpManager.URL_PROD, method = ApiBase.method)
@Path(HttpManager.BASE_PATH)
@Action(HttpManager.ACTION)
interface ApiBase : Serializable {
    /**
     * 必须有个，不然打正式包的时候会丢失
     */
    companion object {
        const val method = "POST"
    }

    /**
     * retrofit+协成方式
     */
    suspend fun test(
        tikit: Int,
        @FieldNo
        handle: HttpUiHandle? = null,
    ): HttpResponse

    /**
     * 回调方式
     * 这里可以使用缓存
     */
    fun testxCall(
        handle: HttpUiHandle,
        success: OnSuccess<HttpResult<String>>
    ): ExecuteCall? {
        return "index".requestPost()
            .execute(handle, success)
    }
}