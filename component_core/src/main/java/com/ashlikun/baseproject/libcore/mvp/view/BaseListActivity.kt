package com.ashlikun.baseproject.libcore.mvp.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ashlikun.adapter.recyclerview.CommonAdapter
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener
import com.ashlikun.core.BasePresenter
import com.ashlikun.core.R
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
abstract class BaseListActivity<P : BasePresenter<*>, D> : BaseMvpActivity<P>()
        , RecycleViewSwipeListener, OnItemClickListener<D>, OnLoadSwitchClick {
    protected val listSwipeView by lazy { getSuperRecyclerView() }
    protected var commonAdapter: CommonAdapter<*>? = null

    val layoutManager: RecyclerView.LayoutManager
        get() = LinearLayoutManager(context)

    private fun getSuperRecyclerView(): SuperRecyclerView = f(R.id.switchRoot)


    override fun baseInitView() {
        super.baseInitView()
        commonAdapter = getAdapter()
        if (itemDecoration != null) {
            listSwipeView.recyclerView.addItemDecoration(itemDecoration!!)
        }
        listSwipeView.recyclerView.layoutManager = layoutManager
        listSwipeView.setAdapter(commonAdapter)
        listSwipeView.setOnRefreshListener(this)
        listSwipeView.setOnLoaddingListener(this)
        commonAdapter?.setOnItemClickListener(this)
    }

    abstract fun getAdapter(): CommonAdapter<*>?
    abstract val itemDecoration: RecyclerView.ItemDecoration?

    fun clearData() {}

    fun clearPaging() {
        listSwipeView.pageHelp.clear()
    }

    fun getSwipeRefreshLayout() = listSwipeView.getRefreshLayout()


    fun getPageHelp() = listSwipeView.pageHelp

    fun currentPage() = listSwipeView.pageHelp.currentPage
}
