package com.ashlikun.libcore;

import android.app.Activity;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.view.Gravity;

import com.ashlikun.appcrash.config.CaocConfig;
import com.ashlikun.glideutils.GlideUtils;
import com.ashlikun.libarouter.RouterManage;
import com.ashlikun.libcore.utils.AppUtils;
import com.ashlikun.libcore.utils.CacheUtils;
import com.ashlikun.libcore.utils.http.HttpManager;
import com.ashlikun.loadswitch.LoadSwitch;
import com.ashlikun.orm.LiteOrmUtil;
import com.ashlikun.utils.Utils;
import com.ashlikun.utils.ui.ActivityManager;
import com.ashlikun.utils.ui.SuperToast;

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initLib();
        AppUtils.init(this);
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
        LiteOrmUtil.init(this);
        LiteOrmUtil.setVersionCode(BuildConfig.VERSION_CODE);
        LiteOrmUtil.setIsDebug(BuildConfig.DEBUG);

        GlideUtils.setBaseUrl(HttpManager.BASE_URL);
        GlideUtils.setDebug(BuildConfig.DEBUG);

        Utils.init(this);
        Utils.setDebug(BuildConfig.DEBUG);
        SuperToast.setGravity(Gravity.CENTER);
        RouterManage.init(this, BuildConfig.DEBUG);
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
