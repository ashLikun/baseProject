package com.ashlikun.baseproject.presenter.login;

import android.os.Bundle;

import com.ashlikun.core.BasePresenter;
import com.ashlikun.okhttputils.http.response.HttpResult;
import com.ashlikun.utils.other.CountdownUtils;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.baseproject.R;
import com.ashlikun.baseproject.mode.httpquest.ApiService;
import com.ashlikun.baseproject.utils.http.HttpCallBack;
import com.ashlikun.baseproject.view.login.iview.IBLoginView;

/**
 * Created by yang on 2016/8/17.
 */
public class AmendPasswordPresenter extends BasePresenter<IBLoginView.IAmendPasswordView> {
    public String phone;
    public String code;
    public String password1;
    public String password2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpView.getDataBind().setPresenter(this);
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/10 13:45
     * <p>
     * 方法功能：
     */
    public void updateUserPwd() {
        if (!StringUtils.isEquals(password1, password2)) {
            mvpView.showWarningMessage("两次密码不一致");
            return;
        }
        HttpCallBack.Buider buider = HttpCallBack.Buider.get(this);
        HttpCallBack httpCallBack = new HttpCallBack<HttpResult>(buider) {
            @Override
            public void onSuccess(HttpResult result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    mvpView.showInfoMessage(result.getMessage());
                    mvpView.finish();
                } else {
                    mvpView.showErrorMessage(result.getMessage());
                }

            }
        };
        ApiService.getApi().upDataPassword(phone, password1, code, httpCallBack);
    }


    public void sendMsg() {
        HttpCallBack.Buider buider = HttpCallBack.Buider.get(this);
        HttpCallBack httpCallBack = new HttpCallBack<HttpResult>(buider) {
            @Override
            public void onSuccess(HttpResult result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    new CountdownUtils.Bulider()
                            .setMsg(mvpView.getContext().getResources().getString(R.string.get_yanzenma))
                            .bulid()
                            .start(mvpView.getDataBind().sendButton);
                } else {
                    mvpView.showErrorMessage(result.getMessage());
                }

            }
        };
       ApiService.getApi().sendMsg(phone, httpCallBack);
    }
}
