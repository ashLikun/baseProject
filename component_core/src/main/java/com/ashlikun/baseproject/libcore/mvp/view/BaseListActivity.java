package com.ashlikun.baseproject.libcore.mvp.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ashlikun.adapter.recyclerview.CommonAdapter;
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener;
import com.ashlikun.core.BasePresenter;
import com.ashlikun.core.activity.BaseMvpActivity;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.xrecycleview.PageHelp;
import com.ashlikun.xrecycleview.RefreshLayout;
import com.ashlikun.xrecycleview.SuperRecyclerView;
import com.ashlikun.xrecycleview.listener.RecycleViewSwipeListener;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/27　16:43
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：列表界面父类
 */
public abstract class BaseListActivity<P extends BasePresenter, D>
        extends BaseMvpActivity<P>
        implements RecycleViewSwipeListener, OnItemClickListener<D>,
        OnLoadSwitchClick {
    protected SuperRecyclerView listSwipeView;
    protected CommonAdapter adapter;

    @Override
    protected void baseInitView() {
        super.baseInitView();
        listSwipeView = f(com.ashlikun.core.R.id.switchRoot);
        adapter = getAdapter();

        RecyclerView.ItemDecoration itemDecoration = getItemDecoration();
        if (itemDecoration != null) {
            listSwipeView.getRecyclerView().addItemDecoration(itemDecoration);
        }
        listSwipeView.getRecyclerView().setLayoutManager(getLayoutManager());
        listSwipeView.setAdapter(adapter);
        listSwipeView.setOnRefreshListener(this);
        listSwipeView.setOnLoaddingListener(this);
        adapter.setOnItemClickListener(this);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    public abstract CommonAdapter getAdapter();

    public abstract RecyclerView.ItemDecoration getItemDecoration();


    public void clearData() {
    }

    public RefreshLayout getSwipeRefreshLayout() {
        return listSwipeView.getRefreshLayout();
    }

    public PageHelp getPageHelp() {
        return listSwipeView.getPageHelp();
    }

    public void clearPaging() {
        listSwipeView.getPageHelp().clear();
    }

    public int getCurrentPage() {
        return listSwipeView.getPageHelp().getCurrentPage();
    }
}
