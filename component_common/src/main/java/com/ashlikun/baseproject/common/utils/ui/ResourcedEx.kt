package com.vinka.ebike.common.utils.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import com.ashlikun.utils.ui.extend.resString


/**
 * 作者　　: 李坤
 * 创建时间: 2022/4/22　19:21
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
/**
 * 自定义View 获取String
 *  统一调用resString方法，方便全局拦截
 */
inline fun TypedArray.getStringX(context: Context, index: Int): String {
    if (getType(index) == TypedValue.TYPE_STRING) {
        getResourceId(index, 0).apply {
            if (this != 0) {
                return resString(context)
            }
        }
    } else {
        return getString(index).orEmpty()
    }
    return ""
}