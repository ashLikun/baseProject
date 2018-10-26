package com.ashlikun.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.ashlikun.adapter.recyclerview.vlayout.MultipleAdapter;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/5/29 0029　上午 10:26
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：MultipleAdapter 的帮助类
 */
public abstract class MultipleAdapterHelp {
    protected MultipleAdapter adapter;
    protected Context context;

    public Context getContext() {
        return context;
    }

    public MultipleAdapter getAdapter() {
        return adapter;
    }

    public MultipleAdapterHelp(Context context, RecyclerView recyclerView) {
        this(context, recyclerView, true);
    }

    /**
     * @param hasConsistItemType 子适配器项类型是否一致,可以复用相同的viewtype
     */
    public MultipleAdapterHelp(Context context, RecyclerView recyclerView, boolean hasConsistItemType) {
        this.context = context;
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(context);
        this.adapter = new MultipleAdapter(layoutManager, hasConsistItemType);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    /**
     * 清空数据
     */
    public void clearData() {

    }
}
