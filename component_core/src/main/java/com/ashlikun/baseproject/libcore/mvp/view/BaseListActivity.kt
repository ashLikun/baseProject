package com.ashlikun.baseproject.libcore.mvp.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashlikun.adapter.recyclerview.BaseAdapter
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener
import com.ashlikun.core.BasePresenter
import com.ashlikun.core.activity.BaseMvpActivity
import com.ashlikun.loadswitch.OnLoadSwitchClick
import com.ashlikun.xrecycleview.SuperRecyclerView
import com.ashlikun.xrecycleview.listener.RecycleViewSwipeListener

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/27　16:43
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：列表界面父类
 */
abstract class BaseListActivity<P : BasePresenter<*>> : BaseMvpActivity<P>()
        , RecycleViewSwipeListener, OnLoadSwitchClick {
    abstract val itemDecoration: RecyclerView.ItemDecoration?
    abstract val adapter: RecyclerView.Adapter<*>?
    open val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(context)

    override fun baseInitView() {
        super.baseInitView()
        getSuperRecyclerView().run {
            if (itemDecoration != null) {
                recyclerView.addItemDecoration(itemDecoration!!)
            }
            recyclerView.layoutManager = layoutManager
            setAdapter(adapter)
            setOnRefreshListener(this@BaseListActivity)
            setOnLoaddingListener(this@BaseListActivity)
        }
        if (adapter is BaseAdapter<*, *> && this is OnItemClickListener<*>) {
            (adapter as BaseAdapter<*, *>)?.setOnItemClickListener(this)
        }

    }

    abstract fun getSuperRecyclerView(): SuperRecyclerView
    override fun getSwitchRoot(): View? = getSuperRecyclerView()


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
