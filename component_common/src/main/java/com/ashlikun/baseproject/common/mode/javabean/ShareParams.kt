package com.ashlikun.baseproject.common.mode.javabean

import android.graphics.Bitmap
import com.ashlikun.baseproject.common.R
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.ui.ScreenUtils
import com.ashlikun.utils.ui.image.BitmapUtil
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import java.io.Serializable

/**
 * 作者　　: 李坤
 * 创建时间: 2018/10/25　21:12
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：分享的数据模型
 * link：链接地址（URL链接，图片链接等）
 * content：摘要
 * title：标题
 * img：缩略图
 * appid：平台账号
 * type：分享方式（1：图文链接，2：图片）
 */
class ShareParams : Serializable {
    var link: String? = null
    var content: String? = null
    var title: String? = null
    var img: String? = null
    var appid: String? = null
    var type = 0

    /**
     * 这个是下载对应的图片，在接口返回的时候就要调用
     */
    @Transient
    var imgBitmap: Bitmap? = null
    fun loadImage() {
        if (img.isNullOrEmpty()) {
            return
        }
        Observable.create(ObservableOnSubscribe<Bitmap> { e: ObservableEmitter<Bitmap?> ->
            try {
                if (type == 2) {
                    //单独分享图片就用大图
                    val bitmap: Bitmap = Glide.with(AppUtils.app).asBitmap().load(img)
                            .submit(ScreenUtils.width, ScreenUtils.height).get()
                    e.onNext(bitmap)
                } else if (type == 1) {
                    //其他情况，缩略图
                    //一定要压缩，不然会分享失败
                    val bitmap: Bitmap = Glide.with(AppUtils.app).asBitmap().load(img)
                            .submit(THUMB_SIZE, THUMB_SIZE).get()
                    e.onNext(bitmap)
                }
            } catch (t: Throwable) {
            }
            e.onComplete()
        } as ObservableOnSubscribe<Bitmap>?).subscribeOn(Schedulers.io())
                .doOnError { throwable: Throwable? -> imgBitmap = null }
                .subscribe { bitmap: Bitmap? ->
                    //图片加载成功
                    imgBitmap = bitmap
                }
    }

    fun check(): Boolean {
        if (imgBitmap == null) {
            imgBitmap =
                    BitmapUtil.decodeResource(R.mipmap.app_logo, THUMB_SIZE, THUMB_SIZE)
        }
        if (type != 1 && type != 2) {
            return false
        }
        return !appid.isNullOrEmpty()
    } //    public WXMediaMessage getWxMessage() {

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
    companion object {
        private const val THUMB_SIZE = 150
    }
}