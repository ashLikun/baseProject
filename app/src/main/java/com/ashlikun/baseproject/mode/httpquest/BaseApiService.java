package com.ashlikun.baseproject.mode.httpquest;

import com.ashlikun.baseproject.utils.http.HttpCallBack;
import com.ashlikun.okhttputils.http.Callback;
import com.ashlikun.okhttputils.http.ExecuteCall;
import com.ashlikun.okhttputils.http.SuperHttp;
import com.ashlikun.okhttputils.http.request.RequestCall;
import com.ashlikun.okhttputils.http.request.RequestParam;
import com.ashlikun.baseproject.utils.http.HttpManager;

import java.io.IOException;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:33
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class BaseApiService implements SuperHttp {
    //异步回调
    @Override
    public <T> ExecuteCall execute(RequestCall requestCall, Callback<T> callback) {
        if (requestCall.getRequestParam().getTag() == null) {
            if (callback instanceof HttpCallBack) {
                requestCall.getRequestParam().tag(((HttpCallBack) callback).getTag());
            }
        }
        return HttpManager.getInstance().execute(requestCall, callback);
    }

    //异步回调
    @Override
    public <T> ExecuteCall execute(RequestParam requestParam, Callback<T> callback) {
        if (requestParam.getTag() == null) {
            if (callback instanceof HttpCallBack) {
                requestParam.tag(((HttpCallBack) callback).getTag());
            }
        }
        return HttpManager.getInstance().execute(requestParam, callback);
    }

    //同步执行
    @Override
    public <ResultType> ResultType syncExecute(RequestCall requestCall, Class raw, final Class... args) throws IOException {
        return HttpManager.getInstance().syncExecute(requestCall, raw, args);
    }

    //同步执行
    @Override
    public <ResultType> ResultType syncExecute(RequestParam requestParam, Class raw, final Class... args) throws IOException {
        return HttpManager.getInstance().syncExecute(requestParam, raw, args);
    }
}
