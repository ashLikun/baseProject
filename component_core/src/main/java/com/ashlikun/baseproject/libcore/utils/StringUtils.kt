package com.ashlikun.baseproject.libcore.utils

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/18　13:19
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
object StringUtils {
    //进行中和已完结  金额隐藏
    fun getMoney(isShowMoney: Boolean, money: Int): String {
        return if (!isShowMoney) {
            "¥***"
        } else "¥$money"
    }
}
