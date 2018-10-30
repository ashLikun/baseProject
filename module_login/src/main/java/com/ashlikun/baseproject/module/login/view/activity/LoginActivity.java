package com.ashlikun.baseproject.module.login.view.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.baseproject.libcore.constant.RouterPath;
import com.ashlikun.baseproject.libcore.libarouter.interceptor.LoginInterceptor;
import com.ashlikun.baseproject.module.login.R;
import com.ashlikun.baseproject.module.login.iview.IBLoginView;
import com.ashlikun.baseproject.module.login.mode.javabean.UserData;
import com.ashlikun.baseproject.module.login.presenter.LoginPresenter;
import com.ashlikun.common.utils.jump.RouterJump;
import com.ashlikun.core.activity.BaseMvpActivity;
import com.ashlikun.core.factory.Presenter;


/**
 * 登录页面
 * *
 */
@Route(path = RouterPath.LOGIN)
@Presenter(LoginPresenter.class)
public class LoginActivity extends BaseMvpActivity<LoginPresenter>
        implements IBLoginView.IloginView {

    @Override
    public int getLayoutId() {
        return R.layout.login_activity_login;
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
        return false;
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
            //登录拦截器是否有任务挂起
            if (LoginInterceptor.isHaveRun()) {
                LoginInterceptor.run();
            } else {
                //没有就跳转首页
                RouterJump.startHome();
            }
            finish();
        }
    }


}
