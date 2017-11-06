package com.doludolu.baseproject.code.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.doludolu.baseproject.code.BasePresenter;
import com.doludolu.baseproject.code.iview.BaseView;


/**
 * Created by Administrator on 2016/8/13.
 */

public abstract class BaseMvpFragment<T extends BasePresenter, DB extends ViewDataBinding> extends
        DataBindingFragment<DB> {

    public T presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isRecycle) {
            if (this instanceof BaseView) {
                if (presenter != null) {
                    presenter.onCreate((BaseView) this);
                }
            } else {
                new Exception("BaseMvpFragment 必须实现 BaseView");
            }
        }
    }
    @Override
    protected void baseInitView() {
        presenter = initPressenter();

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onLowMemory();
    }

    /**
     * 实例化Presenter对象
     *
     * @return
     */
    public abstract T initPressenter();
}
