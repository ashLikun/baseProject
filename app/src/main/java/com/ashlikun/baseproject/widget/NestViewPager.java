package com.ashlikun.baseproject.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/24 0024　23:55
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */

public class NestViewPager extends ViewPager {
    public NestViewPager(Context context) {
        super(context);
    }

    public NestViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return v.getClass().getName().equals("com.baidu.mapapi.map.MapView")
                || v.getClass().getName().equals("com.baidu.mapapi.map.TextureMapView")
                || v instanceof RecyclerView
                || v.getClass().getName().equals("com.amap.api.maps.MapView")
                || super.canScroll(v, checkV, dx, x, y);
    }
}
