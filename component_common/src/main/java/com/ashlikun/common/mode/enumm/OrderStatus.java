package com.ashlikun.common.mode.enumm;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者　　: 李坤
 * 创建时间:2017/9/24 0024　19:43
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：订单状态
 * （默认0：全部状态(或异常订单)，1：待付款，2：待使用，3：已完成（包含待评价），4：已取消,6：待评价）
 */

public class OrderStatus {
    public static final int ALL = 0;
    public static final int DFK = 1;
    public static final int DSY = 2;
    public static final int COMPLETE = 3;
    public static final int CANCEL = 4;
    public static final int DPJ = 6;

    @IntDef(value = {ALL, DFK, DSY, COMPLETE, CANCEL, DPJ})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Code {

    }

    public static boolean isDfk(@Code int code) {
        return code == OrderStatus.DFK;
    }

    public static boolean isDsy(@Code int code) {
        return code == OrderStatus.DSY;
    }

    public static boolean isCancel(@Code int code) {
        return code == OrderStatus.CANCEL;
    }

    public static boolean isComplete(@Code int code) {
        return code == OrderStatus.COMPLETE;
    }

    public static boolean isDpj(@Code int code) {
        return code == OrderStatus.DPJ;
    }


    public static CharSequence getValue(@Code int code) {
        if (code == ALL) {
            return "";
        } else if (code == DFK) {
            return "等待付款";
        } else if (code == DSY) {
            return "待使用";
        } else if (code == COMPLETE || code == DPJ) {
            return "已完成";
        } else if (code == CANCEL) {
            return "已取消";
        } else {
            return "";
        }
    }
}
