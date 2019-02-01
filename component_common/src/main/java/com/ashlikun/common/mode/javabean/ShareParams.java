package com.ashlikun.common.mode.javabean;

import android.graphics.Bitmap;

import com.ashlikun.common.R;
import com.ashlikun.utils.AppUtils;
import com.ashlikun.utils.other.StringUtils;
import com.ashlikun.utils.ui.BitmapUtil;
import com.ashlikun.utils.ui.ScreenInfoUtils;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者　　: 李坤
 * 创建时间: 2018/10/25　21:12
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：分享的数据模型
 * link：链接地址（URL链接，图片链接等）
 * content：摘要
 * title：标题
 * img：缩略图
 * appid：平台账号
 * type：分享方式（1：图文链接，2：图片）
 */
public class ShareParams implements Serializable {
    private static final int THUMB_SIZE = 150;
    public String link;
    public String content;
    public String title;
    public String img;
    public String appid;
    public int type;
    /**
     * 这个是下载对应的图片，在接口返回的时候就要调用
     */
    public transient Bitmap imgBitmap;

    public void loadImage() {
        if (StringUtils.isEmpty(img)) {
            return;
        }
        Observable.create((ObservableOnSubscribe<Bitmap>) e -> {
            try {
                if (type == 2) {
                    //单独分享图片就用大图
                    Bitmap bitmap = Glide.with(AppUtils.getApp()).asBitmap().load(img)
                            .submit(ScreenInfoUtils.getWidth(), ScreenInfoUtils.getHeight()).get();
                    e.onNext(bitmap);
                } else if (type == 1) {
                    //其他情况，缩略图
                    //一定要压缩，不然会分享失败
                    Bitmap bitmap = Glide.with(AppUtils.getApp()).asBitmap().load(img)
                            .submit(THUMB_SIZE, THUMB_SIZE).get();
                    e.onNext(bitmap);
                }
            } catch (Throwable t) {
            }
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .doOnError(throwable -> {
                    imgBitmap = null;
                })
                .subscribe(bitmap -> {
                    //图片加载成功
                    imgBitmap = bitmap;
                });
    }

    public boolean check() {
        if (imgBitmap == null) {
            imgBitmap = BitmapUtil.decodeResource(AppUtils.getApp(), R.mipmap.app_logo, THUMB_SIZE, THUMB_SIZE);
        }
        if (type != 1 && type != 2) {
            return false;
        }
        if (StringUtils.isEmpty(appid)) {
            return false;
        }
        return true;
    }

//    public WXMediaMessage getWxMessage() {
//        WXMediaMessage msg = new WXMediaMessage();
//        if (type == 1) {
//            //图文链接
//            WXWebpageObject webpage = new WXWebpageObject();
//            webpage.webpageUrl = link;
//            msg.mediaObject = webpage;
//            msg.thumbData = BitmapUtil.bitmapToByte(imgBitmap, 100);
//            BitmapUtil.recycle(imgBitmap);
//        } else if (type == 2) {
//            //图片
//            Bitmap thumbBmp = Bitmap.createScaledBitmap(imgBitmap, THUMB_SIZE, THUMB_SIZE, true);
//            WXImageObject imgObj = new WXImageObject(imgBitmap);
//            msg.mediaObject = imgObj;
//            msg.thumbData = BitmapUtil.bitmapToByte(imgBitmap, 100);
//
//            BitmapUtil.recycle(imgBitmap);
//            BitmapUtil.recycle(thumbBmp);
//        }
//        msg.title = StringUtils.dataFilter(title, "Yoohfit");
//        msg.description = StringUtils.dataFilter(content, "Yoohfit");
//        return msg;
//    }
}
