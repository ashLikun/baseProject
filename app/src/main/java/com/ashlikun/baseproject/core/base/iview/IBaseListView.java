package com.ashlikun.baseproject.core.base.iview;

import com.ashlikun.core.iview.INotifyView;
import com.ashlikun.xrecycleview.PageHelp;
import com.ashlikun.xrecycleview.StatusChangListener;

import java.util.Collection;


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
     * 创建时间: 2016/9/22 11:04
     * <p>
     * 方法功能：获取分页的有效数据
     */

    public <T> Collection<T> getValidData(Collection<T> c);


    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:03
     * <p>
     * 方法功能：清空分页数据
     */

    void clearPagingData();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:03
     * <p>
     * 方法功能：获取分页的页数
     */


    int getPageindex();

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/9/22 11:04
     * <p>
     * 方法功能：获取分页的数量
     */

    int getPageCount();

    PageHelp getPageHelp();
}
