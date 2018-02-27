package com.ashlikun.baseproject.core.base.view;


import com.ashlikun.core.BasePresenter;
import com.ashlikun.core.iview.BaseView;

import java.util.ArrayList;


/**
 * 作者　　: 李坤
 * 创建时间: 16:21 Administrator
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public abstract class BaseListPresenter<D, V extends BaseView> extends BasePresenter<V> {
    public ArrayList<D> listDatas = new ArrayList<>();
}
