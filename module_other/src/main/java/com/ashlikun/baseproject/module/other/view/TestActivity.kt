package com.ashlikun.baseproject.module.other.view

import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.common.utils.jump.RouterJump
import com.ashlikun.core.activity.BaseActivity
import kotlinx.android.synthetic.main.other_activity_test.*


/**
 * Created by yang on 2016/9/3.
 */
@Route(path = RouterPath.TEST)
class TestActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.other_activity_test
    }

    override fun initView() {
        ceshiButton.setOnClickListener {
            RouterJump.startHome(2)
        }

    }

}
