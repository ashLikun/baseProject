package com.ashlikun.baseproject.module.main.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ashlikun.xviewpager.view.XViewPager

/**
 * 作者　　: 李坤
 * 创建时间: 2023/9/16　13:27
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
class MyViewPager @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    XViewPager(context, attrs) {
    init {
        setOnApplyWindowInsetsListener { v, insets ->
            insets.replaceSystemWindowInsets(v.left, v.top, v.right, v.bottom)
                .consumeSystemWindowInsets();
        }
    }
}