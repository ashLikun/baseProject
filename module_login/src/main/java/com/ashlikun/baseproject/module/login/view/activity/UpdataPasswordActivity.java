package com.ashlikun.baseproject.module.login.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.core.activity.BaseMvpActivity;
import com.ashlikun.core.factory.Presenter;
import com.ashlikun.baseproject.libcore.constant.RouterPath;
import com.ashlikun.baseproject.module.login.R;
import com.ashlikun.baseproject.module.login.iview.IBLoginView;
import com.ashlikun.baseproject.module.login.mode.javabean.UserData;
import com.ashlikun.baseproject.module.login.presenter.UpDataPasswordPresenter;
import com.ashlikun.utils.ui.EditHelper;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/11 15:25
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：修改密码
 */
@Route(path = RouterPath.UPDATA_PASSWORD)
@Presenter(UpDataPasswordPresenter.class)
public class UpdataPasswordActivity extends BaseMvpActivity<UpDataPasswordPresenter>
        implements IBLoginView.IUpDataPasswordView {
    public EditHelper editHelper = new EditHelper(this);

    @Override
    public int getLayoutId() {
        return R.layout.login_activity_updata_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    @Override
    public void initView() {
        toolbar.setTitle("修改密码");
        toolbar.setBack(this);
//        editHelper.addEditHelperData(
//                new EditHelper.EditHelperData(dataBind.oldPasswordTil, Validators.getLengthSRegex(6, dataBind.oldPasswordTil.getCounterMaxLength()), "密码6-20位"));
//        editHelper.addEditHelperData(
//                new EditHelper.EditHelperData(dataBind.passwordTil1, Validators.getLengthSRegex(6, dataBind.passwordTil1.getCounterMaxLength()), "密码6-20位"));
//        editHelper.addEditHelperData(
//                new EditHelper.EditHelperData(dataBind.passwordTil2, Validators.getLengthSRegex(6, dataBind.passwordTil2.getCounterMaxLength()), "密码6-20位"));
    }


    @Override
    public void parseIntent(Intent intent) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void receiverUserData(UserData userData) {

        userData.getUserData().save();
    }

    @Override
    public void clearData() {

    }


    public void onClick(View view) {
        if (editHelper.check()) {
            presenter.updateUserPwd();
        }
    }
}
