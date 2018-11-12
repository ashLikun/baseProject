package com.ashlikun.common.utils.jump;

import android.content.Context;
import android.content.Intent;

import com.ashlikun.common.mode.javabean.JpushJsonData;
import com.ashlikun.common.utils.jpush.JpushUtils;
import com.ashlikun.utils.other.StringUtils;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/8/27　9:40
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：拉起app跳转的管理器
 */
public class PullJumpManage {
    /**
     * 缓存的数据，一般首页处理
     */
    private static JpushJsonData cacheData;

    /**
     * 处理缓存的跳转数据,一般在首页处理
     *
     * @param context
     */
    public static void handleCachePush(Context context) {
        if (cacheData != null) {
            jump(context, cacheData);
        }
    }

    private static void jump(Context context, JpushJsonData data) {
        cacheData = null;
        //统一跳转逻辑
        JpushUtils.skip(context, data);
    }

    /**
     * 检查这个意图是否正确
     *
     * @param intent 启动时候的意图
     */
    public static boolean check(Intent intent) {
        if (intent != null && intent.getData() != null &&
                StringUtils.isEquals(intent.getData().getHost(), "com.yoohfit")) {
            return true;
        }
        return false;
    }

    public static void save(Intent intent) {
        if (!check(intent)) {
            return;
        }
        String json = intent.getData().getQueryParameter("json");
        JpushJsonData data = JpushJsonData.jsonParse(json);
        if (data != null) {
            //缓存这个数据
            cacheData = data;
        }
    }
}
