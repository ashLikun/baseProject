package com.ashlikun.baseproject.utils;

/**
 * 作者　　: 李坤
 * 创建时间:2017/10/8 0008　18:56
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class AppUtils {
    //进行中和已完结  金额隐藏
    public static String getMoney(boolean isShowMoney, int money) {
        if (!isShowMoney) {
            return "¥***";
        }
        return "¥" + money;
    }
}
