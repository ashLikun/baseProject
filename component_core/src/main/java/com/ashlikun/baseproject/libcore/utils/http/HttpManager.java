package com.ashlikun.baseproject.libcore.utils.http;

import android.app.Activity;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ashlikun.baseproject.libcore.libarouter.RouterManage;
import com.ashlikun.baseproject.libcore.utils.CacheUtils;
import com.ashlikun.okhttputils.http.OkHttpUtils;
import com.ashlikun.okhttputils.http.response.HttpCode;
import com.ashlikun.okhttputils.http.response.HttpResponse;
import com.ashlikun.utils.ui.ActivityManager;
import com.ashlikun.utils.ui.MainHandle;
import com.ashlikun.utils.ui.SuperToast;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * 作者　　: 李坤
 * 创建时间: 16:19 Administrator
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：项目的http管理
 */

public class HttpManager {
    public static final String BASE_URL = "http://121.43.181.169";
    public static final String BASE_PATH = "/app/";
    private volatile static HttpManager INSTANCE = null;

    //获取单例
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (OkHttpUtils.class) {
                if (INSTANCE == null) {
                    OkHttpUtils.init(getOkHttpClientBuilder().build());
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }


    private static OkHttpClient.Builder getOkHttpClientBuilder() {
        //1：手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true);//链接失败重新链接
        builder.connectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);//链接超时
        builder.readTimeout(OkHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);//读取超时
        builder.writeTimeout(OkHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);//写入超时
        //设置缓存目录
        File cacheDirectory = new File(CacheUtils.appSDCachePath, "HttpCache");
        final Cache cache = new Cache(cacheDirectory, 50 * 1024 * 1024);//50M缓存
        builder.cache(cache);//设置缓存
        //公共拦截器
        builder.addInterceptor(new MarvelSigningInterceptor());
        return builder;
    }


    //处理成功后的数据code
    public static <T> boolean handelResult(T responseBody) {
        HttpResponse response = null;
        if (responseBody instanceof HttpResponse) {
            response = (HttpResponse) responseBody;
        } else if (responseBody instanceof String) {
            response = new HttpResponse() {
                @Override
                public Gson parseGson() {
                    return null;
                }
            };
            response.setOnGsonErrorData((String) responseBody);
        }
        if (response != null) {
            if (response.isSucceed()) {
                return true;
            } else if (response.getCode() == HttpCode.TOKEN_ERROR) {
                if(RouterManage.haveLogin()) {
                    RouterManage.getLogin().exit();
                }
                final Activity activity = ActivityManager.getForegroundActivity();
                if (activity != null && !activity.isFinishing()) {
                    if (Looper.getMainLooper() != Looper.myLooper()) {
                        MainHandle.get().post(new Runnable() {
                            @Override
                            public void run() {
                                showTokenErrorDialog(activity);
                            }
                        });
                    } else {
                        showTokenErrorDialog(activity);
                    }
                } else {
                    SuperToast.get("你的账号登陆异常，请重新登陆").error();
                }
                return false;
            } else if (response.getCode() == HttpCode.SIGN_ERROR) {
                SuperToast.get("签名错误").error();
                return false;
            }
            return true;
        }
        return true;
    }

    private static void showTokenErrorDialog(final Activity activity) {

        new MaterialDialog.Builder(ActivityManager.getForegroundActivity())
                .cancelable(false)
                .content("你的账号登陆异常，请重新登陆")
                .positiveText("知道了")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(RouterManage.haveLogin()) {
                            RouterManage.getLogin().exitLogin(activity);
                        }
                    }
                })
                .build().show();

    }


}

