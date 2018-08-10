package com.ashlikun.baseproject.libcore.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ashlikun.baseproject.libcore.R;
import com.ashlikun.baseproject.libcore.javabean.BannerAdData;
import com.ashlikun.baseproject.libcore.utils.http.HttpManager;
import com.ashlikun.glideutils.GlideUtils;
import com.ashlikun.utils.http.HttpLocalUtils;
import com.ashlikun.xviewpager.listener.ViewPageHelperListener;
import com.ashlikun.xviewpager.view.BannerViewPager;
import com.bumptech.glide.request.RequestOptions;


/**
 * @author　　: 李坤
 * 创建时间: 2018/8/10 14:55
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：Glide加载网络图片的BannerHolder
 */

public abstract class BannerImageHolderView implements ViewPageHelperListener<BannerAdData> {
    private ImageView imageView;
    int width = 0;
    int height = 0;

    @Override
    public View createView(Context context, BannerViewPager banner, BannerAdData data, int position) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(width = banner.getWidth(), height = banner.getHeight()));
        imageView.setMinimumWidth(width);
        imageView.setMinimumHeight(height);
        imageView.setId(R.id.switchRoot);
        GlideUtils.show(imageView, HttpLocalUtils.getHttpFileUrl(HttpManager.BASE_URL, data.imgUrl),
                new RequestOptions().override(width, height));
        imageView.setOnClickListener(v -> {
            onItemClicklistener(imageView, 0, data);
        });
        return imageView;
    }

    public View getView() {
        return imageView;
    }

    public abstract void onItemClicklistener(View item, int position, BannerAdData data);

}
