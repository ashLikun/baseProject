package com.ashlikun.baseproject.libcore.libarouter

import android.app.Application
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.ashlikun.baseproject.libcore.libarouter.service.ICommonService
import com.ashlikun.baseproject.libcore.libarouter.service.IHomeService
import com.ashlikun.baseproject.libcore.libarouter.service.ILoginService
import com.ashlikun.baseproject.libcore.libarouter.service.IOtherService

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:44
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：获取服务的管理器
 */
class RouterManage private constructor() {
    @Autowired
    lateinit var commonService: ICommonService

    @Autowired
    lateinit var homeServic: IHomeService

    @Autowired
    lateinit var loginService: ILoginService

    @Autowired
    lateinit var otherService: IOtherService

    init {
        ARouter.getInstance().inject(this)
    }

    companion object {
        private val instance by lazy { RouterManage() }

        @JvmStatic
        fun get(): RouterManage {
            return instance
        }

        /**
         * 在Application初始化
         */
        fun init(app: Application, isDebug: Boolean) {
            if (isDebug) {
                ARouter.openLog()  // 打印日志
                ARouter.openDebug()    // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            }
            ARouter.init(app)      // 尽可能早，推荐在Application中初始化
            //get()::commonService.isInitialized
        }

        fun common(): ICommonService? =
                if (get()::commonService.isInitialized) {
                    get().commonService
                } else {
                    null
                }

        fun other(): IOtherService? =
                if (get()::otherService.isInitialized) {
                    get().otherService
                } else {
                    null
                }

        fun home(): IHomeService? =
                if (get()::homeServic.isInitialized) {
                    get().homeServic
                } else {
                    null
                }

        fun login(): ILoginService? =
                if (get()::loginService.isInitialized) {
                    get().loginService
                } else {
                    null
                }

        //是否登录
        fun isLogin(isToLogin: Boolean = false, isShowToast: Boolean = true) = login()?.isLogin(isToLogin, !isToLogin && isShowToast)
                ?: false

        fun isLogin() = login()?.isLogin(isToLogin = false, isShowToast = false)
                ?: false
    }
}
