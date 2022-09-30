package com.ashlikun.baseproject.libcore.utils.other

import com.ashlikun.baseproject.libcore.BuildConfig

/**
 * 作者　　: 李坤
 * 创建时间: 2020/11/12　20:57
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
object AppConfig {
    /**
     * 构建的版本
     */
    const val isBeta = BuildConfig.BUILD_TYPE == "beta"
    const val isRelease = BuildConfig.BUILD_TYPE == "release"
    const val isDebug = BuildConfig.BUILD_TYPE == "debug"

}