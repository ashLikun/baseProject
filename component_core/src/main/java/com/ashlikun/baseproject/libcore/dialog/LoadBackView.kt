package com.ashlikun.baseproject.libcore.dialog

import android.content.Context
import android.graphics.drawable.GradientDrawable
import com.ashlikun.customdialog.LoadView
import com.ashlikun.utils.ui.extend.dp

/**
 * 作者　　: 李坤
 * 创建时间: 2020/7/2　16:59
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
class LoadBackView(context: Context) : LoadView(context) {
    override val backgroundDrawable by lazy {
        GradientDrawable().apply {
            setColor(0x99000000.toInt())
            cornerRadius = 10.dp.toFloat()
        }
    }
}