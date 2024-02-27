package com.ashlikun.baseproject.common.utils.extend

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.annotation.DrawableRes
import com.ashlikun.baseproject.common.R
import com.ashlikun.glideutils.GlideLoad
import com.ashlikun.glideutils.GlideUtils
import com.ashlikun.utils.AppUtils
import com.ashlikun.utils.other.DimensUtils
import com.ashlikun.utils.ui.extend.dp
import com.ashlikun.utils.ui.extend.getViewSize
import com.ashlikun.utils.ui.extend.resColor
import com.ashlikun.utils.ui.extend.resDrawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.makeramen.roundedimageview.RoundedDrawable
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
 * @param showBgColorRes 背景 颜色
 * @param radiusDp 圆角半径
 * @param defaultPlace 是否使用默认占位图
 * @param placeholderResId 占位图
 * @param placeholderSize 占位图宽度大小
 * @param placeholderSizeMax 占位图是否最大化（和控件一样大，等比）
 * @param withCrossFade 是否使用淡入淡出过度效果
 * @param requestListener 事件回调
 */
fun ImageView.show(
    path: String?,
    radiusDp: Float = 0f,
    defaultPlace: Boolean = false,
    @DrawableRes
    placeholderResId: Int? = null,
    placeholderSizeMax: Boolean = false,
    placeholderSize: Int = -1,
    showBgColorRes: Int? = null,
    withCrossFade: Boolean = false,
    requestOptions: RequestOptions? = null,
    requestListener: RequestListener<Drawable>? = null
) {
    var options = requestOptions ?: RequestOptions()
    var placeholderResId = placeholderResId
    if (defaultPlace) {
        placeholderResId = R.drawable.material_default_image_1_1
    }
    var layerDrawable: PlaceholderDrawable? = null
    if (placeholderResId != null || showBgColorRes != null) {
        //没有缓存才加载
        try {
            layerDrawable = PlaceholderDrawable(
                this, placeholderResId?.resDrawable,
                placeholderSizeMax, placeholderSize,
                bgColor = showBgColorRes?.resColor,
                radius = radiusDp.dp.toFloat(),
            )
            options.error(layerDrawable)
            options.placeholder(layerDrawable)
            //如果图片是空，直接显示占位符
            if (path.isNullOrEmpty()) {
                setImageDrawable(layerDrawable)
                return
            }
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
        .load(path ?: "")
        .options(options)
        .apply {
            if (withCrossFade) {
                transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true)))
            }
        }
        .listener(requestListener)
        .also {
            if (layerDrawable != null) {
                it.addListener(layerDrawable)
            }
        }
        .show(this)
}

fun ImageView.showCircle(
    path: String?,
    @DrawableRes
    placeholderResId: Int? = null,
    placeholderSizeMax: Boolean = false,
    placeholderSize: Int = -1,
    showBgColorRes: Int? = R.color.gray_ee,
    withCrossFade: Boolean = false,
) {
    show(
        path,
        150f,
        placeholderResId = placeholderResId,
        showBgColorRes = showBgColorRes,
        placeholderSize = placeholderSize,
        placeholderSizeMax = placeholderSizeMax,
        withCrossFade = withCrossFade,
        requestOptions = GlideUtils.getCircleOptions()
    )
}

fun ImageView.showPlace(
    path: String?, radiusDp: Float = 0f,
    @DrawableRes
    placeholderResId: Int? = null,
    placeholderSizeMax: Boolean = false,
    placeholderSize: Int = -1, showBgColorRes: Int? = R.color.gray_ee,
    withCrossFade: Boolean = false,
    requestOptions: RequestOptions? = null
) {
    show(
        path, radiusDp,
        placeholderResId = placeholderResId,
        showBgColorRes = showBgColorRes,
        placeholderSize = placeholderSize,
        placeholderSizeMax = placeholderSizeMax,
        withCrossFade = withCrossFade,
        requestOptions = requestOptions
    )
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
    val imageView: ImageView,
    val placeholder: Drawable? = null,
    val placeholderSizeMax: Boolean,
    val placeholderSize: Int,
    radius: Float? = null,
    bgColor: Int? = null,
) : GradientDrawable(), RequestListener<Drawable> {
    var loadStatus = 0
    private var initPWidth = placeholder?.intrinsicWidth ?: -1
    private var initPHeight = placeholder?.intrinsicHeight ?: -1

    init {
        if (bgColor != null) {
            setColor(bgColor)
        }
        if (radius != null) {
            this.cornerRadius = radius
        }
        init()
    }


    private fun init() {
        if (placeholder != null) {
            //如果view大小无法获取就以展位图的大小计算
            val size = viewSize(imageView) ?: Point(initPWidth, initPHeight)
            val width = size.x
            val height = size.y
            val w = width - imageView.paddingLeft - imageView.paddingRight
            val h = height - imageView.paddingTop - imageView.paddingBottom
            setSize(width, height)
            if (placeholderSizeMax) {
                placeholder.setBounds(0, 0, w, h)
            } else {
                var minSize = min(w, h)
                var placeholderWidth =
                    if (placeholderSize > 0) placeholderSize
                    else when {
                        minSize <= 100.dp -> (minSize / 1.5f).toInt()
                        minSize <= 200.dp -> (minSize / 1.6f).toInt()
                        minSize <= 250.dp -> (minSize / 1.7f).toInt()
                        minSize <= 300.dp -> (minSize / 1.8f).toInt()
                        else -> (minSize / 2f).toInt()
                    }
                var bili = 2.5621f
                if (placeholder.intrinsicWidth > 2 && placeholder.intrinsicHeight > 2) {
                    //有大小，保证宽高比
                    bili = placeholder.intrinsicWidth / (placeholder.intrinsicHeight.toFloat())
                }
                placeholder.setBounds(0, 0, placeholderWidth, (placeholderWidth / bili).toInt())
            }

        }
    }

    private fun reSetDrawable() {
        if (imageView.drawable is RoundedDrawable) {
            if (loadStatus == 0) {
                imageView.setImageDrawable(this)
            }
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (placeholder != null && (placeholder!!.bounds.width() > 0 && placeholder!!.bounds.height() > 0)) {
            canvas.save()
            var transX = if (intrinsicWidth > 2) (intrinsicWidth - placeholder.bounds.width()) / 2 else 0
            var transY = if (intrinsicHeight > 2) (intrinsicHeight - placeholder.bounds.height()) / 2 else 0
            canvas.translate(transX.toFloat(), transY.toFloat())
            placeholder.draw(canvas)
            canvas.restore()

        }
    }

    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
        loadStatus = 2
        return false
    }

    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        loadStatus = 1
        return false
    }
}

private fun viewSize(view: View): Point? {
    if (view.width > 0 || view.height > 0) {
        return Point(view.width, view.height)
    }
    if (view.measuredWidth > 0 || view.measuredHeight > 0) {
        return Point(view.measuredWidth, view.measuredHeight)
    }
    if (view.layoutParams.width > 0 || view.layoutParams.height > 0) {
        return Point(view.layoutParams.width, view.layoutParams.height)
    }
    return null
}