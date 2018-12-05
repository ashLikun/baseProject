package com.ashlikun.libarouter

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.libarouter.service.IHomeService

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/19　14:17
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：获取服务的管理器
 */
public class ARouterManage {
    @Autowired
    lateinit var homeService: IHomeService

    init {
        ARouter.getInstance().inject(this)
    }


    companion object {
        private val instance: ARouterManage by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ARouterManage()
        }

        @JvmStatic
        fun get(): ARouterManage {
            return instance//返回一个实例
        }
    }
}

