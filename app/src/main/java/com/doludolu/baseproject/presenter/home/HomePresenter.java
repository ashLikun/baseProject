package com.doludolu.baseproject.presenter.home;

import android.os.Bundle;

import com.ashlikun.core.BasePresenter;
import com.doludolu.baseproject.view.home.IBHomeView;


public class HomePresenter extends BasePresenter<IBHomeView.IHomeView> {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpView.getDataBind().setPresenter(this);
    }
}
