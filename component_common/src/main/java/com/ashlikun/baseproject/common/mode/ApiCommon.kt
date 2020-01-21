package com.ashlikun.baseproject.common.mode

import com.ashlikun.baseproject.libcore.utils.http.*
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

class ApiCommon private constructor() : BaseApiService() {
    companion object {
        val api by lazy { ApiCommon() }
    }

    /**
     * 2用户输入手机验证码
     * action=user_login_in
     * mobile=手机号
     * type=1（操作类型：1手机号登录2微信登录3更换手机号）
     * code=验证码
     *
     *
     * [open_id]：用户第三方账号：type=2时必填
     * [access_token]：票据：type=2时必填
     */
    fun login(mobile: String, code: String, callback: Callback<*>): ExecuteCall {
        return querenCode(mobile, 1, code, callback)
    }


    /**
     * 2用户输入手机验证码
     * action=user_login_in
     * mobile=手机号
     * type=1（1注册登录2更换手机号3课程购买通知）
     * code=验证码
     */
    fun querenCode(mobile: String, type: Int, code: String, callback: Callback<*>): ExecuteCall {
        val p = HttpRequestParam.post("user_login_in")
        p.addParam("mobile", mobile)
        p.addParam("type", type)
        p.addParam("code", code)
        return execute(p, callback)
    }


    fun test(handle: HttpCallbackHandle,
             success: OnSuccess<HttpResult<String>>): ExecuteCall {
        val callback =  SimpleHttpCallback<HttpResult<String>>(handle)
        callback.success = success
        val p = HttpRequestParam.get("index")
        return execute(p, callback)
    }


}
