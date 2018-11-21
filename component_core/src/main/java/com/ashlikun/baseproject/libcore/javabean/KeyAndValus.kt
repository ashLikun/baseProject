package com.ashlikun.baseproject.libcore.javabean

import com.ashlikun.utils.other.StringUtils

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:15
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：键值对的类
 */

class KeyAndValus(key: Int = 0, valus: String? = null) {
    private var key = 0;
    private var keyS: String? = null
        get() = StringUtils.dataFilter(keyS)
    private var valus: String? = null
        get() = StringUtils.dataFilter(valus)

    init {
        this.key = key;
        this.valus = valus;
    }
}
