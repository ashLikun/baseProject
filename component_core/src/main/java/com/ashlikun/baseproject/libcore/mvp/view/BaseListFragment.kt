package com.ashlikun.baseproject.libcore.mvp.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashlikun.adapter.recyclerview.BaseAdapter
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener
import com.ashlikun.core.BasePresenter
import com.ashlikun.core.fragment.BaseMvpFragment
import com.ashlikun.loadswitch.OnLoadSwitchClick
import com.ashlikun.xrecycleview.SuperRecyclerView
import com.ashlikun.xrecycleview.listener.RecycleViewSwipeListener


/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:32
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：封装列表的Fragment
 */
abstract class BaseListFragment<P : BasePresenter<*>> : BaseMvpFragment<P>(), RecycleViewSwipeListener, OnLoadSwitchClick {
    abstract val itemDecoration: RecyclerView.ItemDecoration?
    abstract val adapter: RecyclerView.Adapter<*>?
    override fun baseInitView() {
        super.baseInitView()
        getSuperRecyclerView().run {
            if (itemDecoration != null) {
                recyclerView.addItemDecoration(itemDecoration!!)
            }
            recyclerView.layoutManager = layoutManager
            setAdapter(adapter)
            setOnRefreshListener(this@BaseListFragment)
            setOnLoaddingListener(this@BaseListFragment)
        }
        if (adapter is BaseAdapter<*, *> && this is OnItemClickListener<*>) {
            (adapter as BaseAdapter<*, *>)?.setOnItemClickListener(this)
        }
    }

    abstract fun getSuperRecyclerView(): SuperRecyclerView
    override fun getSwitchRoot(): View? = getSuperRecyclerView()
    open val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(context)


    open fun clearData() {
        if (adapter is BaseAdapter<*, *>) {
            (adapter as BaseAdapter<*, *>)?.clearData()
        }
    }

    open fun notifyChanged() {
        if (adapter is BaseAdapter<*, *>) {
            (adapter as BaseAdapter<*, *>)?.notifyDataSetChanged()
        }
    }

    open fun clearPaging() {
        getSuperRecyclerView().pageHelp?.clear()
    }

    open fun getSwipeRefreshLayout() = getSuperRecyclerView().refreshLayout
    open fun getStatusChangListener() = getSuperRecyclerView().statusChangListener


    open fun getPageHelp() = getSuperRecyclerView().pageHelp

    open fun getPageindex() = getPageHelp().currentPage

    open fun getPageCount() = getSuperRecyclerView().pageHelp.currentPage

    open fun scrollToPosition(position: Int) {
        if (getSuperRecyclerView() != null) {
            getSuperRecyclerView().recyclerView.scrollToPosition(position)
        }
    }
}
