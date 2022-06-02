package com.ashlikun.baseproject.libcore.router.service

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 作者　　: 李坤
 * 创建时间: 2018/7/24　16:43
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：首页模块的服务
 */
interface IHomeService : IProvider {
    /**
     * 首页是否启动
     *
     * @return
     */
    fun isHomeStart(): Boolean;
}
