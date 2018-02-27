package com.ashlikun.baseproject.utils;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/15 0015　21:15
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public abstract class ClickableNoUnderSpan extends ClickableSpan {
    /**
     * Performs the click action associated with this span.
     */
    public abstract void onClick(View widget);

    /**
     * Makes the text underlined and in the link color.
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
    }
}
