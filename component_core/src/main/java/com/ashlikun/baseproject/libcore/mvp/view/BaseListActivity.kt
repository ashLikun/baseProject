package com.ashlikun.baseproject.libcore.mvp.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashlikun.adapter.recyclerview.BaseAdapter
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener
import com.ashlikun.baseproject.libcore.mvp.presenter.BaseListViewModel
import com.ashlikun.core.mvvm.BaseMvvmActivity
import com.ashlikun.loadswitch.OnLoadSwitchClick
import com.ashlikun.xrecycleview.PageHelp
import com.ashlikun.xrecycleview.RefreshLayout
import com.ashlikun.xrecycleview.StatusChangListener
import com.ashlikun.xrecycleview.listener.RecycleViewSwipeListener

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/27　16:43
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：列表界面父类
 */
abstract class BaseListActivity<VM : BaseListViewModel> : BaseMvvmActivity<VM>()
        , RecycleViewSwipeListener, OnLoadSwitchClick {
    abstract val itemDecoration: RecyclerView.ItemDecoration?
    abstract val adapter: RecyclerView.Adapter<*>?
    open val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(this)

    override fun baseInitView() {
        super.baseInitView()
        viewModel.swipeRefreshLayout = getSwipeRefreshLayout()
        viewModel.statusChangListener = getStatusChangListener()
        viewModel.pageHelp = getPageHelp()
        initRecyclerView()
    }

    open fun initRecyclerView() {
        if (itemDecoration != null) {
            getRecyclerView().addItemDecoration(itemDecoration!!)
        }
        getRecyclerView().layoutManager = layoutManager
        getRecyclerView().adapter = adapter
        if (adapter is BaseAdapter<*, *> && this is OnItemClickListener<*>) {
            (adapter as BaseAdapter<*, *>)?.setOnItemClickListener(this)
        }
    }

    override fun getSwitchRoot(): View? = getRecyclerView()

    override fun clearData() {
        (adapter as BaseAdapter<*, *>?)?.clearData()
    }

    open fun notifyChanged() {
        adapter?.notifyDataSetChanged()
    }

    open abstract fun getRecyclerView(): RecyclerView
    open abstract fun getSwipeRefreshLayout(): RefreshLayout?
    open abstract fun getStatusChangListener(): StatusChangListener?
    open abstract fun getPageHelp(): PageHelp?
    open fun scrollToPosition(position: Int) {
        getRecyclerView().scrollToPosition(position)
    }
}
