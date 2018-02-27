package com.ashlikun.baseproject.utils.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.ashlikun.glideutils.GlideUtils;
import com.bumptech.glide.request.RequestOptions;


/**
 * 作者　　: 李坤
 * 创建时间:2017/8/13 0013　0:26
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：DataBind加载图片
 */

public class ImageViewAttrAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        if (resId > 0) {
            view.setImageResource(resId);
        } else {
            view.setImageDrawable(null);
        }
    }

    /**
     * 作者　　: 李坤
     * 创建时间: 2017/8/13 0013 0:28
     * <p>
     * 方法功能：这里loadImage()绑定了多个属性，"app:imageUrl"、“app:placeHolder”、"app:error"属性。
     * 分别表示要加载的图片路径，占位图片，和加载出错时加载的图片。
     */

    @BindingAdapter({"imageUrl", "placeHolder", "error"})
    public static void loadImage(ImageView imageView, String url, int holderId, int errorId) {
        GlideUtils.show(imageView, url, new RequestOptions()
                .placeholder(holderId)
                .error(errorId)
        );
    }

    @BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView imageView, String url, int errorId) {
        GlideUtils.show(imageView, url, new RequestOptions()
                .placeholder(errorId)
                .error(errorId)
        );
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        GlideUtils.show(imageView, url);
    }

    @BindingAdapter({"imageUrlCircle"})
    public static void imageUrlCircle(ImageView imageView, String url) {
        GlideUtils.showCircle(imageView, url);
    }


}