package com.ashlikun.libcore.mvp.iview;

import com.ashlikun.core.iview.INotifyView;
import com.ashlikun.xrecycleview.PageHelp;
import com.ashlikun.xrecycleview.StatusChangListener;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　17:02
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：列表用的view接口
 */
public interface IBaseListView extends IBaseSwipeView, INotifyView {
    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:04
     * <p>
     * 方法功能：获取改变加载状态
     */
    StatusChangListener getStatusChangListener();


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:03
     * <p>
     * <p>
     * 方法功能：清空分页数据
     */

    void clearPaging();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:03
     * <p>
     * <p>
     * 方法功能：获取分页的页数
     */
    int getPageindex();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:04
     * <p>
     * <p>
     * 方法功能：获取分页的数量
     */

    int getPageCount();

    PageHelp getPageHelp();
}
