package com.ashlikun.common.mode.enumm

import android.support.annotation.IntDef


/**
 * 作者　　: 李坤
 * 创建时间:2017/9/24 0024　19:43
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：订单状态
 * （默认0：全部状态(或异常订单)，1：待付款，2：待使用，3：已完成（包含待评价），4：已取消,6：待评价）
 */

object OrderStatus {
    const val ALL = 0
    const val DFK = 1
    const val DSY = 2
    const val COMPLETE = 3
    const val CANCEL = 4
    const val DPJ = 6

    @IntDef(value = [ALL, DFK, DSY, COMPLETE, CANCEL, DPJ])
    @Retention(AnnotationRetention.SOURCE)
    annotation class Code

    fun isDfk(@Code code: Int): Boolean {
        return code == OrderStatus.DFK
    }

    fun isDsy(@Code code: Int): Boolean {
        return code == OrderStatus.DSY
    }

    fun isCancel(@Code code: Int): Boolean {
        return code == OrderStatus.CANCEL
    }

    fun isComplete(@Code code: Int): Boolean {
        return code == OrderStatus.COMPLETE
    }

    fun isDpj(@Code code: Int): Boolean {
        return code == OrderStatus.DPJ
    }


    fun getValue(@Code code: Int): CharSequence {
        return if (code == ALL) {
            ""
        } else if (code == DFK) {
            "等待付款"
        } else if (code == DSY) {
            "待使用"
        } else if (code == COMPLETE || code == DPJ) {
            "已完成"
        } else if (code == CANCEL) {
            "已取消"
        } else {
            ""
        }
    }
}
