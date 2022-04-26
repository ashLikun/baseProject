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
 * 保留小数后几位位，不用四舍五入
 * @param isJumpInt 整数的时候直接返回
 */
fun Number?.toFormat(wei: Int = 1, isJumpInt: Boolean = false): String {
    if (this == null) return ""
    if (isJumpInt && (this is Int || this is Long)) {
        return this.toString()
    }
    val format = DecimalFormat("#." + MutableList(wei) { "#" }.joinToString("") { it })
    //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
    format.roundingMode = RoundingMode.FLOOR
    return format.format(this)
}


fun String.reversalEvery2Charts(hasSpace: Boolean = false): String {
    val hex = this.addSpaceEvery2Charts()
    return hex.split(" ").reversed().joinToString(if (hasSpace) " " else "")
}

fun String.addSpaceEvery2Charts(): String {
    val hex = this.replace(" ", "")
    val sb = StringBuilder()
    for (i in 0 until hex.length / 2) {
        sb.append(hex.substring(i * 2, i * 2 + 2))
        sb.append(" ")
    }
    return sb.toString().trim()
}


fun String.ascii2ByteArray(hasSpace: Boolean = false): ByteArray {
    val s = if (hasSpace) this else this.replace(" ", "")
    return s.toByteArray(charset("US-ASCII"))
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
 *  1=是 0=否
 */
fun Int.toBoolean() = if (this == 1) true else false