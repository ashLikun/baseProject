package com.ashlikun.baseproject.common.utils.extend

import android.os.Binder
import android.os.Bundle
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import com.alibaba.android.arouter.facade.Postcard
import com.ashlikun.baseproject.common.R
import java.io.Serializable

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
