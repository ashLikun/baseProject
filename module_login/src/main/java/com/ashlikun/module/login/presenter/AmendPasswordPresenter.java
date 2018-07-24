package com.ashlikun.module.login.presenter;

import android.os.Bundle;

import com.ashlikun.core.BasePresenter;
import com.ashlikun.libcore.utils.http.HttpCallBack;
import com.ashlikun.module.login.iview.IBLoginView;
import com.ashlikun.module.login.mode.ApiLogin;
import com.ashlikun.okhttputils.http.response.HttpResult;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.utils.ui.SuperToast;

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
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2016/12/10 13:45
     * <p>
     * 方法功能：
     */
    public void updateUserPwd() {
        if (!StringUtils.isEquals(password1, password2)) {
            SuperToast.showWarningMessage("两次密码不一致");
            return;
        }
        HttpCallBack.Buider buider = HttpCallBack.Buider.get(this);
        HttpCallBack httpCallBack = new HttpCallBack<HttpResult>(buider) {
            @Override
            public void onSuccess(HttpResult result) {
                super.onSuccess(result);
                if (result.isSucceed()) {
                    SuperToast.showInfoMessage(result.getMessage());
                    mvpView.finish();
                } else {
                    SuperToast.showErrorMessage(result.getMessage());
                }

            }
        };
        ApiLogin.getApi().upDataPassword(phone, password1, code, httpCallBack);
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
       ApiLogin.getApi().sendMsg(phone, httpCallBack);
    }
}
