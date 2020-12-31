package com.ashlikun.baseproject.libcore.mvvm.viewmodel


import androidx.lifecycle.MutableLiveData
import com.ashlikun.core.mvvm.BaseViewModel
import com.ashlikun.xrecycleview.PageHelp
import com.ashlikun.xrecycleview.PageHelpListener
import com.ashlikun.xrecycleview.RefreshLayout

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
    var pageHelpListener: PageHelpListener? = null

    //滚动列表
    val scrollPosition: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val pageHelp: PageHelp?
        get() = pageHelpListener?.pageHelp

    fun cleanPage() {
        pageHelpListener?.pageHelp?.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        swipeRefreshLayout = null
        pageHelpListener = null
    }
}