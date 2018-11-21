package com.ashlikun.baseproject.libcore.mvp.iview

import com.ashlikun.core.iview.IBaseView
import com.ashlikun.xrecycleview.RefreshLayout

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　17:05
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：下拉刷新的基类接口
 */
interface IBaseSwipeView : IBaseView, RefreshLayout.OnRefreshListener {
    /**
     * 获取下拉刷新控件
     */
    fun getSwipeRefreshLayout(): RefreshLayout
}
