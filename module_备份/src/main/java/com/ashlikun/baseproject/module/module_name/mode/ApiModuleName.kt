package com.ashlikun.baseproject.module.main.mode

import com.ashlikun.baseproject.libcore.mode.ApiBase
import com.ashlikun.okhttputils.retrofit.Retrofit

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/10　9:31
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：请求mode类
 */

interface ApiModuleName : ApiBase {
    companion object {
        val api: ApiModuleName by lazy { Retrofit.get().create(ApiModuleName::class.java) }
    }
}

