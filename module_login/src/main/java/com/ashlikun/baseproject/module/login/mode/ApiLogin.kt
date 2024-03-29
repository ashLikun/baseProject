package com.ashlikun.baseproject.module.login.mode

import com.ashlikun.baseproject.libcore.mode.ApiBase
import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.baseproject.libcore.utils.http.requestGet
import com.ashlikun.baseproject.libcore.utils.http.requestPost
import com.ashlikun.baseproject.libcore.utils.http.syncExecute
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.okhttputils.http.response.HttpResult
import com.ashlikun.okhttputils.retrofit.Retrofit

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：请求mode类
 */

interface ApiLogin : ApiBase {
    companion object {
        val api: ApiLogin by lazy { Retrofit.get().create(ApiLogin::class.java) }
    }


    /**
     * 登录
     */
    suspend fun login(handle: HttpUiHandle,
                      telphone: String,
                      password: String): HttpResult<UserData>? {
        return "log.php".requestPost()
                .addParam("username", telphone)
                .addParam("password", password)
                .syncExecute(handle)
    }

    /**
     * 模板 同步
     */
    suspend fun test(handle: HttpUiHandle): HttpResult<String>? {
        return "index".requestGet()
                .syncExecute(handle)
    }


}
