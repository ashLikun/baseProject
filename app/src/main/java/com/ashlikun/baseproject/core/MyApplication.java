package com.ashlikun.baseproject.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.appcrash.config.CaocConfig;
import com.ashlikun.baseproject.BuildConfig;
import com.ashlikun.baseproject.R;
import com.ashlikun.glideutils.GlideUtils;
import com.ashlikun.libcore.utils.CacheUtils;
import com.ashlikun.libcore.utils.http.HttpManager;
import com.ashlikun.loadswitch.LoadSwitch;
import com.ashlikun.orm.LiteOrmUtil;
import com.ashlikun.utils.Utils;
import com.ashlikun.utils.ui.ActivityManager;
import com.ashlikun.utils.ui.SuperToast;

public class MyApplication extends MultiDexApplication {
    public static Application myApp;


    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        initLib();
        CacheUtils.init(getResources().getString(R.string.app_name_letter));

        //布局切换管理器
        LoadSwitch.BASE_EMPTY_LAYOUT_ID = R.layout.base_load_empty;
        LoadSwitch.BASE_RETRY_LAYOUT_ID = R.layout.base_load_retry;
        LoadSwitch.BASE_LOADING_LAYOUT_ID = R.layout.base_load_loading;
        //activity创建管理器
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private void initLib() {
        CaocConfig.Builder.create()
                .apply();
        LiteOrmUtil.init(myApp);
        LiteOrmUtil.setVersionCode(BuildConfig.VERSION_CODE);
        LiteOrmUtil.setIsDebug(BuildConfig.DEBUG);

        GlideUtils.setBaseUrl(HttpManager.BASE_URL);
        GlideUtils.setDebug(BuildConfig.DEBUG);

        Utils.init(this);
        Utils.setDebug(BuildConfig.DEBUG);
        SuperToast.setGravity(Gravity.CENTER);
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(myApp); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
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
            initLib();
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            ActivityManager.getInstance().exitActivity(activity);
        }
    };


}
