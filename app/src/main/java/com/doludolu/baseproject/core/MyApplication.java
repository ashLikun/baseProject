package com.doludolu.baseproject.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ashlikun.appcrash.config.CaocConfig;
import com.ashlikun.loadswitch.LoadSwitchService;
import com.ashlikun.orm.LiteOrmUtil;
import com.ashlikun.utils.Utils;
import com.ashlikun.utils.ui.ActivityManager;
import com.ashlikun.utils.ui.SuperToast;
import com.doludolu.baseproject.BuildConfig;
import com.doludolu.baseproject.R;
import com.doludolu.baseproject.utils.http.HttpManager;
import com.ashlikun.glideutils.GlideUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.File;

public class MyApplication extends MultiDexApplication {
    public static Application myApp;
    //app缓存路径，内部
    public static String appCachePath;
    //app文件路径，内部
    public static String appFilePath;
    //appsd卡缓存路径
    public static String appSDCachePath;
    //appsd卡文件路径
    public static String appSDFilePath;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        initLib();
        String cacheStr = "/" + getResources().getString(R.string.app_name_letter) + "/cache";
        String fileStr = "/" + getResources().getString(R.string.app_name_letter) + "/file";
        appCachePath = getCacheDir().getPath() + cacheStr;
        appFilePath = getFilesDir().getPath() + fileStr;
        appSDCachePath = Environment.getExternalStorageDirectory().getPath() + cacheStr;
        appSDFilePath = Environment.getExternalStorageDirectory().getPath() + fileStr;

        File file = new File(appSDCachePath);
        if (file.exists() || file.mkdirs()) {
        }
        File file2 = new File(appSDFilePath);
        if (file2.exists() || file2.mkdirs()) {
        }
        //布局切换管理器
        LoadSwitchService.BASE_EMPTY_LAYOUT_ID = R.layout.base_load_empty;
        LoadSwitchService.BASE_RETRY_LAYOUT_ID = R.layout.base_load_retry;
        LoadSwitchService.BASE_LOADING_LAYOUT_ID = R.layout.base_load_loading;
        //activity创建管理器
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private void initLib() {
        CaocConfig.Builder.create()
                .apply();
        LiteOrmUtil.init(new LiteOrmUtil.OnNeedListener() {
            @Override
            public Application getApplication() {
                return myApp;
            }

            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }

            @Override
            public int getVersionCode() {
                return BuildConfig.VERSION_CODE;
            }
        });
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
            MobclickAgent.onResume(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            MobclickAgent.onPause(activity);
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
