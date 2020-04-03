package com.ashlikun.baseproject.libcore.mvp.presenter


import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.xrecycleview.PageHelp
import com.ashlikun.xrecycleview.RefreshLayout
import com.ashlikun.xrecycleview.StatusChangListener

/**
 * 作者　　: 李坤
 * 创建时间: 16:21 Administrator
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：列表形式的ViewModel
 */
abstract class BaseListViewModel : BaseViewModel() {
    //下拉刷新
    var swipeRefreshLayout: RefreshLayout? = null

    //分页状态
    var statusChangListener: StatusChangListener? = null

    //分页工具
    var pageHelp: PageHelp? = null
}
