package com.ashlikun.baseproject.module.login.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.module.login.R
import com.ashlikun.baseproject.module.login.iview.IBLoginView
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.baseproject.module.login.presenter.AmendPasswordPresenter
import com.ashlikun.core.activity.BaseMvpActivity
import com.ashlikun.core.factory.Presenter
import com.ashlikun.utils.ui.EditHelper


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/11 15:25
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：忘记密码
 */
@Route(path = RouterPath.AMEND_PASSWORD)
@Presenter(AmendPasswordPresenter::class)
class AmendPasswordActivity : BaseMvpActivity<AmendPasswordPresenter>(), IBLoginView.IAmendPasswordView {
    var editHelper = EditHelper(this)

    override fun getLayoutId(): Int {
        return R.layout.login_activity_amend_password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

    }

    override fun initView() {
        toolbar!!.setTitle("忘记密码")
        toolbar!!.setBack(this)
        //        editHelper.addEditHelperData(
        //                new EditHelper.EditHelperData(dataBind.phoneTil, Validators.REGEX_PHONE_NUMBER, "请正确输入11位手机号"));
        //        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.codeTil,
        //                "[0-9]{6,6}",
        //                "请正确输入验证码"));
        //        editHelper.addEditHelperData(
        //                new EditHelper.EditHelperData(dataBind.passwordTil1, Validators.getLengthSRegex(6, dataBind.passwordTil1.getCounterMaxLength()), "密码6-20位"));
        //        editHelper.addEditHelperData(
        //                new EditHelper.EditHelperData(dataBind.passwordTil2, Validators.getLengthSRegex(6, dataBind.passwordTil2.getCounterMaxLength()), "密码6-20位"));
    }


    override fun parseIntent(intent: Intent?) {

    }



    override fun receiverUserData(userData: UserData) {
        userData.save()
    }

    override fun clearData() {

    }


    fun onClick(view: View) {
        if (editHelper.check()) {
            presenter.updateUserPwd()
        }
    }

//    fun onSendCodeClick(view: View) {
//        if (editHelper.getEditHelperData(0).check(this)) {
//            presenter.sendMsg()
//        }
//    }


}
