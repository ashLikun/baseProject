package com.ashlikun.baseproject.module.login.view.activity

import android.content.Intent
import android.support.design.widget.TabLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.module.login.R
import com.ashlikun.baseproject.module.login.iview.IBLoginView
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.baseproject.module.login.presenter.RegisterPresenter
import com.ashlikun.core.activity.BaseMvpActivity
import com.ashlikun.core.factory.Presenter
import com.ashlikun.utils.ui.EditHelper


/**
 * 登录页面
 * *
 */
@Route(path = RouterPath.REGISTER)
@Presenter(RegisterPresenter::class)
class RegisterActivity : BaseMvpActivity<RegisterPresenter>(), IBLoginView.IRegisterView, TabLayout.OnTabSelectedListener {
    var editHelper = EditHelper(this)
    internal var isSkipHome = true

    override fun getLayoutId(): Int {
        return R.layout.login_activity_register
    }


    override fun parseIntent(intent: Intent?) {
        intent?.run {
            isSkipHome = getBooleanExtra("isSkipHome", true)
        }
    }

    override fun initView() {
        toolbar!!.setBack(this)
        //        editHelper.addEditHelperData(
        //                new EditHelper.EditHelperData(dataBind.phoneTil,
        //                        Validators.REGEX_PHONE_NUMBER,
        //                        "请正确输入11位手机号"));
        //        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.codeTil,
        //                "[0-9]{6,6}",
        //                "请正确输入验证码"));
        //        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.passwordTil,
        //                Validators.getLengthSRegex(6, dataBind.passwordTil.getCounterMaxLength()),
        //                "密码6-20位"));
        //        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.passwordTil2,
        //                Validators.getLengthSRegex(6, dataBind.passwordTil.getCounterMaxLength()),
        //                "密码6-20位"));
        //
        //        dataBind.setPresenter(presenter);
        //        dataBind.tabLayout.addOnTabSelectedListener(this);

    }

    override fun receiverUserData(userData: UserData) {
        finish()
    }

    override fun checkData(): Boolean {
        return editHelper.check()
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        presenter.registerType = tab.tag as Int
    }

    override fun onTabUnselected(tab: TabLayout.Tab) {

    }

    override fun onTabReselected(tab: TabLayout.Tab) {

    }


//    fun onSendCodeClick(view: View) {
//        if (editHelper.getEditHelperData(0).check(this)) {
//            presenter.sendMsg()
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun clearData() {

    }

}
