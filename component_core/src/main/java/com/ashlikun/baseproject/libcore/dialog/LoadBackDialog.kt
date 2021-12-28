package com.ashlikun.baseproject.libcore.dialog

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.WindowManager
import com.ashlikun.customdialog.LoadDialog
import com.ashlikun.utils.other.DimensUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2020/7/2　16:59
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
class LoadBackDialog(context: Context) : LoadDialog(context) {

    override fun initWindowParams(params: WindowManager.LayoutParams) {
        super.initWindowParams(params)
        window?.setBackgroundDrawable(getGradientDrawable2())
    }

    private fun getGradientDrawable2(): GradientDrawable? {
        val drawable = GradientDrawable()
        drawable.setColor(0x99000000.toInt())
        drawable.cornerRadius = DimensUtils.dip2px(10f).toFloat()
        return drawable
    }
}