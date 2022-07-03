package com.ashlikun.baseproject.libcore.dialog

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.WindowManager
import com.ashlikun.baseproject.libcore.R
import com.ashlikun.customdialog.DialogUtils
import com.ashlikun.customdialog.LoadDialog
import com.ashlikun.utils.ui.extend.dp
import com.ashlikun.utils.ui.extend.resColor

/**
 * 作者　　: 李坤
 * 创建时间: 2020/7/2　16:59
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：背景透明，只有红圈
 */
class LoadTransDialog(context: Context) : LoadDialog(context) {
    override val backgroundDrawable by lazy {
        GradientDrawable().apply {
            setColor(R.color.translucent.resColor)
            cornerRadius = 10.dp.toFloat()
        }
    }

    override fun initWindowParams(params: WindowManager.LayoutParams) {
        super.initWindowParams(params)
        window?.setBackgroundDrawableResource(R.color.translucent)
    }


}