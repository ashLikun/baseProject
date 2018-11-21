package com.ashlikun.baseproject.module.main.view.activity

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.module.main.R
import com.ashlikun.bottomnavigation.AHBottomNavigation
import com.ashlikun.bottomnavigation.AHBottomNavigationItem
import com.ashlikun.common.EvenBusKey
import com.ashlikun.common.utils.jump.RouterJump
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.listener.OnDispatcherMessage
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.ResUtils
import com.ashlikun.utils.ui.SuperToast
import com.ashlikun.utils.ui.ToastUtils
import com.ashlikun.xviewpager.fragment.FragmentPagerAdapter
import com.ashlikun.xviewpager.fragment.FragmentPagerItem
import kotlinx.android.synthetic.main.main_activity_home.*


/**
 * 作者　　: 李坤
 * 创建时间:2016/9/19　17:04
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：
 */
@Route(path = RouterPath.HOME)
class HomeActivity : BaseActivity(), AHBottomNavigation.OnTabSelectedListener {
    private var exitTime: Long = 0
    var index = 0
    var cachePosition = -1
    var adapter: FragmentPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun parseIntent(intent: Intent?) {
        intent?.run {
            index = getIntExtra(RouterKey.FLAG_INDEX, -1)
        }
    }


    override fun initData() {
        super.initData()
        if (index != -1) {
            setCurrentItem(index)
        } else if (cachePosition != -1) {
            setCurrentItem(cachePosition)
            cachePosition = -1
        }
    }

    fun setCurrentItem(postion: Int) {
        bottomNavigationBar.currentItem = postion
    }

    override fun getLayoutId(): Int {
        return R.layout.main_activity_home
    }

    override fun getStatusBarColor(): Int {
        return ResUtils.getColor(R.color.white)
    }

    override fun initView() {
        bottomNavigationBar.accentColor = ResUtils.getColor(R.color.colorPrimary)
        bottomNavigationBar.addItem(AHBottomNavigationItem.Builder(R.string.main_bottom_1,
                R.mipmap.app_logo, R.mipmap.app_logo).builder())
        bottomNavigationBar.addItem(AHBottomNavigationItem.Builder(R.string.main_bottom_2,
                R.mipmap.app_logo, R.mipmap.app_logo).builder())
        bottomNavigationBar.addItem(AHBottomNavigationItem.Builder(R.string.main_bottom_3,
                R.mipmap.app_logo, R.mipmap.app_logo).builder())

        bottomNavigationBar.defaultBackgroundColor = ResUtils.getColor(R.color.white)
        bottomNavigationBar.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavigationBar.setupWithViewPager(viewPager)
        bottomNavigationBar.addOnTabSelectedListener(this)
        val fragments = supportFragmentManager.fragments
        if (fragments.size != 0) {
            val ft = supportFragmentManager.beginTransaction()
            for (i in fragments.indices) {
                ft.remove(fragments[i])
            }
            ft.commit()
        }
        adapter = FragmentPagerAdapter.Builder.get(supportFragmentManager)
                .addItem(FragmentPagerItem.get(RouterPath.FRAGMENT_HOME))
                .addItem(FragmentPagerItem.get(RouterPath.FRAGMENT_HOME))
                .addItem(FragmentPagerItem.get(RouterPath.FRAGMENT_HOME))
                .build()
        viewPager.offscreenPageLimit = adapter?.count ?: 3
        viewPager.adapter = adapter
        //登录之后可以左右滑动
        viewPager.setCanSlide(RouterManage.getLogin().isLogin())
                .run { }
        //监听登录成功的通知
        EventBus.get(EvenBusKey.LOGIN).registerLifecycle(this, Observer<Any> {
            //登录之后可以左右滑动
            viewPager.setCanSlide(RouterManage.getLogin().isLogin())
        })
    }


    override fun onBackPressed() {
        isExitApplication(this)
    }


    fun isExitApplication(context: Context) {
        if (System.currentTimeMillis() - exitTime > 2000) {
            SuperToast.get("再按一次退出程序").info()
            exitTime = System.currentTimeMillis()
        } else {
            // 退出
            ActivityManager.getInstance().exitAllActivity()
            ToastUtils.getMyToast().cancel()
        }
    }

    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        return if (!wasSelected) {
            onTabChang(position)
        } else {
            true
        }
    }

    private fun onTabChang(position: Int): Boolean {
        //限制登录
        if (position == 2 || position == 3) {
            if (!RouterManage.getLogin().isLogin()) {
                RouterJump.startLogin()
                cachePosition = position
                return false
            }
        }
        if (position == 0) {
            statusBar.translucentStatusBar()
            val fragment = adapter?.getCurrentFragment<Fragment>()
            if (fragment is OnDispatcherMessage) {
                //首页自己更改状态栏
                (fragment as OnDispatcherMessage).onDispatcherMessage(1, null)
            }
        } else {
            statusBar.setStatusBarColor(statusBarColor)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onResume() {
        super.onResume()

    }
}