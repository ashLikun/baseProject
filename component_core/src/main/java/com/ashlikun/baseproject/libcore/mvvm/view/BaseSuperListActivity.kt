package com.ashlikun.baseproject.libcore.mvvm.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ashlikun.baseproject.libcore.mvvm.viewmodel.BaseListViewModel
import com.ashlikun.loadswitch.OnLoadSwitchClick
import com.ashlikun.xrecycleview.PageHelpListener
import com.ashlikun.xrecycleview.RefreshLayout
import com.ashlikun.xrecycleview.SuperRecyclerView
import com.ashlikun.xrecycleview.listener.RecycleViewSwipeListener

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/27　16:43
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：列表(SuperRecyclerView)界面父类
 */
abstract class BaseSuperListActivity<VM : BaseListViewModel> : BaseListActivity<VM>(), RecycleViewSwipeListener, OnLoadSwitchClick {
    override fun initRecyclerView() {
        //这个必须放到上面执行
        superRecyclerView.setOnRefreshListener(this@BaseSuperListActivity)
        superRecyclerView.setOnLoaddingListener(this@BaseSuperListActivity)
        super.initRecyclerView()
    }

    open abstract val superRecyclerView: SuperRecyclerView

    override fun getSwitchRoot(): View? = switchRoot ?: superRecyclerView

    override val swipeRefreshLayout: RefreshLayout?
        get() = superRecyclerView.refreshLayout
    override val pageHelpListener: PageHelpListener?
        get() = superRecyclerView.pageHelpListener
    override val recyclerView: RecyclerView
        get() = superRecyclerView.recyclerView!!
}
