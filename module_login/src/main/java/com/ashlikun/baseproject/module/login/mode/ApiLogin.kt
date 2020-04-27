package com.ashlikun.baseproject.module.login.mode

import com.ashlikun.baseproject.libcore.utils.http.*
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.okhttputils.http.ExecuteCall
import com.ashlikun.okhttputils.http.callback.Callback
import com.ashlikun.okhttputils.http.response.HttpResult

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：请求mode类
 */

class ApiLogin private constructor() : BaseApiService() {

    companion object {
        val api: ApiLogin by lazy { ApiLogin() }
    }

    /**
     * http://121.43.181.169/app/log.php
     * post
     * username,password
     */
    fun login(telphone: String,
              password: String,
              handle: HttpCallbackHandle,
              success: OnSuccess<HttpResult<UserData>>): ExecuteCall {
        return "log.php".requestPost()
                .addParam("username", telphone)
                .addParam("password", password)
                .execute(handle, success)
    }

    fun testx(handle: HttpCallbackHandle,
              success: OnSuccess<HttpResult<String>>): ExecuteCall? {
        return "index".requestGet()
                .execute(handle, success)
    }

}
