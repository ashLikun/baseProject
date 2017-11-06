package com.doludolu.baseproject.mode.enum_type;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者　　: 李坤
 * 创建时间:2017/9/24 0024　19:43
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：认证状态
 * 类型 0,1,2（分别代表未认证，正在认证，已认证）
 */

public class DomeEnum {
    public static final int AUTH_NO = 0;
    public static final int AUTH_ING = 1;
    public static final int AUTH_YES = 2;

    @IntDef(value = {AUTH_NO, AUTH_ING, AUTH_YES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Code {

    }

    public static CharSequence getValue(@Code int code) {
        if (code == AUTH_NO) {
            return "未认证";
        } else if (code == AUTH_ING) {
            return "正在认证";
        } else if (code == AUTH_YES) {
            return "已认证";
        } else {
            return "";
        }
    }
}
