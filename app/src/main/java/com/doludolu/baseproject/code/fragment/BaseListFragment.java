package com.doludolu.baseproject.code.fragment;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ashlikun.adapter.databind.recycleview.CommonBindAdapter;
import com.ashlikun.adapter.recyclerview.click.OnItemClickListener;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.ashlikun.xrecycleview.PagingHelp;
import com.ashlikun.xrecycleview.RefreshLayout;
import com.ashlikun.xrecycleview.StatusChangListener;
import com.ashlikun.xrecycleview.SuperRecyclerView;
import com.doludolu.baseproject.R;
import com.doludolu.baseproject.code.BaseListPresenter;
import com.doludolu.baseproject.code.BasePresenter;

import java.util.Collection;


/**
 * Created by Administrator on 2016/8/8.
 */

public abstract class BaseListFragment<T extends BasePresenter, DB extends ViewDataBinding, D>
        extends BaseMvpFragment<T, DB>
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
        listSwipeView = (SuperRecyclerView) view.findViewById(R.id.switchRoot);
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

    /**
     * 获取分页的有效数据
     */
    public <T> Collection<T> getValidData(Collection<T> c) {
        return listSwipeView.getPagingHelp().getValidData(c);
    }

    public void clearData() {
    }

    public RefreshLayout getSwipeRefreshLayout() {
        return listSwipeView.getRefreshLayout();
    }

    public PagingHelp getPagingHelp() {
        return listSwipeView.getPagingHelp();
    }

    public void clearPagingData() {
        listSwipeView.getPagingHelp().clear();
    }

    public int getPageindex() {
        return listSwipeView.getPagingHelp().getPageindex();
    }

    public int getPageCount() {
        return listSwipeView.getPagingHelp().getPageCount();
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
