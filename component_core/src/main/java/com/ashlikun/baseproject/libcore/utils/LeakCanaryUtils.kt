package com.ashlikun.baseproject.libcore.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.squareup.leakcanary.AndroidExcludedRefs
import com.squareup.leakcanary.DisplayLeakService
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.LeakCanary.refWatcher
import com.squareup.leakcanary.RefWatcher
import com.squareup.leakcanary.internal.ActivityLifecycleCallbacksAdapter
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
    var isDebug = true
    @JvmStatic
    fun addFiltration(cls: Class<out Any>) {
        if (!isDebug) {
            return
        }
        if (filtration == null) {
            filtration = CopyOnWriteArraySet<Any>();
        }
        filtration?.add(cls)
    }

    @JvmStatic
    fun removeFiltration(cls: Class<Any>) {
        if (!isDebug) {
            return
        }
        if (filtration == null) {
            return
        }
        filtration?.remove(cls)
    }

    @JvmStatic
    fun init(application: Application, isDebug: Boolean) {
        LeakCanaryUtils.isDebug = isDebug
        if (!LeakCanaryUtils.isDebug) {
            return
        }
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            var refWatcher = refWatcher(application).listenerServiceClass(DisplayLeakService::class.java)
                    .watchFragments(false)
                    .excludedRefs(AndroidExcludedRefs.createAppDefaults().build())
                    .buildAndInstall()
            FragmentHelper(application, refWatcher)
        }
    }


    class FragmentHelper(var application: Application, refWatcher: RefWatcher) {

        private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacksAdapter() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                val supportFragmentManager = (activity as FragmentActivity).supportFragmentManager
                supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
            }
        }
        private val fragmentLifecycleCallbacks = object : android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentViewDestroyed(fm: android.support.v4.app.FragmentManager, fragment: android.support.v4.app.Fragment) {
                if (filtration?.contains(fragment::class.java) == true) {
                    return
                }
                val view = fragment.view
                if (view != null) {
                    refWatcher.watch(view)
                }
            }

            override fun onFragmentDestroyed(fm: android.support.v4.app.FragmentManager, fragment: android.support.v4.app.Fragment) {
                if (filtration?.contains(fragment::class.java) == true) {
                    return
                }
                refWatcher.watch(fragment)
            }
        }

        init {
            application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        }
    }
}

