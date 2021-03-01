package com.ashlikun.baseproject.libcore.mvvm.view

import android.view.View
import com.ashlikun.baseproject.libcore.mvvm.viewmodel.BaseListViewModel
import com.ashlikun.baseproject.libcore.R
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
        //这个必须放到上面执行
        getSuperRecyclerView().setOnRefreshListener(this@BaseSuperListActivity)
        getSuperRecyclerView().setOnLoaddingListener(this@BaseSuperListActivity)
        super.initRecyclerView()
    }

    abstract fun getSuperRecyclerView(): SuperRecyclerView

    override fun getSwitchRoot(): View? = f(R.id.switchRoot) ?: getSuperRecyclerView()
    override fun getSwipeRefreshLayout() = getSuperRecyclerView().refreshLayout!!
    override fun getPageHelpListener() = getSuperRecyclerView().pageHelpListener!!
    override fun getRecyclerView() = getSuperRecyclerView().recyclerView!!

}
