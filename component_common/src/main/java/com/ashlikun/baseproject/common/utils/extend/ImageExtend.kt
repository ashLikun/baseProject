package com.ashlikun.baseproject.common.utils.extend

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import com.ashlikun.glideutils.GlideLoad
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.baseproject.common.R
import com.ashlikun.utils.other.DimensUtils
import com.ashlikun.utils.ui.*
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

/**
 * 作者　　: 李坤
 * 创建时间: 2020/5/13　13:25
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
/**
 * 显示图片
 * @param showBg 背景 颜色
 * @param placeholderDp 占位图宽度大小 dp
 */
fun ImageView.show(path: String, radiusDp: Float = 0f, placeholderDp: Float = 0f,
                   showBgColorRes: Int = R.color.color_f5f5f5, requestOptions: RequestOptions? = null, requestListener: RequestListener<Any>? = null) {
    var options = RequestOptions()
    if (placeholderDp > 0) {
        val drawable = BitmapDrawable(resources, BitmapUtil.decodeResource(context, R.drawable.material_default_image_1_1,
                DimensUtils.dip2px(placeholderDp), (DimensUtils.dip2px(placeholderDp) / 2.5621f).toInt()))
        options.error(drawable)
        options.placeholder(drawable)
    }

    if (radiusDp > 0) {
        options.apply(GlideUtils.getRoundedOptions(DimensUtils.dip2px(radiusDp)))
    }
    if (requestOptions != null) {
        options.apply(requestOptions)
    }
    GlideLoad.with(this)
            .load(path)
            .options(options)
            .requestListener(PlaceRequestListener(this, showBgColorRes, radiusDp, requestListener))
            .show(this)
}

fun ImageView.showCircle(path: String, showBgColorRes: Int = R.color.color_f5f5f5, placeholderDp: Float = 30f) {
    if (showBgColorRes != 0) {
        this.background = DrawableUtils.getGradientDrawable(showBgColorRes, 200f)
    }
    show(path, 0f, placeholderDp, 0, GlideUtils.getCircleOptions())
}

fun ImageView.showPlace(path: String, radiusDp: Float = 0f, placeholderDp: Float = 103f,
                        showBgColor: Int = R.color.color_f5f5f5, requestOptions: RequestOptions? = null) {

    show(path, radiusDp, placeholderDp, showBgColor, requestOptions)
}

fun ImageView.showMaxPlace(path: String, radiusDp: Float = 0f,
                           showBgColor: Int = R.color.color_f5f5f5, requestOptions: RequestOptions? = null) =
        showPlace(path, radiusDp, 103f, showBgColor, requestOptions)

fun ImageView.showMinPlace(path: String, radiusDp: Float = 0f,
                           showBgColor: Int = R.color.color_f5f5f5, requestOptions: RequestOptions? = null) =
        showPlace(path, radiusDp, 59f, showBgColor, requestOptions)

/**
 * 设置SimpleDraweeView  上面蒙层
 */
fun ImageView.isShowMengcheng(radiusDp: Float = 0f, color: Int = 0x80000000.toInt(), isMengCheng: Boolean = true) {
    if (isMengCheng) {
        visibility = View.VISIBLE
        val drawable = GradientDrawable()
        drawable.setColor(color)
        val size = DimensUtils.dip2px(radiusDp).toFloat()
        drawable.cornerRadii = floatArrayOf(size, size, size, size, size, size, size, size)
        background = drawable
    } else {
        visibility = View.GONE
    }
}

class PlaceRequestListener(var view: View, var showBgColorRes: Int = R.color.color_f5f5f5,
                           var radiusDp: Float = 0f,
                           var listener: RequestListener<Any>?) : RequestListener<Any> {
    var oldBack: Drawable? = null

    companion object {
        const val SET_KEY = 305688845
    }

    init {
        this.view.setTag(SET_KEY, oldBack)
        setBack()
    }

    fun setBack() {
        if (showBgColorRes != 0) {
            oldBack = this.view.getTag(SET_KEY) as Drawable?
            this.view.background = DrawableUtils.getGradientDrawable(showBgColorRes, radiusDp)
        }
    }

    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Any>?, isFirstResource: Boolean): Boolean {
        setBack()
        return listener?.onLoadFailed(e, model, target, isFirstResource) ?: false
    }

    override fun onResourceReady(resource: Any?, model: Any?, target: Target<Any>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        this.view.background = oldBack
        return listener?.onResourceReady(resource, model, target, dataSource, isFirstResource)
                ?: false
    }

}