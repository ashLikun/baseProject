package com.ashlikun.baseproject.libcore;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.view.Gravity;

import com.ashlikun.appcrash.config.CaocConfig;
import com.ashlikun.baseproject.libcore.libarouter.RouterManage;
import com.ashlikun.baseproject.libcore.utils.CacheUtils;
import com.ashlikun.baseproject.libcore.utils.http.HttpManager;
import com.ashlikun.glideutils.GlideUtils;
import com.ashlikun.loadswitch.LoadSwitch;
import com.ashlikun.orm.LiteOrmUtil;
import com.ashlikun.utils.AppUtils;
import com.ashlikun.utils.ui.ActivityManager;
import com.ashlikun.utils.ui.SuperToast;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/30 14:45
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：Application公共的初始化
 */

public class BaseApplication extends MultiDexApplication {
    /**
     * 项目中全部module的app集合
     */
    private List<IApplication> applications = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        initLib();
        CacheUtils.init(getResources().getString(R.string.app_name_letter));
        //布局切换管理器
        LoadSwitch.BASE_EMPTY_LAYOUT_ID = R.layout.base_load_empty;
        LoadSwitch.BASE_RETRY_LAYOUT_ID = R.layout.base_load_retry;
        LoadSwitch.BASE_LOADING_LAYOUT_ID = R.layout.base_load_loading;
        //activity创建管理器
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        for (IApplication a : applications) {
            a.onCreate();
        }
        RouterManage.init(AppUtils.getApp(), AppUtils.isDebug());
    }

    /**
     * 每次新建一个module时候都要在这里添加
     *
     * @param application
     */
    public void addApplication(IApplication application) {
        if (!applications.contains(application)) {
            applications.add(application);
        }
    }

    private void initLib() {
        //内存溢出检测
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
        //app工具
        AppUtils.init(this);
        AppUtils.setDebug(BuildConfig.DEBUG);
        //异常捕获
        CaocConfig.Builder.create()
                .apply();
        //数据库
        LiteOrmUtil.init(this);
        LiteOrmUtil.setVersionCode(BuildConfig.VERSION_CODE);
        LiteOrmUtil.setIsDebug(BuildConfig.DEBUG);
        //glide图片库
        GlideUtils.setBaseUrl(HttpManager.BASE_URL);
        GlideUtils.setDebug(BuildConfig.DEBUG);
        //toast库
        SuperToast.setGravity(Gravity.CENTER);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (IApplication a : applications) {
            a.onTerminate();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (IApplication a : applications) {
            a.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (IApplication a : applications) {
            a.onTrimMemory(level);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        for (IApplication a : applications) {
            a.onConfigurationChanged(newConfig);
        }
    }

    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            ActivityManager.getInstance().pushActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityManager.getInstance().exitActivity(activity);
        }
    };


}
