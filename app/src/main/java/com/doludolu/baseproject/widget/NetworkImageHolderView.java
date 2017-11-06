package com.doludolu.baseproject.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.doludolu.baseproject.R;
import com.doludolu.baseproject.utils.http.HttpManager;
import com.doludolu.baseproject.mode.javabean.base.BannerAdData;
import com.hbung.banner.ConvenientBanner;
import com.hbung.banner.holder.Holder;
import com.hbung.glideutils.GlideOptions;
import com.hbung.glideutils.GlideUtils;
import com.ashlikun.utils.http.HttpLocalUtils;


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

        GlideUtils.show(imageView, HttpLocalUtils.getHttpFileUrl(HttpManager.BASE_URL,data.getImg_url()), new GlideOptions.Builder()
                .setPlaceholder3_1()
                .setWidth(width).setHeight(height).bulider());
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
