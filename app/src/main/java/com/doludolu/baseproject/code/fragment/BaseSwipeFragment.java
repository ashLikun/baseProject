package com.doludolu.baseproject.code.fragment;

import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.doludolu.baseproject.R;
import com.doludolu.baseproject.code.BasePresenter;

/**
 * Created by yang on 2016/8/22.
 * 下拉刷新的基类
 */

public abstract class BaseSwipeFragment<T extends BasePresenter, DB extends ViewDataBinding>
        extends BaseMvpFragment<T, DB> implements SwipeRefreshLayout.OnRefreshListener, OnLoadSwitchClick {
    @Nullable
    public SwipeRefreshLayout swipe;

    @Override
    public void initView() {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.switchRoot);
        if (swipe == null) {
            swipe = getSwipeRefreshLayout();
        }
        swipe.setOnRefreshListener(this);

    }

    /**
     * 获取下拉刷新控件
     */
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipe;
    }
}
