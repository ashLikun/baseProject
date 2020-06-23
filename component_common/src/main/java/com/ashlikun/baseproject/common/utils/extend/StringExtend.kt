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

/**
 * double转String,三位三位的隔开
 * @param precision 保留几位小数不足位补0
 */
fun String.moneyFormat3(precision: Int): String {
    try {
        if (this == null || "0" == this || this.isEmpty()) {
            return this
        }
        if (precision <= 0) {
            val ff = DecimalFormat("#,##0")
            ff.roundingMode = RoundingMode.DOWN
            return ff.format(this.toDouble())
        }
        // #,##0.0000:金钱数字保留4位(不足补一位0)小数且三位三位的隔开
        val pattern = StringBuilder("#,##0.")
        for (i in 0 until precision) {
            pattern.append("0")
        }
        val ff = DecimalFormat(pattern.toString())
        ff.roundingMode = RoundingMode.DOWN
        return ff.format(this.toDouble())
    } catch (e: Exception) {
        return this
    }
}

/**
 * 获取小数点前面部分或者后面部分
 * @param isQian true：前面      false:后面
 */
fun String?.intDian(isQian: Boolean = true) = try {
    this?.run {
        val res = split(".")
        if (!res.isNullOrEmpty()) {
            if (isQian) {
                return res[0]
            } else if (res.size >= 2) {
                return res[1]
            }
        }
    }
    this
} catch (e: Exception) {
    this
}

/**
 * 装换万
 * @param isQian true：前面      false:后面
 */
fun Int.toWan(sub: String = "W") = if (this >= 10000) {
    try {
        var bd = BigDecimal(this / 10000.0)
                .setScale(1, RoundingMode.DOWN)
        "${bd}${sub}"
    } catch (e: Exception) {
        ""
    }
} else {
    this.toString()
}