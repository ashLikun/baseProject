package com.ashlikun.baseproject.presenter.home;

import android.os.Bundle;

import com.ashlikun.baseproject.mode.httpquest.ApiService;
import com.ashlikun.libcore.javabean.UserData;
import com.ashlikun.libcore.utils.http.HttpCallBack;
import com.ashlikun.baseproject.view.home.IBHomeView;
import com.ashlikun.core.BasePresenter;
import com.ashlikun.okhttputils.http.response.HttpResult;
import com.ashlikun.utils.ui.SuperToast;


public class HomePresenter extends BasePresenter<IBHomeView.IHomeView> {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer a = new Integer(22);
        int c = 22;
        Integer b = new Integer(22);
    }

    public void login() {
        HttpCallBack.Buider buider = HttpCallBack.Buider.get(this).setLoadSwitchService(mvpView.getSwitchService());
        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<UserData>>(buider) {
            @Override
            public void onSuccessSubThread(HttpResult<UserData> responseBody) {
                super.onSuccessSubThread(responseBody);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(HttpResult<UserData> result) {
                super.onSuccess(result);
                if (result.isSucceed() && result.getData() != null) {
                    result.getData().save();
                } else {
                    SuperToast.showErrorMessage(result.getMessage());
                }
            }
        };
        ApiService.getApi().login("18662288251", "18662288251", httpCallBack);
    }

    public void login2() {
        HttpCallBack.Buider buider = HttpCallBack.Buider.get(this).setLoadSwitchService(mvpView.getSwitchService());
        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<UserData>>(buider) {
            @Override
            public void onSuccessSubThread(HttpResult<UserData> responseBody) {
                super.onSuccessSubThread(responseBody);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(HttpResult<UserData> result) {
                super.onSuccess(result);
                if (result.isSucceed() && result.getData() != null) {
                    result.getData().save();
                } else {
                    SuperToast.showErrorMessage(result.getMessage());
                }
            }
        };
        ApiService.getApi().login("18662288251", "18662288251", httpCallBack);
    }
}
