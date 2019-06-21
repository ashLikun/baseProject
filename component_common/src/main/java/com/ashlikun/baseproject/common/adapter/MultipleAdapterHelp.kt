package com.ashlikun.baseproject.common.adapter

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
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
        adapter.clear()
        //如果使用动态(个数不一致)addAddpter，那么这里要重新设置adapter，不然RecyclerView的缓存会使Position错乱，点击事件错乱
        recyclerView.adapter = adapter
    }
}