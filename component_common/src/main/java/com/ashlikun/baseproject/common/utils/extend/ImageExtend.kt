package com.ashlikun.baseproject.common.utils.extend

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import com.ashlikun.baseproject.common.R
import com.ashlikun.glideutils.GlideLoad
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.utils.other.DimensUtils
import com.ashlikun.utils.ui.extend.resColor
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlin.math.min

/**
 * 作者　　: 李坤
 * 创建时间: 2020/5/13　13:25
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */

fun ImageView.getDefaultTransformation(
    radiusDp: Float = 0f,
    cornerType: RoundedCornersTransformation.CornerType = RoundedCornersTransformation.CornerType.ALL
): MultiTransformation<Bitmap>? {
    val st = when (scaleType) {
        ScaleType.CENTER_CROP -> CenterCrop()
        ScaleType.CENTER_INSIDE, ScaleType.FIT_XY -> CenterInside()
        ScaleType.FIT_CENTER, ScaleType.FIT_START, ScaleType.FIT_END -> FitCenter()
        else -> CenterCrop()
    }
    if (radiusDp > 0) {
        return MultiTransformation(
            st,
            RoundedCornersTransformation(DimensUtils.dip2px(radiusDp), 0, cornerType)
        )
    }
    return MultiTransformation(st)
}

/**
 * 显示图片
 * @param showBg 背景 颜色
 * @param placeholderDp 占位图宽度大小 dp
 */
fun ImageView.show(
    path: String,
    radiusDp: Float = 0f,
    isPlaceholder: Boolean = false,
    showBgColorRes: Int = R.color.color_f5f5f5,
    requestOptions: RequestOptions? = null,
    requestListener: RequestListener<Drawable>? = null
) {
    var options = requestOptions ?: RequestOptions()
    if (isPlaceholder) {
        try {
            val drawable = BitmapDrawable(
                resources,
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.material_default_image_1_1
                )
            )
            val layerDrawable = PlaceholderDrawable(drawable)
            layerDrawable.setColor(showBgColorRes.resColor)
            layerDrawable.cornerRadius = DimensUtils.dip2px(radiusDp).toFloat()
            options.error(layerDrawable)
            options.placeholder(layerDrawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    if (radiusDp > 0) {
        if (!options.transformations.isNullOrEmpty()) {
            val a = options.transformations[Bitmap::class.java]!! as Transformation<Bitmap>
            options.transform(MultiTransformation(a, getDefaultTransformation(radiusDp)))
        } else {
            options.transform(getDefaultTransformation(radiusDp))
        }
    }

    GlideLoad.with(this)
        .load(path)
        .options(options)
        .requestListener(requestListener)
        .show(this)
}

fun ImageView.showCircle(
    path: String,
    showBgColorRes: Int = R.color.color_f5f5f5,
    isPlaceholder: Boolean = true
) {
    show(path, 150f, isPlaceholder, showBgColorRes, GlideUtils.getCircleOptions())
}

fun ImageView.showPlace(
    path: String, radiusDp: Float = 0f, isPlaceholder: Boolean = true,
    showBgColor: Int = R.color.color_f5f5f5, requestOptions: RequestOptions? = null
) {
    show(path, radiusDp, isPlaceholder, showBgColor, requestOptions)
}

/**
 * 设置SimpleDraweeView  上面蒙层
 */
fun ImageView.isShowMengcheng(
    radiusDp: Float = 0f,
    color: Int = 0x80000000.toInt(),
    isMengCheng: Boolean = true
) {
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

class PlaceholderDrawable(
    var placeholder: Drawable,
    orientation: Orientation = Orientation.TOP_BOTTOM,
    colors: IntArray? = null
) : GradientDrawable(orientation, colors) {
    override fun onBoundsChange(r: Rect) {
        super.onBoundsChange(r)
        var minSize = min(r.width(), r.height())
        var placeholderWidth = when {
            minSize <= DimensUtils.dip2px(100f) -> (minSize / 1.5f).toInt()
            minSize <= DimensUtils.dip2px(200f) -> (minSize / 1.6f).toInt()
            minSize <= DimensUtils.dip2px(250f) -> (minSize / 1.7f).toInt()
            minSize <= DimensUtils.dip2px(300f) -> (minSize / 1.8f).toInt()
            else -> (minSize / 2f).toInt()
        }
        placeholder.setBounds(0, 0, placeholderWidth, (placeholderWidth / 2.5621f).toInt())
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.save()
        var transX = (bounds.width() - placeholder.bounds.width()) / 2
        var transY = (bounds.height() - placeholder.bounds.height()) / 2
        canvas.translate(transX.toFloat(), transY.toFloat())
        placeholder.draw(canvas)
        canvas.restore()
    }
}
