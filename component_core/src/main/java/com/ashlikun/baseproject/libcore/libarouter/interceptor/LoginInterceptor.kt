package com.ashlikun.baseproject.libcore.libarouter.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.libarouter.RouterManage

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:35
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：登录拦截器
 */
@Interceptor(priority = 1, name = "登录拦截器")
class LoginInterceptor : IInterceptor {
    internal var context: Context? = null


    override fun process(postcard: Postcard, callback: InterceptorCallback?) {
        //这里加上自己的判断
        if (RouterManage.getLogin().isLogin()) {
            //已经登录，执行默认操作
            callback?.onContinue(postcard)
        } else {
            //没有登录,记录当前任务,登录后要清空这个任务
            LoginInterceptor.callback = callback
            LoginInterceptor.postcard = postcard
            //跳转登录
            ARouter.getInstance().build(RouterPath.LOGIN)
                    .greenChannel()
                    .navigation()
        }

    }

    override fun init(context: Context) {
        this.context = context
    }

    companion object {
        var callback: InterceptorCallback? = null
        var postcard: Postcard? = null

        @JvmStatic
        fun isHaveRun() = postcard != null && callback != null

        @JvmStatic
        fun run() {
            if (postcard != null && callback != null) {
                callback!!.onContinue(postcard)
            }
            LoginInterceptor.callback = null
            LoginInterceptor.postcard = null
            return
        }
    }
}
