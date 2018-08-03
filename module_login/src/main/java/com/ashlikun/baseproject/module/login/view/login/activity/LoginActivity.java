package com.ashlikun.baseproject.module.login.view.login.activity;

import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.core.activity.BaseMvpActivity;
import com.ashlikun.core.factory.Presenter;
import com.ashlikun.baseproject.libcore.libarouter.constant.RouterPath;
import com.ashlikun.baseproject.module.login.R;
import com.ashlikun.baseproject.module.login.iview.IBLoginView;
import com.ashlikun.baseproject.module.login.mode.javaben.UserData;
import com.ashlikun.baseproject.module.login.presenter.LoginPresenter;
import com.ashlikun.utils.ui.EditHelper;


/**
 * 登录页面
 * *
 */
@Route(path = RouterPath.LOGIN)
@Presenter(LoginPresenter.class)
public class LoginActivity extends BaseMvpActivity<LoginPresenter>
        implements IBLoginView.IloginView {
    public EditHelper editHelper = new EditHelper(this);

    @Override
    public int getLayoutId() {
        return R.layout.login_activity_login;
    }


    @Override
    public void parseIntent(Intent intent) {
    }

    @Override
    public void initView() {
        toolbar.setTitle("登录");
        toolbar.setBack(this);
//        editHelper.addEditHelperData(
//                new EditHelper.EditHelperData(dataBind.phoneTil, Validators.REGEX_PHONE_NUMBER, "请正确输入11位手机号"));
//        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.passwordTil,
//                Validators.getLengthSRegex(6, dataBind.passwordTil.getCounterMaxLength()), "密码6-20位"));
//        dataBind.setPresenter(presenter);
    }

    @Override
    public boolean checkData() {
        return editHelper.check();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void clearData() {

    }

    public void onWangjiClick(View view) {
        ARouter.getInstance().build(RouterPath.AMEND_PASSWORD)
                .navigation(this);
    }

    @Override
    public void login(UserData data) {
        if (UserData.isLogin()) {
            ARouter.getInstance().build(RouterPath.HOME)
                    .withFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    .navigation(this);
        }
    }


}
