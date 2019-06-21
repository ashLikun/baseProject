package com.ashlikun.baseproject.libcore.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ashlikun.utils.ActivityLifecycleCallbacksAdapter
import com.ashlikun.utils.AppUtils
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import java.util.concurrent.CopyOnWriteArraySet

/**
 * 作者　　: 李坤
 * 创建时间: 2019/4/17　16:44
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：对LeakCanary的初始化
 */
object LeakCanaryUtils {
    /**
     * 不用监听的类
     */
    var filtration: MutableSet<Any>? = null

    @JvmStatic
    fun addFiltration(cls: Class<out Any>) {
        if (!AppUtils.isDebug()) {
            return
        }
        if (filtration == null) {
            filtration = CopyOnWriteArraySet<Any>();
        }
        filtration?.add(cls)
    }

    @JvmStatic
    fun removeFiltration(cls: Class<Any>) {
        if (!AppUtils.isDebug()) {
            return
        }
        if (filtration == null) {
            return
        }
        filtration?.remove(cls)
    }

    @JvmStatic
    fun init(application: Application) {
        if (!AppUtils.isDebug()) {
            return
        }
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            Helper(application, LeakCanary.install(application))
        }
    }


    /**
     * 监听Fragment生命周期
     */
    class Helper(var application: Application, refWatcher: RefWatcher) {


        private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (activity is FragmentActivity) {
                    val supportFragmentManager = activity.supportFragmentManager
                    supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
                }
            }

            override fun onActivityDestroyed(activity: Activity) {
                super.onActivityDestroyed(activity)
                if (activity is FragmentActivity) {
                    val supportFragmentManager = activity.supportFragmentManager
                    supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
                }
            }
        }
        private val fragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentViewDestroyed(fm: FragmentManager, fragment: Fragment) {
                if (filtration?.contains(fragment::class.java) == true) {
                    return
                }
                val view = fragment.view
                if (view != null) {
                    refWatcher.watch(view)
                }
            }

            override fun onFragmentDestroyed(fm: FragmentManager, fragment: Fragment) {
                if (filtration?.contains(fragment::class.java) == true) {
                    return
                }
                refWatcher.watch(fragment)
            }
        }

        init {
            application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        }

    }
}
