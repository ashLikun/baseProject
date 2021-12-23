package com.ashlikun.baseproject.common.utils.extend

import com.alibaba.android.arouter.facade.Postcard
import com.ashlikun.baseproject.common.R

/**
 * 作者　　: 李坤
 * 创建时间: 2020/6/8　17:54
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
fun Postcard.withTransitionNoAnim(noEnter: Boolean = false, noExit: Boolean = false): Postcard {
    this.withTransition(if (noEnter) 0 else R.anim.activity_open_enter, if (noExit) 0 else R.anim.activity_open_enter)
    return this
}
