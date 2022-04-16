package com.ashlikun.baseproject.module.login.view.activity

import android.view.MotionEvent
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.http.interceptor.LoginInterceptor
import com.ashlikun.baseproject.module.login.databinding.LoginActivityLoginBinding
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.baseproject.module.login.viewmodel.LoginViewModel
import com.ashlikun.core.mvvm.BaseMvvmActivity
import com.ashlikun.core.mvvm.IViewModel


/**
 * @author　　: 李坤
 * 创建时间: 2020/10/21 11:33
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：登录页面
 */

@Route(path = RouterPath.LOGIN)
@IViewModel(LoginViewModel::class)
class LoginActivity : BaseMvvmActivity<LoginViewModel>() {
    override val binding by lazy {
        LoginActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }
    override fun initView() {

        toolbar!!.setTitle("登录")
        toolbar!!.setBack(this)

        //        editHelper.addEditHelperData(
        //                new EditHelper.EditHelperData(dataBind.phoneTil, Validators.REGEX_PHONE_NUMBER, "请正确输入11位手机号"));
        //        editHelper.addEditHelperData(new EditHelper.EditHelperData(dataBind.passwordTil,
        //                Validators.getLengthSRegex(6, dataBind.passwordTil.getCounterMaxLength()), "密码6-20位"));
        //        dataBind.setPresenter(presenter);
        viewModel.userData.observe(this) {
            login(it)
        }
        binding.textView.setOnClickListener {

        }
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
