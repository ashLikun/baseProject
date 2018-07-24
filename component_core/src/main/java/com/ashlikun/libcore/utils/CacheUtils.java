package com.ashlikun.libcore.utils;

import android.os.Environment;

import java.io.File;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/18　10:50
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class CacheUtils {
    //app缓存路径，内部
    public static String appCachePath;
    //app文件路径，内部
    public static String appFilePath;
    //appsd卡缓存路径
    public static String appSDCachePath;
    //appsd卡文件路径
    public static String appSDFilePath;

    /**
     * @param rootName 跟目录名称，一般为app名称
     */
    public static void init(String rootName) {
        String cacheStr = "/" + rootName + "/cache";
        String fileStr = "/" + rootName + "/file";
        appCachePath = AppUtils.getApp().getCacheDir().getPath() + cacheStr;
        appFilePath = AppUtils.getApp().getFilesDir().getPath() + fileStr;
        appSDCachePath = Environment.getExternalStorageDirectory().getPath() + cacheStr;
        appSDFilePath = Environment.getExternalStorageDirectory().getPath() + fileStr;

        File file = new File(appSDCachePath);
        if (file.exists() || file.mkdirs()) {
        }
        File file2 = new File(appSDFilePath);
        if (file2.exists() || file2.mkdirs()) {
        }
    }
}
