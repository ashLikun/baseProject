package com.ashlikun.baseproject.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ashlikun.banner.ConvenientBanner;
import com.ashlikun.banner.holder.Holder;
import com.ashlikun.glideutils.GlideUtils;
import com.ashlikun.utils.http.HttpLocalUtils;
import com.bumptech.glide.request.RequestOptions;
import com.ashlikun.baseproject.R;
import com.ashlikun.libcore.javabean.BannerAdData;
import com.ashlikun.libcore.utils.http.HttpManager;


/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public abstract class NetworkImageHolderView implements Holder<BannerAdData> {
    private ImageView imageView;
    int width = 0;
    int height = 0;

    @Override
    public View createView(ConvenientBanner banner, Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(width = banner.getWidth(), height = banner.getHeight()));
        imageView.setMinimumWidth(width);
        imageView.setMinimumHeight(height);
        imageView.setId(R.id.switchRoot);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, final BannerAdData data) {

        GlideUtils.show(imageView, HttpLocalUtils.getHttpFileUrl(HttpManager.BASE_URL, data.getImg_url()),
                new RequestOptions()
                        .override(width, height));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicklistener(imageView, position, data);
            }
        });

    }

    public View getView() {
        return imageView;
    }

    public abstract void onItemClicklistener(View item, int position, BannerAdData data);

}
