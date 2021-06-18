package com.ashlikun.baseproject.module.other.view

import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.baseproject.module.other.databinding.OtherActivityOrFragmentWebviewBinding
import com.ashlikun.baseproject.module.other.databinding.OtherActivityTestBinding
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.utils.ui.ActivityManager


/**
 * Created by yang on 2016/9/3.
 */
class Test2Activity : BaseActivity() {
    val binding by lazy {
        OtherActivityTestBinding.inflate(layoutInflater)
    }


    override fun initView() {
        toolbar.setTitle("我是测试2")
        binding.ceshiButton.setOnClickListener {


        }

        binding.ceshiButton2.setOnClickListener {
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

