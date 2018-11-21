package com.ashlikun.baseproject.module.login.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.module.login.R
import com.ashlikun.baseproject.module.login.iview.IBLoginView
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.baseproject.module.login.presenter.UpDataPasswordPresenter
import com.ashlikun.core.activity.BaseMvpActivity
import com.ashlikun.core.factory.Presenter
import com.ashlikun.utils.ui.EditHelper


/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/11 15:25
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：修改密码
 */
@Route(path = RouterPath.UPDATA_PASSWORD)
@Presenter(UpDataPasswordPresenter::class)
class UpdataPasswordActivity : BaseMvpActivity<UpDataPasswordPresenter>(), IBLoginView.IUpDataPasswordView {
    var editHelper = EditHelper(this)

    override fun getLayoutId(): Int {
        return R.layout.login_activity_updata_password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

    }

    override fun initView() {
        toolbar!!.setTitle("修改密码")
        toolbar!!.setBack(this)
        //        editHelper.addEditHelperData(
        //                new EditHelper.EditHelperData(dataBind.oldPasswordTil, Validators.getLengthSRegex(6, dataBind.oldPasswordTil.getCounterMaxLength()), "密码6-20位"));
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
}
