package com.ashlikun.baseproject.module.main.view.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.baseproject.libcore.constant.RouterPath;
import com.ashlikun.baseproject.module.main.R;
import com.ashlikun.baseproject.module.main.iview.IBHomeView;
import com.ashlikun.baseproject.module.main.presenter.HomePresenter;
import com.ashlikun.core.factory.Presenter;
import com.ashlikun.core.fragment.BaseMvpFragment;
import com.ashlikun.loadswitch.ContextData;
import com.ashlikun.loadswitch.OnLoadSwitchClick;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/12 0012　21:17
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@Presenter(HomePresenter.class)
@Route(path = RouterPath.FRAGMENT_HOME)
public class HomeFragment extends BaseMvpFragment<HomePresenter>
        implements IBHomeView.IHomeView, OnLoadSwitchClick {


    @Override
    public int getLayoutId() {
        return R.layout.main_fragment_home;
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
