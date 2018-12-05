package com.ashlikun.libcore.mvp.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ashlikun.adapter.recyclerview.CommonAdapter
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener
import com.ashlikun.core.BasePresenter
import com.ashlikun.core.activity.BaseMvpActivity
import com.ashlikun.loadswitch.OnLoadSwitchClick
import com.ashlikun.xrecycleview.PageHelp
import com.ashlikun.xrecycleview.RefreshLayout
import com.ashlikun.xrecycleview.SuperRecyclerView
import com.ashlikun.xrecycleview.listener.RecycleViewSwipeListener

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/20　16:57
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：列表界面父类
 */
abstract class BaseListActivity<P : BasePresenter<*>, D> : BaseMvpActivity<P>(), RecycleViewSwipeListener, OnItemClickListener<D>,
        OnLoadSwitchClick {
    protected val listSwipeView: SuperRecyclerView by lazy {
        findViewById<SuperRecyclerView>(com.ashlikun.core.R.id.switchRoot)
    }

    protected val adapter: CommonAdapter<D> by lazy { getMyAdapter() }

    override fun baseInitView() {
        super.baseInitView()
        listSwipeView.recyclerView.addItemDecoration(getItemDecoration())
        listSwipeView.recyclerView.layoutManager = getLayoutManager()
        listSwipeView.setAdapter(adapter)
        listSwipeView.setOnRefreshListener(this)
        listSwipeView.setOnLoaddingListener(this)
        adapter.setOnItemClickListener(this)
    }

    public fun getRefreshLayout(): RefreshLayout {
        return listSwipeView.getRefreshLayout()
    }

    fun clearData() {}


    fun getSwipeRefreshLayout(): RefreshLayout {
        return listSwipeView.getRefreshLayout()
    }

    fun getPageHelp(): PageHelp {
        return listSwipeView.pageHelp
    }

    fun clearPaging() {
        listSwipeView.pageHelp.clear()
    }

    fun getCurrentPage(): Int {
        return listSwipeView.pageHelp.currentPage
    }

    /**
     * 获取adapter
     */
    abstract fun getMyAdapter(): CommonAdapter<D>

    /**
     * 获取分割线
     */
    abstract fun getItemDecoration(): RecyclerView.ItemDecoration

    /**
     * 获取布局管理器
     */
    fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }
}