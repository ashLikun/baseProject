package com.doludolu.baseproject.widget;

import android.support.annotation.NonNull;
import android.view.View;

import com.azoft.carousellayoutmanager.FrameLayoutManager;
import com.azoft.carousellayoutmanager.ItemTransformation;
import com.azoft.carousellayoutmanager.OnLayoutListener;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/26 0026　0:07
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class CardZoomOnLayoutListener implements OnLayoutListener {
    @Override
    public ItemTransformation transformChild(@NonNull final View child, final float itemPositionToCenterDiff, final int orientation) {
        //final float scale = (float) (2 * (2 * -StrictMath.atan(Math.abs(itemPositionToCenterDiff) + 1.0) / Math.PI + 1));
        final float scale = (float) (1 - Math.min(1, Math.abs(itemPositionToCenterDiff) * 0.2));
        final float translateY;
        final float translateX;
        if (FrameLayoutManager.VERTICAL == orientation) {
            final float translateYGeneral = child.getMeasuredHeight() * (1 - scale) * 1.1f;
            translateY = Math.signum(itemPositionToCenterDiff) * translateYGeneral;
            translateX = 0;
        } else {
            final float translateXGeneral = child.getMeasuredWidth() * (1 - scale) * 1.1f;
            translateX = Math.signum(itemPositionToCenterDiff) * translateXGeneral;
            translateY = 0;
        }

        return new ItemTransformation(scale, scale, translateX, translateY);
    }
}