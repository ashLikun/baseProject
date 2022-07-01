package com.vinka.ebike.common.utils.ui

import android.widget.TextView
import com.ashlikun.baseproject.common.R
import com.ashlikun.utils.ui.extend.dp
import com.ashlikun.utils.ui.image.DrawableUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2022/4/19　17:39
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */

/**
 * 创建输入框的地步主线，焦点改变变成红色
 */
fun createEditDraw(textView: TextView) {
    DrawableUtils.createTextDraw(
        textView, location = 4, height = 1.dp, width = 1000.dp,
        drawable = DrawableUtils.getStateListDrawable(
            normal = DrawableUtils.getGradientDrawable(normalId = R.color.black_33),
            focused = DrawableUtils.getGradientDrawable(normalId = R.color.colorPrimary)
        )
    )
}