package com.ashlikun.baseproject.presenter.login;

import android.view.View;

import com.ashlikun.baseproject.R;
import com.ashlikun.baseproject.mode.httpquest.ApiService;
import com.ashlikun.baseproject.view.login.iview.IBLoginView;
import com.ashlikun.core.BasePresenter;
import com.ashlikun.libcore.javabean.UserData;
import com.ashlikun.libcore.utils.http.HttpCallBack;
import com.ashlikun.okhttputils.http.response.HttpResult;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.utils.ui.SuperToast;

import static com.ashlikun.baseproject.core.MyApplication.myApp;

/**
 * Created by yang on 2016/8/17.
 */
public class RegisterPresenter extends BasePresenter<IBLoginView.IRegisterView> {
    public String phone;
    public String password;
    public String password2;
    public String code;
    public int registerType = 1;

    public void goRegister() {
        HttpCallBack.Buider buider = HttpCallBack.Buider.get(this);
        HttpCallBack<HttpResult<UserData>> callBack = new HttpCallBack<HttpResult<UserData>>(buider) {
            @Override
            public void onSuccess(HttpResult<UserData> result) {
                super.onSuccess(result, false);
                if (result.isSucceed() && result.getData() != null) {
                    result.getData().save();
                    mvpView.receiverUserData(result.getData());
                    SuperToast.showInfoMessage(result.getMessage());
                } else {
                    SuperToast.showWarningMessage(result.getMessage());
                }
            }
        };
        ApiService.getApi().register(registerType, phone, password, code, callBack);
    }

    public void sendMsg() {
        HttpCallBack.Buider buider = HttpCallBack.Buider.get(this);
        HttpCallBack httpCallBack = new HttpCallBack<HttpResult>(buider) {
            @Override
            public void onSuccess(HttpResult result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
//                    new CountdownUtils.Bulider()
//                            .setMsg(mvpView.getContext().getResources().getString(R.string.get_yanzenma))
//                            .bulid()
//                            .start(sendButton);
                } else {
                    SuperToast.showErrorMessage(result.getMessage());
                }

            }
        };
        ApiService.getApi().sendMsg(phone, httpCallBack);
    }

    public void onClickView(View view) {
        if (mvpView.checkData()) {
            if (!StringUtils.isEquals(password, password2)) {
                SuperToast.showWarningMessage(myApp.getResources().getString(R.string.inputCheckHint));
            } else {
                goRegister();
            }
        }
    }
}
