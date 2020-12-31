package com.ashlikun.baseproject.libcore.mvvm.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashlikun.adapter.recyclerview.BaseAdapter
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener
import com.ashlikun.adapter.recyclerview.vlayout.MultipleAdapter
import com.ashlikun.baseproject.libcore.mvvm.viewmodel.BaseListViewModel
import com.ashlikun.core.mvvm.BaseMvvmActivity
import com.ashlikun.loadswitch.OnLoadSwitchClick
import com.ashlikun.xrecycleview.PageHelpListener
import com.ashlikun.xrecycleview.RefreshLayout
import com.ashlikun.xrecycleview.listener.RecycleViewSwipeListener

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/27　16:43
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：列表界面父类
 */
abstract class BaseListActivity<VM : BaseListViewModel> : BaseMvvmActivity<VM>(), RecycleViewSwipeListener, OnLoadSwitchClick {
    open val itemDecoration: RecyclerView.ItemDecoration? = null
    abstract val adapter: RecyclerView.Adapter<*>
    open val layoutManager: RecyclerView.LayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun baseInitView() {
        super.baseInitView()
        initRecyclerView()
        viewModel.swipeRefreshLayout = getSwipeRefreshLayout()
        viewModel.pageHelpListener = getPageHelpListener()
    }

    override fun initData() {
        super.initData()
        viewModel.scrollPosition.observe(this, Observer {
            scrollToPosition(it)
        })
    }

    open fun initRecyclerView() {
        if (itemDecoration != null) {
            getRecyclerView().addItemDecoration(itemDecoration!!)
        }
        //MultipleAdapter 自己设置 layoutManager 和 adapter
        if (adapter is BaseAdapter<*, *>) {
            if (layoutManager != null) {
                getRecyclerView().layoutManager = layoutManager
                getRecyclerView().adapter = adapter
            }
        }
        if (adapter is BaseAdapter<*, *> && this is OnItemClickListener<*>) {
            (adapter as BaseAdapter<*, *>)?.setOnItemClickListener(this)
        }
    }

    override fun getSwitchRoot(): View? = getRecyclerView()

    override fun clearData() {
        if (adapter is BaseAdapter<*, *>) {
            (adapter as BaseAdapter<*, *>).clearData(adapter.itemCount != 0)
        } else if (adapter is MultipleAdapter) {
            (adapter as MultipleAdapter).clear()
            //如果使用动态(个数不一致)addAddpter，那么这里要重新设置adapter，不然RecyclerView的缓存会使Position错乱，点击事件错乱
            getRecyclerView().adapter = adapter
        }
        scrollToPosition(0)
    }

    open fun notifyChanged() {
        adapter?.notifyDataSetChanged()
    }

    open abstract fun getRecyclerView(): RecyclerView
    open abstract fun getSwipeRefreshLayout(): RefreshLayout?
    open abstract fun getPageHelpListener(): PageHelpListener?
    open fun scrollToPosition(position: Int) {
        getRecyclerView().scrollToPosition(position)
    }
}
