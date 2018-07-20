package com.ashlikun.libcore.mvp.iview

import com.ashlikun.core.iview.BaseView
import com.ashlikun.xrecycleview.RefreshLayout

/**
 * Created by Administrator on 2016/8/8.
 *
 *
 * 下拉刷新的基类接口
 */

interface IBaseSwipeView : BaseView, RefreshLayout.OnRefreshListener {

    /**
     * 获取下拉刷新控件
     */
    fun getSwipeRefreshLayout(): RefreshLayout
}
