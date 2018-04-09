package com.ashlikun.libarouter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.libarouter.service.IHomeService;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/4/4 0004　下午 3:55
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：整个路由的管理器
 * 包括  一般启动四大组件
 * 服务（某个需要共享给其他模块调用的接口）提供者
 */

public class ARouterManage {
    private static volatile ARouterManage instance = null;
    @Autowired
    IHomeService homeService = null;

    private ARouterManage() {
        ARouter.getInstance().inject(this);
    }

    public static ARouterManage get() {
        if (instance == null) {//双重校验DCL单例模式
            synchronized (ARouterManage.class) {//同步代码块
                if (instance == null) {
                    instance = new ARouterManage();//创建一个新的实例
                }
            }
        }
        return instance;//返回一个实例
    }

    public IHomeService getHomeService() {
        return homeService;
    }
}
