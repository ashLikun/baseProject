package com.ashlikun.baseproject.module.other.view

import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.common.utils.jump.PullJumpManage
import com.ashlikun.baseproject.common.utils.jump.RouterJump
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.router.RouterManage
import com.ashlikun.baseproject.module.other.R
import com.ashlikun.baseproject.module.other.databinding.OtherActivityWelcomBinding
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.mvvm.launch
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.store.StoreUtils
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.extend.finishNoAnim
import com.ashlikun.utils.ui.extend.resColor
import com.ashlikun.utils.ui.status.enableEdgeToEdgeX

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:22
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：项目入口类
 */

@Route(path = RouterPath.WELCOME)
class WelcomeActivity : BaseActivity() {
    override val binding by lazy {
        OtherActivityWelcomBinding.inflate(layoutInflater)
    }
    private val time = 200L
    override fun setSafeArea() {
        enableEdgeToEdgeX(isSetView = false)
//        window.hindNavigationBar()
//        window.hideStatusBar()
//        getRootView().setSfeAreaPadding(top = false, bottom = true)
    }

    override fun initView() {
        launch(delayTime = time) {
            when {
                checkIsFirst() -> RouterJump.startLaunch()
                else -> RouterJump.startHome(0)
            }
            finishNoAnim(delay = 200)
        }
    }


    override fun parseIntent(intent: Intent) {
        super.parseIntent(intent)
        PullJumpManage.save(intent)
        //如果首页已经启动了，那么久不用启动首页
        if (RouterManage.home()?.isHomeStart() == true) {
            //这里是为了后续跳转使用的topactivity用的是以前的Activity栈，防止返回后回到微信或者浏览器
            ActivityManager.get().exitActivity(this)
            //处理拉起App数据
            PullJumpManage.handleCachePush(this)
        }
    }

    private fun checkIsFirst(): Boolean {
        if (StoreUtils.getInt("VersionCode", name = "Run") != AppUtils.versionCode) {
//            // 第一次
//            StoreUtils.setKeyAndValue(this, "Run",
//                    "VersionCode", BuildConfig.VERSION_CODE)
//            val intent = Intent(this, LeadActivity::class.java)
//            startActivity(intent)
//            return false
//            finish()
        } else {
        }
        return false
    }
}
