package com.ashlikun.libcore.utils.http;

import com.ashlikun.okhttputils.http.ExecuteCall;
import com.ashlikun.okhttputils.http.OkHttpUtils;
import com.ashlikun.okhttputils.http.callback.Callback;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:33
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class BaseApiService {

    /**
     * http://121.43.181.169/app/log.php
     * post
     * username,password
     */
    public ExecuteCall execute(HttpRequestParam param, Callback callback) {
        if (param.getTag() == null) {
            if (callback instanceof HttpCallBack) {
                param.tag(((HttpCallBack) callback).getTag());
            }
        }
        return OkHttpUtils.request(param)
                .execute(callback);
    }
}
