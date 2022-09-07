package com.ashlikun.baseproject.libcore.utils.other

import com.ashlikun.baseproject.libcore.constant.EventBusKey
import com.ashlikun.baseproject.libcore.router.RouterManage
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.DeviceUtil
import com.ashlikun.utils.other.StringUtils
import com.ashlikun.utils.other.file.FileUtils
import com.tencent.bugly.crashreport.CrashReport

/**
 * 作者　　: 李坤
 * 创建时间: 2019/8/30　10:51
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
fun Throwable?.postBugly() {
    //正式环境上传错误
    if (this != null && !AppUtils.isDebug) {
        CrashReport.postCatchedException(this)
    }
}

/**
 * 初始化Bugly
 */
fun initBugly() {
    if (!AppUtils.isDebug) {
        EventBus.get(EventBusKey.LOGIN).observeForeverX {
            CrashReport.setUserId(RouterManage.login()?.getUserId() ?: "NoLogin")
        }
        EventBus.get(EventBusKey.EXIT_LOGIN).observeForeverX {
            CrashReport.setUserId(RouterManage.login()?.getUserId() ?: "NoLogin")
        }
        val strategy = CrashReport.UserStrategy(AppUtils.app)
        strategy.deviceID = DeviceUtil.soleDeviceId
        strategy.deviceModel = StringUtils.dataFilter(DeviceUtil.systemModel, DeviceUtil.deviceBrand)
        strategy.appPackageName = AppUtils.packageName
        strategy.appVersion = AppUtils.versionName
//        strategy.appReportDelay = 20 //改为20s
        //初始化，设置debug为false，防止日志重复打印
        CrashReport.initCrashReport(AppUtils.app, FileUtils.getMetaValue("BUGLY_APPKEY"), false, strategy)
        CrashReport.setUserId(RouterManage.login()?.getUserId() ?: "NoLogin")

    }
}
