package com.ashlikun.baseproject.module.main.mode

import androidx.recyclerview.widget.RecyclerView
import com.ashlikun.baseproject.libcore.utils.http.*
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

class ApiMain private constructor() : BaseApiService() {

    fun test(handle: HttpCallbackHandle,
             success: OnSuccess<HttpResult<String>>): ExecuteCall {
        val callback =  object :SimpleHttpCallback<HttpResult<String>>(handle){}
        callback.success = success
        val p = HttpRequestParam.get("index")
        return execute(p, callback)
    }

    companion object {
        val api: ApiMain by lazy { ApiMain() }
    }

}
