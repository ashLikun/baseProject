package com.ashlikun.baseproject.libcore.dialog

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.WindowManager
import com.ashlikun.customdialog.LoadDialog
import com.ashlikun.utils.other.DimensUtils
import com.ashlikun.baseproject.libcore.R

/**
 * 作者　　: 李坤
 * 创建时间: 2020/7/2　16:59
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：背景透明，只有红圈
 */
class LoadTransDialog(context: Context) : LoadDialog(context) {

    override fun initWindowParams(params: WindowManager.LayoutParams?) {
        super.initWindowParams(params)
        window?.setBackgroundDrawableResource(R.color.translucent)
    }


}