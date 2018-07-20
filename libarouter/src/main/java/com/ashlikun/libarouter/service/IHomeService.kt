package com.ashlikun.libarouter.service

import android.app.Application
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/19　14:06
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：首页模块的服务
 */
interface IHomeService : IProvider {
    fun getApp(): Application
}