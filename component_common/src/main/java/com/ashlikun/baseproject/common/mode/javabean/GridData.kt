package com.ashlikun.baseproject.common.mode.javabean

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/18　11:38
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：一般用于像个人中心的订单item，一个图标加一个文字，可扩展成加一个消息条数
 */
class GridData {
    /**
     * 用于设置标识
     */
    var tag: Any? = null
    var icon: Any
    var text: CharSequence
    var msgNumber: Int = 0

    constructor(icon: Any, text: CharSequence) {
        this.icon = icon
        this.text = text
    }

    constructor(icon: Any, text: CharSequence, msgNumber: Int) {
        this.icon = icon
        this.text = text
        this.msgNumber = msgNumber
    }
}
