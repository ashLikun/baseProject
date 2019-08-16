package com.ogow.libs.utils.extend

import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.ashlikun.utils.AppUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2019/5/22　14:12
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：Recycler的相关扩展
 */
/**
 * 解决官方的smoothScrollToPosition 滚动后不对其的bug
 */
fun RecyclerView.LayoutManager.smoothScrollToPositionAlign(position: Int, speed: Float = 25f, onStart: (() -> Unit)? = null, onStop: (() -> Unit)? = null) {
    val linearSmoothScroller = object : LinearSmoothScroller(AppUtils.getApp()) {
        //返回滑动一个pixel需要多少毫秒
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            //这边可以自定义进行控制速度
            return speed / (displayMetrics?.densityDpi?.toFloat() ?: 1f)
        }

        override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
            //保证准确的滑动到指定位置，返回的就是我们item置顶需要的偏移量
            return boxStart - viewStart
        }

        override fun onStart() {
            super.onStart()
            onStart?.invoke()
        }

        override fun onStop() {
            super.onStop()
            onStop?.invoke()
        }
    }
    linearSmoothScroller.targetPosition = position
    startSmoothScroll(linearSmoothScroller)
}