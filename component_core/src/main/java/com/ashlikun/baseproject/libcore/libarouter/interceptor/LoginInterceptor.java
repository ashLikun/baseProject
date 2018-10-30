package com.ashlikun.baseproject.libcore.libarouter.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.baseproject.libcore.libarouter.RouterManage;
import com.ashlikun.baseproject.libcore.constant.RouterPath;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:35
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：登录拦截器
 */
@Interceptor(priority = 1, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {
    Context context;
    public static InterceptorCallback callback;
    public static Postcard postcard;

    public static boolean isHaveRun() {
        return postcard != null && callback != null;
    }

    public static void run() {
        if (postcard != null && callback != null) {
            callback.onContinue(postcard);
        }
        LoginInterceptor.callback = null;
        LoginInterceptor.postcard = null;
        return;
    }


    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        //这里加上自己的判断
        if (RouterManage.haveLogin() && RouterManage.getLogin().isLogin()) {
            //已经登录，执行默认操作
            if (callback != null) {
                callback.onContinue(postcard);
            }
        } else {
            //没有登录,记录当前任务,登录后要清空这个任务
            LoginInterceptor.callback = callback;
            LoginInterceptor.postcard = postcard;
            //跳转登录
            ARouter.getInstance().build(RouterPath.LOGIN)
                    .greenChannel()
                    .navigation();
        }

    }

    @Override
    public void init(Context context) {
        this.context = context;
    }
}
