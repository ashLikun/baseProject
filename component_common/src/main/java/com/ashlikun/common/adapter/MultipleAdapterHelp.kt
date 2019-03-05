package com.ashlikun.common.adapter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.support.v7.widget.RecyclerView

import com.alibaba.android.vlayout.VirtualLayoutManager
import com.ashlikun.adapter.recyclerview.vlayout.MultipleAdapter
import com.ashlikun.utils.main.ActivityUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2018/5/29 0029　上午 10:26
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：MultipleAdapter 的帮助类
 */
open class MultipleAdapterHelp
/**
 * @param hasConsistItemType 子适配器项类型是否一致,可以复用相同的viewtype
 */
@JvmOverloads constructor(context: Context, protected var recyclerView: RecyclerView, hasConsistItemType: Boolean = true) {
    var adapter: MultipleAdapter
        protected set
    var context: Context
        protected set
    var lifecycle: Lifecycle? = null
    var layoutManager: VirtualLayoutManager
        protected set

    init {
        this.context = context
        if (context is LifecycleOwner) {
            lifecycle = (context as LifecycleOwner).lifecycle
        } else {
            val activity = ActivityUtils.getActivity(context)
            if (activity is LifecycleOwner) {
                lifecycle = (activity as LifecycleOwner).lifecycle
            }
        }

        layoutManager = VirtualLayoutManager(context)
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