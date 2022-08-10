package com.ashlikun.baseproject.common.utils.store

import com.ashlikun.baseproject.libcore.constant.SpKey
import com.ashlikun.utils.other.store.StoreUtils

/**
 * 作者　　: 李坤
 * 创建时间: 2022.5.16　15:38
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
object AppStoreUtils {
    /**
     * 是否同意用户协议
     */
    fun isXieYi() = StoreUtils.getBoolean(SpKey.IS_XIEYI, false)
    fun setXieYi() = StoreUtils.putBoolean(SpKey.IS_XIEYI, true)
    fun exitLogin() {
    }

}