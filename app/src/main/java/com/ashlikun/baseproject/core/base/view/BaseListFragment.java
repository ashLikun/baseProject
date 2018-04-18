package com.ashlikun.baseproject.core.base.view;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ashlikun.adapter.databind.recycleview.CommonBindAdapter;
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener;
import com.ashlikun.core.BasePresenter;
import com.ashlikun.core.fragment.BaseMvpFragment;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.xrecycleview.PageHelp;
import com.ashlikun.xrecycleview.RefreshLayout;
import com.ashlikun.xrecycleview.StatusChangListener;
import com.ashlikun.xrecycleview.SuperRecyclerView;

import java.util.Collection;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/12/19 15:32
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：封装列表的Fragment
 */
public abstract class BaseListFragment<P extends BasePresenter, VDB extends ViewDataBinding, D>
        extends BaseMvpFragment<P, VDB>
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
    public void initView() {
        listSwipeView = (SuperRecyclerView) rootView.findViewById(com.ashlikun.core.R.id.switchRoot);
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

    public abstract CommonBindAdapter getAdapter();

    public abstract RecyclerView.ItemDecoration getItemDecoration();


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
