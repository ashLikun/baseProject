package com.ashlikun.baseproject.libcore.javabean;

import com.ashlikun.utils.other.StringUtils;

/**
 * @author　　: 李坤
 * 创建时间: 2018/7/18 13:15
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：键值对的类
 */

public class KeyAndValus {

    private int key;
    private String keyS;
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

    public String getValus() {
        return StringUtils.dataFilter(valus);
    }

    public String getKeyS() {
        return StringUtils.dataFilter(keyS);
    }

}
