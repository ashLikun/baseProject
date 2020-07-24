package com.ashlikun.baseproject.libcore.utils.jni

/**
 * 作者　　: 李坤
 * 创建时间: 2020/6/28　15:39
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
object JniDemo1 {
    external fun nativeMethod()

    init {
        System.loadLibrary("native-lib")
    }
}