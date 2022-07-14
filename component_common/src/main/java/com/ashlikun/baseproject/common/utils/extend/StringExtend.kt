package com.ashlikun.baseproject.common.utils.extend

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * 作者　　: 李坤
 * 创建时间: 2019/1/23　15:12
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
inline fun String.ifNoEmpty(defaultValue: (String) -> String): String =
    if (isNotEmpty()) defaultValue(this) else this

/**
 * 不为“”就执行
 */
inline fun String.ifNoEmptyInvok(invok: (String) -> Unit): String {
    if (isNotEmpty()) invok(this)
    return this
}

inline fun String?.toIntOrEmpty(defaultValue: Int = 0): Int =
    this?.toIntOrNull() ?: defaultValue

inline fun String?.toFloatOrEmpty(defaultValue: Float = 0f): Float =
    this?.toFloatOrNull() ?: defaultValue

inline fun String?.toDoubleOrEmpty(defaultValue: Double = 0.0): Double =
    this?.toDoubleOrNull() ?: defaultValue

inline fun String?.toLongOrEmpty(defaultValue: Long = 0): Long =
    this?.toLongOrNull() ?: defaultValue


inline fun Int?.orEmpty(defaultValue: Int = 0): Int =
    this ?: defaultValue

inline fun Float?.orEmpty(defaultValue: Float = 0f): Float =
    (if (this?.isNaN() == true) null else this) ?: defaultValue

inline fun Double?.orEmpty(defaultValue: Double = 0.0): Double =
    (if (this?.isNaN() == true) null else this) ?: defaultValue

inline fun Long?.orEmpty(defaultValue: Long = 0): Long =
    this ?: defaultValue

fun Number?.toString() = this?.toString() ?: "0"

/**
 * 保留小数后几位位，不用四舍五入
 * 2.0-> 2
 * 2.222->2.2
 * 2.292->2.2
 * 2->2
 * @param isJumpInt 整数的时候直接返回
 */
fun Number.toFormat(wei: Int = 1, isJumpInt: Boolean = false): String {
    if (isJumpInt && (this is Int || this is Long)) {
        return this.toString()
    }
    val format =
        DecimalFormat("#" + if (wei == 0) "" else "." + MutableList(wei) { "#" }.joinToString("") { it })
    //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
    format.roundingMode = RoundingMode.FLOOR
    return format.format(this)
}

/**
 * 保留小数后几位位，四舍五入
 * @param isJumpInt 整数的时候直接返回
 */
fun Number.toFormat45(wei: Int = 1, isJumpInt: Boolean = false): String {
    if (isJumpInt && (this is Int || this is Long)) {
        return this.toString()
    }
    val format =
        DecimalFormat("#" + if (wei == 0) "" else "." + MutableList(wei) { "#" }.joinToString("") { it })
    //舍弃规则，RoundingMode.HALF_EVEN表示直接舍弃。
    format.roundingMode = RoundingMode.HALF_UP
    return format.format(this)
}


fun String.addFirst(s: String) = "$s$this"

fun String.addLast(s: String) = "$this$s"


/**
 * 秒变成 HH:MM:SS
 */
fun Int.formatHHmmss() =
    "%02d:%02d:%02d".format(this / 3600, ((this % 3600) / 60), ((this % 3600) % 60))

fun Int.formatHHmm() = "%02d:%02d".format(this / 3600, ((this % 3600) / 60))
fun Int.format02() = "%02d".format(this)

/**
 *  1=是 0=否
 */
fun Boolean.toInt() = if (this) 1 else 0

/**
 *  off=false on=1
 */
fun Boolean.toOffOrOn() = if (this) "on" else "off"
fun String.toOffOrOn() = if (this == "on") 1 else 0


/**
 *  1=是 0=否
 */
fun Int.toBoolean() = this == 1
fun String.toBooleanInt() = this == "1"


/**
 *  转换成 s min h
 */
fun Int.toTimeDanwei() = this == 1