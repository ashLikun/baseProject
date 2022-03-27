package com.ashlikun.baseproject.module.main.view.activity

import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Process
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import androidx.core.app.AppOpsManagerCompat
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.ashlikun.baseproject.libcore.constant.EventBusKey
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.baseproject.libcore.constant.RouterPath
import com.ashlikun.baseproject.libcore.libarouter.RouterManage
import com.ashlikun.baseproject.module.main.R
import com.ashlikun.baseproject.module.main.databinding.MainActivityHomeBinding
import com.ashlikun.bottomnavigation.AHBottomNavigation
import com.ashlikun.bottomnavigation.AHBottomNavigationItem
import com.ashlikun.core.activity.BaseActivity
import com.ashlikun.core.mvvm.launch
import com.ashlikun.livedatabus.EventBus
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.LogUtils
import com.ashlikun.utils.other.coroutines.taskLaunchMain
import com.ashlikun.utils.ui.ActivityManager
import com.ashlikun.utils.ui.extend.resColor
import com.ashlikun.utils.ui.modal.SuperToast
import com.ashlikun.utils.ui.modal.ToastUtils
import com.ashlikun.xviewpager.FragmentUtils
import com.ashlikun.xviewpager.fragment.FragmentPagerAdapter
import com.ashlikun.xviewpager.fragment.FragmentPagerItem
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


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
    override val binding by lazy {
        MainActivityHomeBinding.inflate(layoutInflater)
    }
    override val statusBarColor = R.color.white.resColor

    private var exitTime: Long = 0
    var index = 0
    var cachePosition = -1
    val adapter: FragmentPagerAdapter by lazy {
        FragmentPagerAdapter.Builder.create(supportFragmentManager)
                .addItem(FragmentPagerItem.get(RouterPath.FRAGMENT_HOME))
                .addItem(FragmentPagerItem.get(RouterPath.FRAGMENT_HOME))
                .addItem(FragmentPagerItem.get(RouterPath.FRAGMENT_HOME))
                .setCache(true)
                .build()
    }

    override fun parseIntent(intent: Intent) {
        super.parseIntent(intent)
        launch {  }
        taskLaunchMain {  }
        MainScope().launch {

        }
        flow<String> {  }
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
        binding.bottomNavigationBar.currentItem = postion
    }


    override fun initView() {
        binding.run {
            bottomNavigationBar.accentColor = R.color.colorPrimary.resColor
            bottomNavigationBar.addItem(
                    AHBottomNavigationItem.Builder(
                            R.string.main_bottom_1,
                            R.mipmap.app_logo, R.mipmap.app_logo
                    ).builder()
            )
            bottomNavigationBar.addItem(
                    AHBottomNavigationItem.Builder(
                            R.string.main_bottom_2,
                            R.mipmap.app_logo, R.mipmap.app_logo
                    ).builder()
            )
            bottomNavigationBar.addItem(
                    AHBottomNavigationItem.Builder(
                            R.string.main_bottom_3,
                            R.mipmap.app_logo, R.mipmap.app_logo
                    ).builder()
            )
            bottomNavigationBar.defaultBackgroundColor = R.color.white.resColor
            bottomNavigationBar.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

            bottomNavigationBar.addOnTabSelectedListener(this@HomeActivity)
            //防止重复添加
            FragmentUtils.removeAll(supportFragmentManager)
            viewPager.offscreenPageLimit = adapter.count
            viewPager.setAdapter(adapter)
            //登录之后可以左右滑动
            viewPager.setCanSlide(RouterManage.login()?.isLogin() ?: false)
            //监听登录成功的通知
            EventBus.get(EventBusKey.LOGIN).registerLifecycle(this@HomeActivity, Observer<Any> {
                //登录之后可以左右滑动
                viewPager.setCanSlide(RouterManage.login()?.isLogin() ?: false)
            })
            bottomNavigationBar.setupWithViewPager(viewPager, false)
        }
        val mode = AppOpsManagerCompat.noteOp(
                this,
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                this.packageName
        )
        val granted = mode == AppOpsManagerCompat.MODE_ALLOWED
        if (!granted) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
        launch(delayTime = 1000) {
            while (true) {

                val m = AppUtils.app.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
                if (m != null) {
                    val now = System.currentTimeMillis()
                    //获取60秒之内的应用数据
                    val stats =
                            m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 6000 * 1000, now)
                    LogUtils.e("Running app number in last 60 seconds : " + stats!!.size)
                    //取得最近运行的一个app，即当前运行的app
                    if (stats.isEmpty()) {
                        var j = 0
                        for (i in stats.indices) {
                            if (stats[i].lastTimeUsed > stats[j].lastTimeUsed) {
                                j = i
                            }
                            var packageInfo = AppUtils.app.packageManager.getPackageInfo(
                                    stats[j].packageName,
                                    0
                            )
                            val appName =
                                    packageInfo.applicationInfo.loadLabel(AppUtils.app.packageManager)
                                            .toString()
                            LogUtils.e("top running app is : $appName")
                        }
                    }
                }

//                // Get a list of running apps
//                // Get a list of running apps
//                val processes = AndroidProcesses.getRunningAppProcesses()
//
//                for (process in processes) {
//                    // Get some information about the process
//                    val processName = process.name
//                    val stat = process.stat()
//                    val pid = stat.pid
//                    val parentProcessId = stat.ppid()
//                    val startTime = stat.stime()
//                    val policy = stat.policy()
//                    val state = stat.state()
//                    val statm = process.statm()
//                    val totalSizeOfProcess = statm.size
//                    val residentSetSize = statm.residentSetSize
//                    val packageInfo = process.getPackageInfo(AppUtils.app(), 0)
//                    val appName = packageInfo.applicationInfo.loadLabel(AppUtils.app().packageManager).toString()
//                    LogUtils.e("appName${appName}${processName}")
//                }
                delay(1000)
            }
        }
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
            ActivityManager.get().exitAllActivity()
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
//        if (position == 2 || position == 3) {
//            if (!RouterManage.getLogin().isLogin()) {
//                RouterJump.startLogin()
//                cachePosition = position
//                return false
//            }
//        }
//        if (position == 0) {
//            statusBar.translucentStatusBar()
//            val fragment = adapter?.getCurrentFragment<Fragment>()
//            if (fragment is OnDispatcherMessage) {
//                //首页自己更改状态栏
//                (fragment as OnDispatcherMessage).onDispatcherMessage(1, null)
//            }
//        } else {
//            statusBar.setStatusBarColor(statusBarColor)
//        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onResume() {
        super.onResume()

    }
}