package com.ashlikun.libcore.mvp.iview

import com.ashlikun.core.iview.INotifyView
import com.ashlikun.xrecycleview.PageHelp
import com.ashlikun.xrecycleview.StatusChangListener

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/20　16:47
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：列表用的view接口
 */
interface IBaseListView : IBaseSwipeView, INotifyView {
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:04
     * <p>
     * 方法功能：获取改变加载状态
     */
    fun getStatusChangListener(): StatusChangListener

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:04
     *
     *
     * 方法功能：获取分页的有效数据
     */

    fun <T> getValidData(c: Collection<T>): Collection<T>


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:03
     *
     *
     * 方法功能：清空分页数据
     */

    fun clearPagingData()

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:03
     *
     *
     * 方法功能：获取分页的页数
     */


    fun getPageindex(): Int

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:04
     *
     *
     * 方法功能：获取分页的数量
     */

    fun getPageCount(): Int

    fun getPageHelp(): PageHelp
}