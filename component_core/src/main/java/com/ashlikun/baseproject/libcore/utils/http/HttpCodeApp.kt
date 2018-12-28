package com.lingyun.client.libcore.utils.http

/**
 * 作者　　: 李坤
 * 创建时间: 2018/12/25　17:02
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
object HttpCodeApp {
    /**
     * 未登录（出现此状态，可跳转页面进入登录页）
     */
    const val NO_LOGIN = 699
    /**
     * 未实名认证（出现此状态，可跳转页面进入实名认证页面）
     */
    const val NO_SHIMING = 698
    /**
     * 无可用客服，客户在进入聊天页面获取此状态 通知一下并返回上一页
     */
    const val NO_KEFU = 777
}