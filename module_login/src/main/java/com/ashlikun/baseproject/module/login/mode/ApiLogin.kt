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

    /**
     * http://121.43.181.169/app/log.php
     * post
     * username,password
     */
    fun login(telphone: String, password: String,
              callbackHandle: HttpCallbackHandle,
              success: OnSuccess<HttpResult<UserData>>): ExecuteCall {
        val p = HttpRequestParam.post("log.php")
        p.addParam("username", telphone)
        p.addParam("password", password)
        return execute(p, callbackHandle, success)
    }

    /**
     * 加一个短信验证码的接口
     * http://121.43.181.169/app/sendmsg.php
     * post数据phone
     */
    fun sendMsg(phone: String, callback: Callback<*>): ExecuteCall {
        val p = HttpRequestParam.post("sendmsg.php")
        p.addParam("phone", phone)
        return execute(p, callback)
    }

    /**
     * Post
     * Username,password,type
     * Type=1 学生注册
     * Type=2 商户注册
     */
    fun register(type: Int, telphone: String, password: String, code: String, callback: Callback<*>): ExecuteCall {
        val p = HttpRequestParam.post("reg.php")
        p.addParam("username", telphone)
        p.addParam("password", password)
        p.addParam("type", type)
        p.addParam("codemsg", code)
        return execute(p, callback)
    }


    /**
     * 4.提交新密码,忘记密码
     * http://121.43.181.169/app/shemima.php
     * post
     * phone,password,type,token
     * type=1 学生登陆
     * type=2 商户登陆
     */
    fun upDataPassword(phone: String, password: String, code: String, callback: Callback<*>): ExecuteCall {
        val p = HttpRequestParam.post("shemima.php")
        p.addParam("phone", phone)
        p.addParam("password", password)
        p.addParam("codemsg", code)
        return execute(p, callback)
    }


    fun test(handle: HttpCallbackHandle,
             success: OnSuccess<HttpResult<String>>): ExecuteCall {
        val callback = object : SimpleHttpCallback<HttpResult<String>>(handle) {}
        callback.success = success
        val p = HttpRequestParam.get("index")
        return execute(p, callback)
    }

    companion object {
        val api: ApiLogin by lazy { ApiLogin() }
    }


}
