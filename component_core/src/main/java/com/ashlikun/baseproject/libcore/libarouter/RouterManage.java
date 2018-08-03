package com.ashlikun.baseproject.libcore.libarouter;

import android.app.Application;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.baseproject.libcore.libarouter.service.IHomeService;
import com.ashlikun.baseproject.libcore.libarouter.service.ILoginService;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:44
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：获取服务的管理器
 */
public class RouterManage {
    @Autowired
    IHomeService homeServic = null;

    @Autowired
    ILoginService loginService = null;


    private static volatile RouterManage instance = null;

    private RouterManage() {
        ARouter.getInstance().inject(this);
    }

    public static RouterManage get() {
        //双重校验DCL单例模式
        if (instance == null) {
            //同步代码块
            synchronized (RouterManage.class) {
                if (instance == null) {
                    //创建一个新的实例
                    instance = new RouterManage();
                }
            }
        }
        //返回一个实例
        return instance;
    }

    public static void init(Application app, boolean isDebug) {
        if (true) {
            ARouter.openLog();  // 打印日志
            ARouter.openDebug();    // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(app);      // 尽可能早，推荐在Application中初始化
    }

    public static boolean haveHome() {
        return get().homeServic != null;
    }

    public static IHomeService getHome() {
        return get().homeServic;
    }

    public static boolean haveLogin() {
        return get().loginService != null;
    }

    public static ILoginService getLogin() {
        return get().loginService;
    }
}
