package com.ashlikun.baseproject.utils.databinding;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/9/15　17:41
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：之定义属性与方法不一致
 */

import android.databinding.BindingMethod;
import android.databinding.BindingMethods;

import com.ashlikun.baseproject.widget.LineItenView;
import com.ashlikun.animcheckbox.AnimCheckBox;

/**
 * AnimCheckBox
 * <attr name="ab_strokeWidth" format="dimension" />
 * <attr name="ab_strokeColor" format="color" />
 * <attr name="ab_outColor" format="color" />
 * <attr name="ab_circleColor" format="color" />
 * <attr name="ab_isSelect" format="boolean" />
 * <attr name="ab_isCircle" format="boolean" />
 * <attr name="ab_autoSelect" format="boolean" />
 * <attr name="ab_text" format="string" />
 * <attr name="ab_textSize" format="dimension" />
 * <attr name="ab_textColor" format="color" />
 */
@BindingMethods({
        @BindingMethod(type = AnimCheckBox.class, attribute = "android:backgroundTint", method = "setBackgroundTintList"),
        @BindingMethod(type = LineItenView.class, attribute = "liv_sub_title", method = "setSubTitle")})
public class CustomViewBindAdapter {
}
