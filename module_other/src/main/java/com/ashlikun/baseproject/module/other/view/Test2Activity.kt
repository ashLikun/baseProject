package com.ashlikun.baseproject.module.other.view

import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.utils.ui.ActivityManager
import kotlinx.android.synthetic.main.other_activity_test.*


/**
 * Created by yang on 2016/9/3.
 */
class Test2Activity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.other_activity_test
    }

    override fun initView() {
        toolbar.setTitle("我是测试2")
        ceshiButton.setOnClickListener {


        }

        ceshiButton2.setOnClickListener {
            RouterJump.startTest()
            //销毁之前的
            ActivityManager.getInstance().getTagActivitys<TestActivity>(TestActivity::class.java)
                    .forEach {
                        it.finish()
                    }
//            //跳转测试
//            taskLaunch(delayTime = 10000) { RouterJump.startTest() }
        }
    }


}

