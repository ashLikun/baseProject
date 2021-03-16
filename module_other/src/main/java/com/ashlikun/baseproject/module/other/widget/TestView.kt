package com.ashlikun.baseproject.module.other.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.ashlikun.utils.ui.BitmapUtil
import com.ashlikun.baseproject.module.other.R

/**
 * 作者　　: 李坤
 * 创建时间: 2021/3/10　9:45
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：
 */
class TestView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {
    var paint: Paint = Paint()
    lateinit var bitmaoA: Bitmap
    lateinit var bitmaoB: Bitmap
    var xfermode: Xfermode? = null

    init {
        paint.isAntiAlias = true
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DARKEN )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        bitmaoA = BitmapUtil.decodeResource(context, R.mipmap.other_aaa, w, h)
        bitmaoB = BitmapUtil.decodeResource(context, R.mipmap.other_bbb, w, h)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //设置背景色
        var canvasWidth = width
        var r = canvasWidth / 3f
        // 绘制底图
        canvas.drawBitmap(bitmaoA, Matrix(), paint)
        //渲染模式
        paint.xfermode = xfermode
//        绘制叠加图
        val bbMatrix = Matrix()
        bbMatrix.setScale(2f, 2f)
        canvas.drawBitmap(bitmaoB, bbMatrix, paint)
        paint.xfermode = null
    }

}