package com.doludolu.baseproject.view.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.utils.other.Validators;
import com.ashlikun.utils.ui.EditHelper;
import com.doludolu.baseproject.R;
import com.doludolu.baseproject.code.ARouterFlag;
import com.doludolu.baseproject.code.activity.BaseMvpActivity;
import com.doludolu.baseproject.databinding.ActivityUpdataPasswordBinding;
import com.doludolu.baseproject.mode.javabean.base.UserData;
import com.doludolu.baseproject.presenter.login.UpDataPasswordPresenter;
import com.doludolu.baseproject.view.login.iview.IBLoginView;


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/11 15:25
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：修改密码
 */
@Route(path = ARouterFlag.UPDATA_PASSWORD)
public class UpdataPasswordActivity extends BaseMvpActivity<UpDataPasswordPresenter, ActivityUpdataPasswordBinding>
        implements IBLoginView.IUpDataPasswordView {
    public EditHelper editHelper = new EditHelper(this);

    @Override
    public int getLayoutId() {
        return R.layout.activity_updata_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }

    public void initView() {
        toolbar.setTitle("修改密码");
        toolbar.setBack(this);
        editHelper.addEditHelperData(
                new EditHelper.EditHelperData(dataBind.oldPasswordTil, Validators.getLengthSRegex(6, dataBind.oldPasswordTil.getCounterMaxLength()), "密码6-20位"));
        editHelper.addEditHelperData(
                new EditHelper.EditHelperData(dataBind.passwordTil1, Validators.getLengthSRegex(6, dataBind.passwordTil1.getCounterMaxLength()), "密码6-20位"));
        editHelper.addEditHelperData(
                new EditHelper.EditHelperData(dataBind.passwordTil2, Validators.getLengthSRegex(6, dataBind.passwordTil2.getCounterMaxLength()), "密码6-20位"));
    }


    @Override
    public void parseIntent(Intent intent) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public UpDataPasswordPresenter initPressenter() {
        return new UpDataPasswordPresenter();
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
