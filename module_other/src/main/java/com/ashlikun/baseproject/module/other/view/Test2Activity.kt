package com.ashlikun.baseproject.module.other.view

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.http.HttpRequestParam
import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.baseproject.libcore.utils.http.syncExecute
import com.ashlikun.baseproject.module.other.databinding.OtherActivityTestBinding
import com.ashlikun.baseproject.module.other.mode.ApiOther
import com.ashlikun.baseproject.module.other.utils.MaotaiUtils
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.mvvm.launch
import com.ashlikun.gson.GsonHelper
import com.ashlikun.utils.encryption.Md5Utils
import com.ashlikun.utils.encryption.SHAUtil
import com.ashlikun.utils.other.DateUtils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.logge
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.util.*


//@Route(path = RouterPath.TEST)
class Test2Activity : BaseActivity() {
    override val binding by lazy {
        OtherActivityTestBinding.inflate(layoutInflater)
    }


    override fun initView() {
        toolbar?.setTitle("我是测试")

        binding.ceshiButton.setOnClickListener {
            //跳转
            startActivity(Intent(this, Test2Activity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.e("Test onDestroy")
    }

    override fun finish() {
        super.finish()
        LogUtils.e("Test finish")
    }

    override fun onResume() {
        super.onResume()
        "onResume".logge()
    }

    override fun onPause() {
        super.onPause()
        "onPause".logge()
    }

    override fun onStop() {
        super.onStop()
        "onStop".logge()
    }
}

