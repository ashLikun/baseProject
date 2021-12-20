package com.ashlikun.baseproject.libcore.mvvm.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashlikun.adapter.recyclerview.BaseAdapter
import com.ashlikun.adapter.recyclerview.vlayout.MultipleAdapter
import com.ashlikun.baseproject.libcore.mvvm.viewmodel.BaseListViewModel
import com.ashlikun.core.mvvm.BaseMvvmFragment
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
abstract class BaseListFragment<VM : BaseListViewModel> : BaseMvvmFragment<VM>(), RecycleViewSwipeListener, OnLoadSwitchClick {
    open val itemDecoration: RecyclerView.ItemDecoration? = null
    abstract val adapter: RecyclerView.Adapter<*>

    override val switchRoot: View?
        get() = recyclerView

    open val layoutManager: RecyclerView.LayoutManager by lazy {
        LinearLayoutManager(context)
    }

    override fun baseInitView() {
        super.baseInitView()
        initRecyclerView()
        viewModel.swipeRefreshLayout = swipeRefreshLayout
        viewModel.pageHelpListener = pageHelpListener
    }

    open fun initRecyclerView() {
        if (itemDecoration != null) {
            recyclerView.addItemDecoration(itemDecoration!!)
        }
        //MultipleAdapter 自己设置 layoutManager 和 adapter
        if (adapter is BaseAdapter<*, *>) {
            if (layoutManager != null) {
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
            }
        }
    }


    override fun clearData() {
        if (adapter is BaseAdapter<*, *>) {
            (adapter as BaseAdapter<*, *>).clearData(adapter.itemCount != 0)
        } else if (adapter is MultipleAdapter) {
            (adapter as MultipleAdapter).clear()
            //如果使用动态(个数不一致)addAddpter，那么这里要重新设置adapter，不然RecyclerView的缓存会使Position错乱，点击事件错乱
            recyclerView.adapter = adapter
        }
        scrollToPosition(0)
    }

    open fun notifyChanged() {
        adapter?.notifyDataSetChanged()
    }

    open abstract val recyclerView: RecyclerView
    open abstract val swipeRefreshLayout: RefreshLayout?
    open abstract val pageHelpListener: PageHelpListener?
    open fun scrollToPosition(position: Int) {
        recyclerView.scrollToPosition(position)
    }
}
