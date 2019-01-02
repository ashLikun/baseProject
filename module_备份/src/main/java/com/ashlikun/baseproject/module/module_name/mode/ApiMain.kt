package com.lingyun.client.module.main.mode

import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.utils.AppUtils
import com.ashlikun.xrecycleview.PageHelp
import com.lingyun.client.libcore.javabean.HttpPageResult
import com.lingyun.client.libcore.utils.http.BaseApiService
import com.lingyun.client.libcore.utils.http.HttpCallbackHandle
import com.lingyun.client.libcore.utils.http.HttpRequestParam
import com.lingyun.client.module.main.mode.javabean.*
import com.lingyun.common.mode.enumm.OrderStatus
import com.lingyun.common.mode.javabean.ImageData

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：请求mode类
 */

class ApiMain private constructor() : BaseApiService() {

    companion object {
        val api: ApiMain by lazy { ApiMain() }
    }


    fun test(handle: HttpCallbackHandle,
             success: (result: HttpResult<String>) -> Unit): ExecuteCall {
        val p = HttpRequestParam("index")
        return execute(p, handle, success)
    }

}