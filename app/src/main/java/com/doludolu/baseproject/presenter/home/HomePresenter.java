package com.doludolu.baseproject.presenter.home;

import com.doludolu.baseproject.code.BasePresenter;
import com.doludolu.baseproject.mode.javabean.base.UserData;
import com.doludolu.baseproject.view.home.IBHomeView;


public class HomePresenter extends BasePresenter<IBHomeView.IHomeView> {


    @Override
    public void onCreate(IBHomeView.IHomeView mvpView) {
        super.onCreate(mvpView);
        mvpView.getDataBind().setPresenter(this);
    }

}
