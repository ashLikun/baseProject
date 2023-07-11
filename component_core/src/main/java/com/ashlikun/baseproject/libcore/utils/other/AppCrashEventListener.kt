package com.ashlikun.baseproject.libcore.utils.other

import com.ashlikun.appcrash.CrashEventListener

/**
 * 作者　　: 李坤
 * 创建时间: 2019/8/29　14:27
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：app异常回调
 */
class AppCrashEventListener : CrashEventListener() {

    override fun onUncaughtExceptionHappened(thread: Thread?, throwable: Throwable?) {
        throwable?.postBugly()
    }

    override fun onBandageExceptionHappened(throwable: Throwable?) {
        throwable?.postBugly()
    }

    override fun onEnterSafeMode() {
    }
}