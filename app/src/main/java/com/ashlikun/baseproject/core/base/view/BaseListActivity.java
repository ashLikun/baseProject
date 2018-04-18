package com.ashlikun.baseproject.core.base.view;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ashlikun.adapter.databind.recycleview.CommonBindAdapter;
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener;
import com.ashlikun.core.BasePresenter;
import com.ashlikun.core.activity.BaseMvpActivity;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.xrecycleview.PageHelp;
import com.ashlikun.xrecycleview.RefreshLayout;
import com.ashlikun.xrecycleview.StatusChangListener;
import com.ashlikun.xrecycleview.SuperRecyclerView;


/**
 * Created by Administrator on 2016/8/8.
 */

public abstract class BaseListActivity<P extends BasePresenter, VDB extends ViewDataBinding, D>
        extends BaseMvpActivity<P, VDB>
        implements SuperRecyclerView.ListSwipeViewListener, OnItemClickListener<D>,
        OnLoadSwitchClick {
    protected SuperRecyclerView listSwipeView;

    protected CommonBindAdapter adapter;

    public RefreshLayout getRefreshLayout() {
        return listSwipeView.getRefreshLayout();
    }

    public StatusChangListener getStatusChangListener() {
        return listSwipeView.getStatusChangListener();
    }

    @Override
    public void baseInitView() {
        super.baseInitView();
        listSwipeView = (SuperRecyclerView) findViewById(com.ashlikun.core.R.id.switchRoot);
        adapter = getAdapter();
        listSwipeView.getRecyclerView().addItemDecoration(getItemDecoration());
        listSwipeView.getRecyclerView().setLayoutManager(getLayoutManager());
        listSwipeView.setAdapter(adapter);
        listSwipeView.setOnRefreshListener(this);
        listSwipeView.setOnLoaddingListener(this);
        adapter.setOnItemClickListener(this);
    }


    public abstract RecyclerView.ItemDecoration getItemDecoration();

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    public abstract CommonBindAdapter getAdapter();

    public void clearData() {
    }


    public RefreshLayout getSwipeRefreshLayout() {
        return listSwipeView.getRefreshLayout();
    }

    public PageHelp getPageHelp() {
        return listSwipeView.getPageHelp();
    }

    public void clearPagingData() {
        listSwipeView.getPageHelp().clear();
    }

    public int getCurrentPage() {
        return listSwipeView.getPageHelp().getCurrentPage();
    }


    public void notifyChanged() {
        adapter.notifyDataSetChanged();
        if (presenter instanceof BaseListPresenter) {
            if (((BaseListPresenter) presenter).listDatas.isEmpty()) {
                loadSwitchService.showEmpty(new ContextData());
            }
        }

    }
}
