package com.ashlikun.baseproject.common.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.ashlikun.utils.other.LogUtils.d
import com.google.android.material.appbar.AppBarLayout
import java.lang.reflect.Field

/**
 * @author　　: 李坤
 * 创建时间: 2020/7/10 17:06
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：解决appbarLayout若干问题：
 * * （1）快速滑动appbarLayout会出现回弹
 * * （2）快速滑动appbarLayout到折叠状态下，立马下滑，会出现抖动的问题
 * * （3）滑动appbarLayout，无法通过手指按下让其停止滑动
 */
class AppBarLayoutBehavior @JvmOverloads constructor(context: Context?, attrs: AttributeSet?) :
    AppBarLayout.Behavior(context, attrs) {
    private var isFlinging = false
    private var shouldBlockNestedScroll = false


    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        d("onInterceptTouchEvent:" + child.totalScrollRange)
        shouldBlockNestedScroll = false
        if (isFlinging) {
            shouldBlockNestedScroll = true
        }
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN ->                 //手指触摸屏幕的时候停止fling事件
                stopAppbarLayoutFling(child)
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }// 可能是28及以上版本// support design 27及以下版本

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     *
     * @return Field
     */
    @get:Throws(NoSuchFieldException::class)
    private val flingRunnableField: Field
        private get() = try {
            // support design 27及以下版本
            val headerBehaviorType: Class<*> = this.javaClass.superclass.superclass
            headerBehaviorType.getDeclaredField("mFlingRunnable")
        } catch (e: NoSuchFieldException) {
            // 可能是28及以上版本
            val headerBehaviorType: Class<*> = this.javaClass.superclass.superclass.superclass
            headerBehaviorType.getDeclaredField("flingRunnable")
        }// 可能是28及以上版本// support design 27及以下版本

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     *
     * @return Field
     */
    @get:Throws(NoSuchFieldException::class)
    private val scrollerField: Field
        private get() = try {
            // support design 27及以下版本
            val headerBehaviorType: Class<*> = this.javaClass.superclass.superclass
            headerBehaviorType.getDeclaredField("mScroller")
        } catch (e: NoSuchFieldException) {
            // 可能是28及以上版本
            val headerBehaviorType: Class<*> = this.javaClass.superclass.superclass.superclass
            headerBehaviorType.getDeclaredField("scroller")
        }

    /**
     * 停止appbarLayout的fling事件
     *
     * @param appBarLayout
     */
    private fun stopAppbarLayoutFling(appBarLayout: AppBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            val flingRunnableField = flingRunnableField
            val scrollerField = scrollerField
            flingRunnableField.isAccessible = true
            scrollerField.isAccessible = true
            val flingRunnable = flingRunnableField[this] as Runnable
            val overScroller = scrollerField[this] as OverScroller
            if (flingRunnable != null) {
                d("存在flingRunnable")
                appBarLayout.removeCallbacks(flingRunnable)
                flingRunnableField[this] = null
            }
            if (overScroller != null && !overScroller.isFinished) {
                overScroller.abortAnimation()
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun onStartNestedScroll(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        d("onStartNestedScroll")
        stopAppbarLayoutFling(child)
        return super.onStartNestedScroll(
            parent,
            child,
            directTargetChild,
            target,
            nestedScrollAxes,
            type
        )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        d("onNestedPreScroll:" + child.totalScrollRange + " ,dx:" + dx + " ,dy:" + dy + " ,type:" + type)
        //type返回1时，表示当前target处于非touch的滑动，
        //该bug的引起是因为appbar在滑动时，CoordinatorLayout内的实现NestedScrollingChild2接口的滑动子类还未结束其自身的fling
        //所以这里监听子类的非touch时的滑动，然后block掉滑动事件传递给AppBarLayout
        if (type == TYPE_FLING) {
            isFlinging = true
        }
        if (!shouldBlockNestedScroll) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        d(
            "onNestedScroll: target:" + target.javaClass + " ," + child.totalScrollRange + " ,dxConsumed:"
                    + dxConsumed + " ,dyConsumed:" + dyConsumed + " " + ",type:" + type
        )
        if (!shouldBlockNestedScroll) {
            super.onNestedScroll(
                coordinatorLayout,
                child,
                target,
                dxConsumed,
                dyConsumed,
                dxUnconsumed,
                dyUnconsumed,
                type,
                consumed
            )
        }
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        abl: AppBarLayout,
        target: View,
        type: Int
    ) {
        d("onStopNestedScroll")
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
        isFlinging = false
        shouldBlockNestedScroll = false
    }

    companion object {
        private const val TYPE_FLING = 1
    }
}