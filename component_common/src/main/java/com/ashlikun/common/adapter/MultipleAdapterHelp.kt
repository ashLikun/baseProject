package com.ashlikun.common.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView

import com.alibaba.android.vlayout.VirtualLayoutManager
import com.ashlikun.adapter.recyclerview.vlayout.MultipleAdapter

/**
 * 作者　　: 李坤
 * 创建时间: 2018/5/29 0029　上午 10:26
 * 邮箱　　：496546144@qq.com
 * @param hasConsistItemType 子适配器项类型是否一致,可以复用相同的viewtype
 *
 * 功能介绍：MultipleAdapter 的帮助类
 */
abstract class MultipleAdapterHelp @JvmOverloads constructor(context: Context, recyclerView: RecyclerView, hasConsistItemType: Boolean = true) {
    var adapter: MultipleAdapter
        private set
    var context: Context
        private set

    init {
        this.context = context
        val layoutManager = VirtualLayoutManager(context)
        this.adapter = MultipleAdapter(layoutManager, hasConsistItemType)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    /**
     * 清空数据
     */
    fun clearData() {

    }
}
