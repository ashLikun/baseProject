package com.ashlikun.baseproject.common.utils.extend

import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.Group
import androidx.drawerlayout.widget.DrawerLayout
import com.ashlikun.utils.other.ClassUtils
import com.ashlikun.utils.ui.ScreenInfoUtils

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

fun DrawerLayout.setEdgeSize(size: Int = ScreenInfoUtils.width() / 2) {
    ClassUtils.setField(ClassUtils.getField(this, "mRightDragger"), "mEdgeSize", size)
}

fun View?.isTouchPoint(xAxis: Float, yAxis: Float): Boolean {
    if (this == null) {
        return false
    }
    val location = IntArray(2)
    getLocationOnScreen(location)
    val left = location[0]
    val top = location[1]
    val right: Int = left + measuredWidth
    val bottom: Int = top + measuredHeight
    return yAxis.toInt() in top..bottom && xAxis >= left && xAxis <= right
}

fun View?.touchEventToLocation(motionEvent: MotionEvent): MotionEvent {
    if (this == null) {
        return motionEvent
    }
    val location = IntArray(2)
    getLocationOnScreen(location)
    motionEvent.offsetLocation(-location[0].toFloat(), -location[1].toFloat())
    return motionEvent
}

/**
 * 防止多次点击
 */
inline fun View?.setOnSingleClickListener(delayMillis: Long = 500, crossinline onClick: (view: View) -> Unit) {
    this?.setOnClickListener {
        this.isClickable = false
        onClick(this)
        this.postDelayed({
            this.isClickable = true
        }, delayMillis)
    }
}