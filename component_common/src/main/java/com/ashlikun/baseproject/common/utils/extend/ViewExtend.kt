package com.ashlikun.baseproject.common.utils.extend

import android.view.View
import androidx.constraintlayout.widget.Group

/**
 * 作者　　: 李坤
 * 创建时间: 2020/6/3　15:58
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */

fun Group.addOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}
