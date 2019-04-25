package com.ashlikun.baseproject

import androidx.annotation.IntDef


/**
 * 作者　　: 李坤
 * 创建时间:2017/9/24 0024　19:43
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：模板枚举
 */

object DomeEnum {
    const val AUTH_NO = 0
    const val AUTH_ING = 1
    const val AUTH_YES = 2

    @IntDef(value = [AUTH_NO, AUTH_ING, AUTH_YES])
    @Retention(AnnotationRetention.SOURCE)
    annotation class Code

    fun getValue(@Code code: Int): CharSequence {
        return if (code == AUTH_NO) {
            "未认证"
        } else if (code == AUTH_ING) {
            "正在认证"
        } else if (code == AUTH_YES) {
            "已认证"
        } else {
            ""
        }
    }
}
