package com.doludolu.baseproject.mode.javabean.base;

/**
 * Created by Administrator on 2016/6/6.
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
