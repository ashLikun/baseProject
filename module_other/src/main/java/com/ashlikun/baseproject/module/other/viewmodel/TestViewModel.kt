package com.ashlikun.baseproject.module.other.viewmodel

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.ashlikun.baseproject.libcore.constant.RouterKey
import com.ashlikun.core.mvvm.BaseViewModel

/**
 * 作者　　: 李坤
 * 创建时间: 2023/8/26　18:12
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
class TestViewModel : BaseViewModel() {
    @Autowired(name = RouterKey.FLAG_POSITION)
    @JvmField
    var position: Int = 0

}