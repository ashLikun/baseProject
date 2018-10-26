package com.ashlikun.common.mode;

import com.ashlikun.baseproject.libcore.utils.http.BaseApiService;
import com.ashlikun.baseproject.libcore.utils.http.HttpCallBack;
import com.ashlikun.baseproject.libcore.utils.http.HttpRequestParam;
import com.ashlikun.okhttputils.http.ExecuteCall;
import com.ashlikun.okhttputils.http.callback.Callback;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：请求mode类
 */

public class ApiCommon extends BaseApiService {
    private static ApiCommon api;

    public static ApiCommon getApi() {
        if (api == null) {
            synchronized (ApiCommon.class) {
                if (api == null) {
                    api = new ApiCommon();
                }
            }
        }
        return api;
    }

    /**
     * 1用户获取短信验证码
     * action:user_login_sms
     *
     * @param phone    手机号
     * @param type     type=1（操作类型：1手机号登录2微信登录3更换手机号）
     * @param callback
     */
    public ExecuteCall sendCode(String phone, int type, HttpCallBack callback) {
        HttpRequestParam p = new HttpRequestParam("user_login_sms");
        p.addParam("mobile", phone);
        p.addParam("type", type);
        return execute(p, callback);
    }

    /**
     * 普通登录发送验证码
     */
    public ExecuteCall sendLoginCode(String phone, HttpCallBack callback) {
        return sendCode(phone, 1, callback);
    }

    /**
     * 微信绑定手机号,发送验证码
     */
    public ExecuteCall sendWxBindPhoneCode(String phone, HttpCallBack callback) {
        return sendCode(phone, 2, callback);
    }

    /**
     * 绑定手机号,发送验证码
     */
    public ExecuteCall sendBindPhoneCode(String phone, HttpCallBack callback) {
        return sendCode(phone, 3, callback);
    }

    /**
     * 2用户输入手机验证码
     * action=user_login_in
     * mobile=手机号
     * type=1（操作类型：1手机号登录2微信登录3更换手机号）
     * code=验证码
     * <p>
     * [open_id]：用户第三方账号：type=2时必填
     * [access_token]：票据：type=2时必填
     */
    public ExecuteCall login(String mobile, String code, Callback callback) {
        return ApiCommon.getApi().querenCode(mobile, 1, code, callback);
    }

    /**
     * 微信绑定手机号验证码完成
     * [open_id]：用户第三方账号：type=2时必填
     * [access_token]：票据：type=2时必填
     */
    public ExecuteCall wxBindPhoneCodeOk(String mobile, String code, String open_id, String access_token, Callback callback) {
        HttpRequestParam p = new HttpRequestParam("user_login_in");
        p.addParam("mobile", mobile);
        p.addParam("type", 2);
        p.addParam("code", code);
        p.addParam("open_id", open_id);
        p.addParam("access_token", access_token);
        return execute(p, callback);
    }

    /**
     * 修改绑定手机号验证码完成
     */
    public ExecuteCall bindPhoneCodeOk(String mobile, String code, Callback callback) {
        return ApiCommon.getApi().querenCode(mobile, 3, code, callback);
    }

    /**
     * 2用户输入手机验证码
     * action=user_login_in
     * mobile=手机号
     * type=1（1注册登录2更换手机号3课程购买通知）
     * code=验证码
     */
    public ExecuteCall querenCode(String mobile, int type, String code, Callback callback) {
        HttpRequestParam p = new HttpRequestParam("user_login_in");
        p.addParam("mobile", mobile);
        p.addParam("type", type);
        p.addParam("code", code);
        return execute(p, callback);
    }

    /**
     * 13第三方登录（微信登录）
     * action：wx_login
     * code：用户CODE
     */
    public ExecuteCall wxLogin(String code, String state, Callback callback) {
        HttpRequestParam p = new HttpRequestParam("wx_login");
        p.addParam("code", code);
        p.addParam("state", state);
        return execute(p, callback);
    }

    /**
     * 15获取微信登录：state验证字符
     */
    public ExecuteCall getWxLoginParams(Callback callback) {
        HttpRequestParam p = new HttpRequestParam("getweixinstate");
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
