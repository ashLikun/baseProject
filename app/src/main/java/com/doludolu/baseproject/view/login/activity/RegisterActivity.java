package com.doludolu.baseproject.view.login.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.utils.other.Validators;
import com.ashlikun.utils.ui.EditHelper;
import com.doludolu.baseproject.R;
import com.doludolu.baseproject.code.activity.BaseMvpActivity;
import com.doludolu.baseproject.databinding.ActivityRegisterBinding;
import com.doludolu.baseproject.mode.javabean.base.UserData;
import com.doludolu.baseproject.presenter.login.RegisterPresenter;
import com.doludolu.baseproject.view.login.iview.IBLoginView;

import static com.doludolu.baseproject.code.ARouterFlag.REGISTER;


/**
 * 登录页面
 * *
 */
@Route(path = REGISTER)
public class RegisterActivity extends BaseMvpActivity<RegisterPresenter, ActivityRegisterBinding>
        implements IBLoginView.IRegisterView, TabLayout.OnTabSelectedListener {
    public EditHelper editHelper = new EditHelper(this);
    boolean isSkipHome = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }


    @Override
    public void parseIntent(Intent intent) {
        isSkipHome = intent.getBooleanExtra("isSkipHome", true);
    }

    @Override
    public void initView() {
        dataBind.tabLayout.addTab(dataBind.tabLayout.newTab().setText("学生注册").setTag(1));
        dataBind.tabLayout.addTab(dataBind.tabLayout.newTab().setText("商家注册").setTag(2));
        toolbar.setBack(this);
        editHelper.addEditHelperData(
                new EditHelper.EditHelperData(dataBind.phoneTil,
                        Validators.REGEX_PHONE_NUMBER,
                        "请正确输入11位手机号"));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.codeTil,
                "[0-9]{6,6}",
                "请正确输入验证码"));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.passwordTil,
                Validators.getLengthSRegex(6, dataBind.passwordTil.getCounterMaxLength()),
                "密码6-20位"));
        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.passwordTil2,
                Validators.getLengthSRegex(6, dataBind.passwordTil.getCounterMaxLength()),
                "密码6-20位"));

        dataBind.setPresenter(presenter);
        dataBind.tabLayout.addOnTabSelectedListener(this);

    }

    @Override
    public void receiverUserData(UserData UserData) {
        finish();
    }

    @Override
    public boolean checkData() {
        return editHelper.check();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        presenter.registerType = (int) tab.getTag();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public void onSendCodeClick(View view) {
        if (editHelper.getEditHelperData(0).check(this)) {
            presenter.sendMsg();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public RegisterPresenter initPressenter() {
        return new RegisterPresenter();
    }


    @Override
    public void clearData() {

    }

}
