package com.ashlikun.baseproject.libcore.utils.other

import com.ashlikun.utils.AppUtils
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
        val strategy = CrashReport.UserStrategy(AppUtils.app)
        strategy.appPackageName = AppUtils.packageName
        strategy.appVersion = AppUtils.versionName
        strategy.appChannel = FileUtils.getMetaValue("UMENG_CHANNEL")
        CrashReport.initCrashReport(AppUtils.app, FileUtils.getMetaValue("BUGLY_APPKEY"), AppUtils.isDebug, strategy)
    }
}
