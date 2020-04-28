package com.ashlikun.baseproject.module.login.view.activity

import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.http.interceptor.LoginInterceptor
import com.ashlikun.baseproject.module.login.R
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.baseproject.module.login.viewmodel.LoginViewModel
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.core.mvvm.BaseMvvmActivity
import com.ashlikun.core.mvvm.IViewModel


/**
 * 登录页面
 * *
 */
@Route(path = RouterPath.LOGIN)
@IViewModel(LoginViewModel::class)
class LoginActivity : BaseMvvmActivity<LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.login_activity_login
    }

    override fun initView() {
        toolbar!!.setTitle("登录")
        toolbar!!.setBack(this)
        //        editHelper.addEditHelperData(
        //                new EditHelper.EditHelperData(dataBind.phoneTil, Validators.REGEX_PHONE_NUMBER, "请正确输入11位手机号"));
        //        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.passwordTil,
        //                Validators.getLengthSRegex(6, dataBind.passwordTil.getCounterMaxLength()), "密码6-20位"));
        //        dataBind.setPresenter(presenter);
        viewModel.userData.observe(this, Observer {
            login(it)
        })
    }


    fun login(data: UserData) {
        if (UserData.isLogin()) {
            //登录拦截器是否有任务挂起
            if (LoginInterceptor.isHaveRun()) {
                LoginInterceptor.run()
            } else {
                //没有就跳转首页
                RouterJump.startHome()
            }
            finish()
        }
    }


}
