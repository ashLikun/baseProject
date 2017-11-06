package com.doludolu.baseproject.presenter.login;

import android.view.View;

import com.ashlikun.okhttputils.http.response.HttpResult;
import com.ashlikun.utils.other.CountdownUtils;
import com.ashlikun.utils.other.StringUtils;
import com.doludolu.baseproject.R;
import com.doludolu.baseproject.code.BasePresenter;
import com.doludolu.baseproject.mode.httpquest.ApiService;
import com.doludolu.baseproject.mode.javabean.base.UserData;
import com.doludolu.baseproject.utils.http.HttpCallBack;
import com.doludolu.baseproject.view.login.iview.IBLoginView;

import static com.doludolu.baseproject.code.MyApplication.myApp;

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
                    mvpView.showInfoMessage(result.getMessage());
                } else {
                    mvpView.showWarningMessage(result.getMessage());
                }
            }
        };
        addHttpCall(ApiService.getApi().register(registerType, phone, password, code, callBack));
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
        addHttpCall(ApiService.getApi().sendMsg(phone, httpCallBack));
    }

    public void onClickView(View view) {
        if (mvpView.checkData()) {
            if (!StringUtils.isEquals(password, password2)) {
                mvpView.showWarningMessage(myApp.getResources().getString(R.string.inputCheckHint));
            } else {
                goRegister();
            }
        }
    }
}
