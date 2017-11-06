package com.doludolu.baseproject.code.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.doludolu.baseproject.code.BasePresenter;
import com.doludolu.baseproject.code.iview.BaseView;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/7/3 9:54
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public abstract class BaseMvpActivity<P extends BasePresenter, DB extends ViewDataBinding>
        extends DataBindingActivity<DB> {

    public P presenter;

    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this instanceof BaseView) {
            if (presenter != null) {
                presenter.onCreate((BaseView) this);
            }
        } else {
            new Exception(this.getClass().getSimpleName() + " 必须实现 BaseView");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (this instanceof BaseView) {
            if (presenter != null) {
                presenter.onCreate((BaseView) this);
            }
        } else {
            new Exception(this.getClass().getSimpleName() + " 必须实现 BaseView");
        }
    }


    @Override
    protected void baseInitView() {
        super.baseInitView();
        presenter = initPressenter();

    }

    @Override
    protected void onStart() {
        presenter.onStart();
        super.onStart();

    }

    @Override
    protected void onResume() {
        presenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
    public abstract P initPressenter();


}
