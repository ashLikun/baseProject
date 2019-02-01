package com.ashlikun.baseproject.module.other.mode

import com.ashlikun.baseproject.libcore.utils.http.BaseApiService
import com.ashlikun.baseproject.libcore.utils.http.HttpCallbackHandle
import com.ashlikun.baseproject.libcore.utils.http.HttpRequestParam
import com.ashlikun.baseproject.libcore.utils.http.SimpleHttpCallback
import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.response.HttpResult


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：请求mode类
 */

class ApiOther : BaseApiService() {
    companion object {
        public var api: ApiOther = ApiOther()
    }

    /**
     *
     */
    fun test(handle: HttpCallbackHandle,
             success: (result: HttpResult<String>) -> Unit): ExecuteCall {
        val callback = object : SimpleHttpCallback<HttpResult<String>>(handle) {}
        callback.success = success

        val p = HttpRequestParam("index")
        return execute(p, callback)
    }
}
