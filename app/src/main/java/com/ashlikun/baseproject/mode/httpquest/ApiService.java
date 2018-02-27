package com.ashlikun.baseproject.mode.httpquest;

import com.ashlikun.okhttputils.http.Callback;
import com.ashlikun.okhttputils.http.ExecuteCall;
import com.ashlikun.baseproject.utils.http.HttpRequestParam;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：请求mode类
 */

public class ApiService extends BaseApiService {
    private static ApiService api;

    public static ApiService getApi() {
        if (api == null) {
            synchronized (ApiService.class) {
                if (api == null) {
                    api = new ApiService();
                }
            }
        }
        return api;
    }

    /**
     * http://121.43.181.169/app/log.php
     * post
     * username,password
     */
    public ExecuteCall login(String telphone, String password, Callback callback) {
        HttpRequestParam p = new HttpRequestParam("log.php");
        p.addParam("username", telphone);
        p.addParam("password", password);
        return execute(p, callback);
    }

    /**
     * 加一个短信验证码的接口
     * http://121.43.181.169/app/sendmsg.php
     * post数据phone
     */
    public ExecuteCall sendMsg(String phone, Callback callback) {
        HttpRequestParam p = new HttpRequestParam("sendmsg.php");
        p.addParam("phone", phone);
        return execute(p, callback);
    }

    /**
     * Post
     * Username,password,type
     * Type=1 学生注册
     * Type=2 商户注册
     */
    public ExecuteCall register(int type, String telphone, String password, String code, Callback callback) {
        HttpRequestParam p = new HttpRequestParam("reg.php");
        p.addParam("username", telphone);
        p.addParam("password", password);
        p.addParam("type", type);
        p.addParam("codemsg", code);
        return execute(p, callback);
    }


    /**
     * 4.提交新密码,忘记密码
     * http://121.43.181.169/app/shemima.php
     * post
     * phone,password,type,token
     * type=1 学生登陆
     * type=2 商户登陆
     */
    public ExecuteCall upDataPassword(String phone, String password, String code, Callback callback) {
        HttpRequestParam p = new HttpRequestParam("shemima.php");
        p.addParam("phone", phone);
        p.addParam("password", password);
        p.addParam("codemsg", code);
        return execute(p, callback);
    }


    /**
     *
     */
    public ExecuteCall test2(String telphone, Callback callback) {
        HttpRequestParam p = new HttpRequestParam("log.php");
        p.addParam("username", telphone);
        return execute(p, callback);
    }


}
