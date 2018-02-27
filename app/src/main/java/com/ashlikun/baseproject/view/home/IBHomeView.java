package com.ashlikun.baseproject.view.home;

import com.ashlikun.core.iview.IDatBindView;
import com.ashlikun.baseproject.core.base.iview.IBaseListView;
import com.ashlikun.baseproject.databinding.FragmentHomeBinding;

/**
 * 作者　　: 李坤
 * 创建时间:2016/12/2　8:48
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：首页几个V的合集
 */

public interface IBHomeView {
    interface IHomeView extends IDatBindView<FragmentHomeBinding> {
    }

    interface IHuodongView extends IBaseListView {

    }
}
