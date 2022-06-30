package com.ashlikun.baseproject.common.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.ashlikun.baseproject.common.R
import com.ashlikun.baseproject.common.databinding.ViewLineItemBinding
import com.ashlikun.utils.other.DimensUtils
import com.ashlikun.utils.ui.extend.dp
import com.ashlikun.utils.ui.extend.layoutInflater
import com.ashlikun.utils.ui.extend.setMargin
import com.vinka.ebike.common.utils.ui.getStringX

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/16 0016　21:11
 * 邮箱　　：496546144@qq.com
 *
 *
 * 功能介绍：类似我的里面的一行view
 */

class LineItenView @JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    LinearLayout(context, attrs, defStyleAttr) {
    val binding by lazy {
        ViewLineItemBinding.inflate(layoutInflater, this)
    }
    private val paint = Paint()
    var isShowMsgTips = false
        set(value) {
            field = value
            binding.msgTipsIv.isVisible = value
        }
    var isShowArror = true
        set(value) {
            field = value
            binding.rightArraw.isVisible = value
        }
    private var bottomLineSize: Int = 0
    private var bottomLineMarginLeft: Int = 0
    private var bottomLineMarginRight: Int = 0
    private var bottomLineColor = Color.parseColor("#ffcccccc")
    private var iconRes = 0
    private var rightIconRes = 0
    private var subDrawablePadding = 0
    private var title: CharSequence? = null
    private var subTitle: CharSequence? = null


    init {
        setWillNotDraw(false)
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.LineItenView
        )
        isShowArror = a.getBoolean(R.styleable.LineItenView_liv_is_show_arror, isShowArror)
        bottomLineSize = a.getDimensionPixelSize(
            R.styleable.LineItenView_liv_bottom_line_size,
            DimensUtils.dip2px(context, 0f)
        )
        bottomLineMarginLeft = a.getDimensionPixelSize(
            R.styleable.LineItenView_liv_bottom_line_marginLeft,
            DimensUtils.dip2px(context, 0f)
        )
        bottomLineMarginRight = a.getDimensionPixelSize(
            R.styleable.LineItenView_liv_bottom_line_marginRight,
            DimensUtils.dip2px(context, 0f)
        )
        bottomLineColor =
            a.getColor(R.styleable.LineItenView_liv_bottom_line_color, bottomLineColor)
        iconRes = a.getResourceId(R.styleable.LineItenView_liv_icon_res, iconRes)
        rightIconRes = a.getResourceId(R.styleable.LineItenView_liv_right_icon_res, iconRes)
        title = a.getStringX(context, R.styleable.LineItenView_liv_title)
        subTitle = a.getStringX(context, R.styleable.LineItenView_liv_sub_title)
        isShowMsgTips = a.getBoolean(R.styleable.LineItenView_liv_msg_tips, isShowMsgTips)
        subDrawablePadding =
            a.getDimensionPixelSize(R.styleable.LineItenView_liv_sub_title_drawable_padding, 0)
        initView(context, a)
        a.recycle()
    }

    private fun initView(context: Context, a: TypedArray) {
        gravity = Gravity.CENTER_VERTICAL
        if (a.hasValue(R.styleable.LineItenView_liv_title_size)) {
            binding.titleView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                a.getDimensionPixelSize(R.styleable.LineItenView_liv_title_size, 0).toFloat()
            )
        }
        if (a.hasValue(R.styleable.LineItenView_liv_title_color)) {
            binding.titleView.setTextColor(a.getColor(R.styleable.LineItenView_liv_title_color, 0))
        }
        if (a.hasValue(R.styleable.LineItenView_liv_sub_title_size)) {
            binding.subTitleView.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                a.getDimensionPixelSize(R.styleable.LineItenView_liv_sub_title_size, 0).toFloat()
            )
        }
        if (a.hasValue(R.styleable.LineItenView_liv_sub_title_color)) {
            binding.subTitleView.setTextColor(
                a.getColor(
                    R.styleable.LineItenView_liv_sub_title_color,
                    0
                )
            )
        }

        paint.isAntiAlias = true
        paint.color = bottomLineColor

        if (bottomLineSize > 0) {
            setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom + bottomLineSize)
        }

        binding.imageView.visibility = if (iconRes != 0) View.VISIBLE else View.GONE
        if (iconRes == 0) {
            binding.titleView.setMargin(0, 0, 0, 0)
        } else {
            binding.titleView.setMargin(8.dp, 0, 0, 0)
        }
        binding.imageView.setImageResource(iconRes)
        binding.rightImg.isVisible = rightIconRes != 0
        if (rightIconRes != 0) {
            binding.rightImg.setImageResource(rightIconRes)
        }
        binding.titleView.text = title
        binding.subTitleView.text = subTitle
    }

    fun setTitle(title: CharSequence) {
        this.title = title
        binding.titleView.text = title
    }

    fun setSubTitle(title: CharSequence) {
        this.subTitle = title
        binding.subTitleView.text = title
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (bottomLineSize > 0) {
            canvas.drawRect(
                bottomLineMarginLeft.toFloat(),
                (height - bottomLineSize).toFloat(),
                width.toFloat() - bottomLineMarginRight,
                height.toFloat(),
                paint
            )
        }
    }

}
