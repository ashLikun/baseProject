package com.ashlikun.common.mode.javabean;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/18　11:38
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：一般用于像个人中心的订单item，一个图标加一个文字，可扩展成加一个消息条数
 */
public class GridData {
    /**
     * 用于设置标识
     */
    public Object tag;
    public Object icon;
    public CharSequence text;
    public int msgNumber;

    public GridData(Object icon, CharSequence text) {
        this.icon = icon;
        this.text = text;
    }

    public GridData(Object icon, CharSequence text, int msgNumber) {
        this.icon = icon;
        this.text = text;
        this.msgNumber = msgNumber;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public CharSequence getText() {
        return text;
    }
}
