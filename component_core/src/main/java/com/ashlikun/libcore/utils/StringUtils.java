package com.ashlikun.libcore.utils;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/18　13:19
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
public class StringUtils {
    //进行中和已完结  金额隐藏
    public static String getMoney(boolean isShowMoney, int money) {
        if (!isShowMoney) {
            return "¥***";
        }
        return "¥" + money;
    }
}
