package com.doludolu.baseproject.code.iview;

import android.databinding.ViewDataBinding;

/**
 * Created by yang on 2016/8/29.
 */

public interface IDatBindView<DB extends ViewDataBinding> extends BaseView{
    public DB getDataBind();
}
