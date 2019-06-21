package com.ashlikun.baseproject.module.login.view.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.libarouter.interceptor.LoginInterceptor
import com.ashlikun.baseproject.module.login.R
import com.ashlikun.baseproject.module.login.iview.IBLoginView
import com.ashlikun.baseproject.module.login.mode.javabean.UserData
import com.ashlikun.baseproject.module.login.presenter.LoginPresenter
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.core.activity.BaseMvpActivity
import com.ashlikun.core.factory.Presenter


/**
 * 登录页面
 * *
 */
@Route(path = RouterPath.LOGIN)
@Presenter(LoginPresenter::class)
class LoginActivity : BaseMvpActivity<LoginPresenter>(), IBLoginView.IloginView {

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
    }

    override fun checkData(): Boolean {
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun clearData() {

    }

//    fun onWangjiClick(view: View) {
//        ARouter.getInstance().build(RouterPath.AMEND_PASSWORD)
//                .navigation(this)
//    }

    override fun login(data: UserData) {
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
