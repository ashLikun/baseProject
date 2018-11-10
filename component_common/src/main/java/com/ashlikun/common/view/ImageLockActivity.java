package com.ashlikun.common.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.ashlikun.baseproject.libcore.constant.RouterKey;
import com.ashlikun.baseproject.libcore.constant.RouterPath;
import com.ashlikun.common.R;
import com.ashlikun.common.mode.javabean.ImageData;
import com.ashlikun.core.activity.BaseActivity;
import com.ashlikun.glideutils.GlideUtils;
import com.ashlikun.photoview.PhotoView;
import com.ashlikun.photoview.ScaleFinishView;
import com.ashlikun.xviewpager.listener.ViewPageHelperListener;
import com.ashlikun.xviewpager.view.BannerViewPager;

import java.util.ArrayList;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/9/2　17:52
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：查看图片的activity
 */
@Route(path = RouterPath.IMAGE_LOCK)
public class ImageLockActivity extends BaseActivity
        implements ViewPageHelperListener<ImageData>, ScaleFinishView.OnSwipeListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    private BannerViewPager viewPager;
    private TextView textView;
    @Autowired(name = RouterKey.FLAG_DATA)
    protected ArrayList<ImageData> listDatas;
    @Autowired(name = RouterKey.FLAG_POSITION)
    protected int position;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_lock;
    }

    @Override
    public void initView() {
        viewPager = f(R.id.viewPager);
        textView = f(R.id.textView);
        viewPager.setPages(this, listDatas);
        if (listDatas != null) {
            textView.setText((position + 1) + "/" + listDatas.size());
        }
        if (position < listDatas.size()) {
            viewPager.setCurrentItem(position, false);
        }
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public View createView(Context context, BannerViewPager banner, ImageData data, int position) {
        ScaleFinishView scaleFinishView = new ScaleFinishView(this);
        scaleFinishView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scaleFinishView.setOnSwipeListener(this);
        PhotoView photoView = new PhotoView(this);
        scaleFinishView.addView(photoView, new ViewGroup.LayoutParams(-1, -1));
        GlideUtils.show(photoView, data.img_url);
        photoView.setOnClickListener(this);
        return scaleFinishView;
    }


    @Override
    public void onOverSwipe(boolean isFinish) {
        if (isFinish) {
            finish();
        }
    }

    @Override
    public boolean onSwiping(float v, float v1) {
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (listDatas != null) {
            textView.setText((position + 1) + "/" + listDatas.size());
        }
    }

    @Override
    public boolean isStatusTranslucent() {
        return true;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
