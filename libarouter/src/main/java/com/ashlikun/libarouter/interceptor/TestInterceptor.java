package com.ashlikun.libarouter.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

// 比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
// 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
@Interceptor(priority = 9, name = "测试拦截器")
public class TestInterceptor implements IInterceptor {

    private Context mContext;

    @Override
    public void process(final Postcard postcard, final InterceptorCallback callback) {
        Log.d("ARouter", "TestInterceptor process");
        //这里加上自己的判断
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
	// 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
        this.mContext = context;
    }
}