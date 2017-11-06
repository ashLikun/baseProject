package com.doludolu.baseproject.code;

import android.view.View;

import com.ashlikun.utils.encryption.AESUtils;
import com.doludolu.baseproject.code.iview.BaseView;

import java.util.ArrayList;


/**
 * 作者　　: 李坤
 * 创建时间: 16:21 Administrator
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public abstract class BaseListPresenter<D, T extends BaseView> extends BasePresenter<T>{
    public ArrayList<D> listDatas = new ArrayList<>();
}
