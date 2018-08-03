package com.ashlikun.baseproject.libcore.libarouter.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:35
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：功能介绍：比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
 * 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
 */
@Interceptor(priority = 1, name = "拦截测试")
public class TestInterceptor implements IInterceptor {
    Context context;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.d("ARouter", "TestInterceptor process");
        //这里加上自己的判断
        //执行默认操作
        if (callback != null) {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }
}
