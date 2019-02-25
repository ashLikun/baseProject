package com.ashlikun.baseproject.libcore.mvp.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
abstract class BaseListFragment<P : BasePresenter<*>, D> : BaseMvpFragment<P>(), RecycleViewSwipeListener, OnItemClickListener<D>, OnLoadSwitchClick {
    abstract val itemDecoration: RecyclerView.ItemDecoration?
    abstract val adapter: RecyclerView.Adapter<*>?
    abstract fun getListAdapter(): RecyclerView.Adapter<*>?

    override fun initView() {
        getSuperRecyclerView().run {
            if (itemDecoration != null) {
                recyclerView.addItemDecoration(itemDecoration!!)
            }
            recyclerView.layoutManager = layoutManager
            setAdapter(adapter)
            setOnRefreshListener(this@BaseListFragment)
            setOnLoaddingListener(this@BaseListFragment)
        }
        if (adapter is BaseAdapter<*, *>) {
            (adapter as BaseAdapter<*, *>)?.setOnItemClickListener(this)
        }
    }

    abstract fun getSuperRecyclerView(): SuperRecyclerView
    override fun getSwitchRoot() = getSuperRecyclerView()
    val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(context)


    fun clearData() {
        if (adapter is BaseAdapter<*, *>) {
            (adapter as BaseAdapter<*, *>)?.clearData()
        }
    }

    fun notifyChanged() {
        if (adapter is BaseAdapter<*, *>) {
            (adapter as BaseAdapter<*, *>)?.notifyDataSetChanged()
        }
    }

    fun clearPaging() {
        getSuperRecyclerView().pageHelp?.clear()
    }

    fun getSwipeRefreshLayout() = getSuperRecyclerView().getRefreshLayout()


    fun getPageHelp() = getSuperRecyclerView().pageHelp

    fun getPageindex() = getPageHelp().currentPage

    fun getPageCount() = getSuperRecyclerView().pageHelp.currentPage

}