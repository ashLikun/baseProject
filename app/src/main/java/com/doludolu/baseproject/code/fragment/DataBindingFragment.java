package com.doludolu.baseproject.code.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者　　: 李坤
 * 创建时间:2017/4/16 0016　11:56
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public abstract class DataBindingFragment<DB extends ViewDataBinding> extends BaseFragment {
    protected DB dataBind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (dataBind == null) {
            dataBind = DataBindingUtil.bind(view);
        }
        return view;
    }

    public DB getDataBind() {
        return dataBind;
    }
}
