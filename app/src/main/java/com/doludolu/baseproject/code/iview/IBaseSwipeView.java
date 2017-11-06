package com.doludolu.baseproject.code.iview;

import com.ashlikun.xrecycleview.RefreshLayout;

/**
 * Created by Administrator on 2016/8/8.
 * <p>
 * 下拉刷新的基类接口
 */

public interface IBaseSwipeView extends BaseView, RefreshLayout.OnRefreshListener {


    /**
     * 获取下拉刷新控件
     */
    RefreshLayout getSwipeRefreshLayout();


}
