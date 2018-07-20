package com.ashlikun.baseproject.presenter.login;

import android.os.Bundle;

import com.ashlikun.baseproject.view.login.iview.IBLoginView;
import com.ashlikun.core.BasePresenter;
import com.ashlikun.libcore.javabean.UserData;
import com.ashlikun.libcore.utils.http.HttpCallBack;
import com.ashlikun.okhttputils.http.response.HttpResult;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.utils.ui.SuperToast;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/31 0031  23:12
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：更新密码
 */

public class UpDataPasswordPresenter extends BasePresenter<IBLoginView.IUpDataPasswordView> {
    public String oldPassword;
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
     * 方法功能：UpdateUserPwd	NewPwd：新密码
     * AgainPwd：确认密码
     */
    public void updateUserPwd() {
        if (!UserData.isSLogin()) {
            return;
        }
        if (!StringUtils.isEquals(password1, password2)) {
            SuperToast.showWarningMessage("两次密码不一致");
            return;
        }
        HttpCallBack.Buider buider = HttpCallBack.Buider.get(this)
                .setShowLoadding(false)
                .setLoadSwitchService(mvpView.getSwitchService());
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
//        addHttpCall(ApiService.getApi().setPassword(oldPassword, password1, password2, httpCallBack));
    }
}
