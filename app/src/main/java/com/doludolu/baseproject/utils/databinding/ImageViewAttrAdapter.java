package com.doludolu.baseproject.utils.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.doludolu.baseproject.R;
import com.hbung.glideutils.GlideOptions;
import com.hbung.glideutils.GlideUtils;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


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
        GlideOptions.Builder builder = new GlideOptions.Builder();
        if (holderId > 0) builder.setPlaceholder(holderId);
        else builder.setPlaceholder(R.drawable.material_default_image_1_1);
        if (errorId > 0) builder.setError(errorId);
        else builder.setError(R.color.glide_placeholder_color);
        GlideUtils.show(imageView, url, builder.bulider());
    }

    @BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView imageView, String url, int errorId) {
        GlideOptions.Builder builder = new GlideOptions.Builder();
        if (errorId > 0) builder.setError(errorId);
        GlideUtils.show(imageView, url, builder.bulider());
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        GlideOptions.Builder builder = new GlideOptions
                .Builder()
                .setPlaceholder1_1();
        GlideUtils.show(imageView, url, builder.bulider());
    }

    @BindingAdapter({"imageUrlCircle"})
    public static void imageUrlCircle(ImageView imageView, String url) {
        GlideOptions.Builder builder = new GlideOptions
                .Builder()
                .setPlaceholder1_1();
        builder.addTransformation(new CropCircleTransformation(imageView.getContext()));
        GlideUtils.show(imageView, url, builder.bulider());
    }


}