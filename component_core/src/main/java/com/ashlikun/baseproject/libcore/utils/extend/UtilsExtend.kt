package com.ashlikun.baseproject.libcore.utils.extend

/**
 * 作者　　: 李坤
 * 创建时间: 2019/2/27　16:44
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：字符串扩展函数
 */
fun String?.isPositive() = this?.toDoubleOrNull() ?: 0.0 > 0

/**
 * 转换成double，错误就0
 */
fun String?.double() = this?.toDoubleOrNull() ?: 0.0

/**
 * 转换成int，错误就0
 */
fun String?.int() = this?.toDoubleOrNull() ?: 0