package com.ashlikun.baseproject.libcore.mvp.view

import android.view.View
import com.ashlikun.adapter.recyclerview.BaseAdapter
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener
import com.ashlikun.baseproject.libcore.mvp.presenter.BaseListViewModel
import com.ashlikun.loadswitch.OnLoadSwitchClick
import com.ashlikun.xrecycleview.SuperRecyclerView
import com.ashlikun.xrecycleview.listener.RecycleViewSwipeListener

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/27　16:43
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：列表(SuperRecyclerView)界面父类
 */
abstract class BaseSuperListActivity<VM : BaseListViewModel> : BaseListActivity<VM>()
        , RecycleViewSwipeListener, OnLoadSwitchClick {
    override fun initRecyclerView() {
        super.initRecyclerView()
        getSuperRecyclerView().setOnRefreshListener(this@BaseSuperListActivity)
        getSuperRecyclerView().setOnLoaddingListener(this@BaseSuperListActivity)
    }

    abstract fun getSuperRecyclerView(): SuperRecyclerView

    override fun getSwitchRoot(): View? = getSuperRecyclerView()
    override fun getSwipeRefreshLayout() = getSuperRecyclerView().refreshLayout
    override fun getStatusChangListener() = getSuperRecyclerView().statusChangListener
    override fun getPageHelp() = getSuperRecyclerView().pageHelp

}
