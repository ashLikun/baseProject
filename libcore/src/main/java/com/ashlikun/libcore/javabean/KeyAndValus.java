package com.ashlikun.libcore.javabean;

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:15
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：键值对的类
 */

public class KeyAndValus {

    private int key;
    private String valus;

    public KeyAndValus(int key, String valus) {
        this.key = key;
        this.valus = valus;
    }

    public KeyAndValus() {
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValus() {
        return valus;
    }

    public void setValus(String valus) {
        this.valus = valus;
    }
}
