package com.ashlikun.baseproject.module.other.view

import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import androidx.core.view.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.utils.http.HttpRequestParam
import com.ashlikun.baseproject.libcore.utils.http.HttpUiHandle
import com.ashlikun.baseproject.libcore.utils.http.syncExecute
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.mvvm.launch
import com.ashlikun.gson.GsonHelper
import com.ashlikun.okhttputils.http.response.HttpResponse
import com.ashlikun.utils.encryption.AESUtils
import com.ashlikun.utils.encryption.Md5Utils
import com.ashlikun.utils.encryption.RSAUtils
import com.ashlikun.utils.encryption.SHAUtil
import com.ashlikun.utils.other.DateUtils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.ui.ActivityManager
import kotlinx.android.synthetic.main.other_activity_test.*
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*


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

