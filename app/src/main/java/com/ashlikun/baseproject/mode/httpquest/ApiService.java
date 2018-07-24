package com.ashlikun.baseproject.mode.httpquest;

import com.ashlikun.libcore.utils.http.HttpRequestParam;
import com.ashlikun.libcore.utils.http.BaseApiService;
import com.ashlikun.okhttputils.http.ExecuteCall;
import com.ashlikun.okhttputils.http.callback.Callback;

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

}
