package com.doludolu.baseproject.view.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ashlikun.core.factory.Presenter;
import com.ashlikun.core.fragment.BaseMvpFragment;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.OnLoadSwitchClick;
import com.doludolu.baseproject.R;
import com.doludolu.baseproject.databinding.FragmentHomeBinding;
import com.doludolu.baseproject.presenter.home.HomePresenter;
import com.doludolu.baseproject.view.home.IBHomeView;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/12 0012　21:17
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@Presenter(HomePresenter.class)
public class HomeFragment extends BaseMvpFragment<HomePresenter, FragmentHomeBinding>
        implements IBHomeView.IHomeView, OnLoadSwitchClick {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        toolbar.setTitle("首页");

    }

    @Override
    public void clearData() {
    }


    @Override
    public void onRetryClick(ContextData data) {
    }


    @Override
    public void onEmptyClick(ContextData data) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
