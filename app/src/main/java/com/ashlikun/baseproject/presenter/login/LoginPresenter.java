package com.ashlikun.baseproject.presenter.login;

import android.view.View;

import com.ashlikun.core.BasePresenter;
import com.ashlikun.okhttputils.http.response.HttpResult;
import com.ashlikun.baseproject.mode.httpquest.ApiService;
import com.ashlikun.baseproject.mode.javabean.base.UserData;
import com.ashlikun.baseproject.utils.http.HttpCallBack;
import com.ashlikun.baseproject.view.login.iview.IBLoginView;

/**
 * Created by yang on 2016/8/17.
 */
public class LoginPresenter extends BasePresenter<IBLoginView.IloginView> {
    public String phone;
    public String password;

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/10 11:25
     * <p>
     * 方法功能：用户登录	UserLogin	Mobile：手机号码
     * PassWord：密码
     */
    public void login() {
        HttpCallBack.Buider buider = HttpCallBack.Buider.get(this).setShowLoadding(true);
        HttpCallBack httpCallBack = new HttpCallBack<HttpResult<UserData>>(buider) {
            @Override
            public void onSuccess(HttpResult<UserData> result) {
                super.onSuccess(result);
                if (result.isSucceed() && result.getData() != null) {
                    result.getData().save();
                    mvpView.login(UserData.getUserData());
                } else {
                    mvpView.showErrorMessage(result.getMessage());
                }
            }
        };
        addHttpCall(ApiService.getApi().login(phone, password, httpCallBack));
    }


    public void onLoginClick(View v) {
        if (mvpView.checkData()) {
            login();
        }
    }
}
