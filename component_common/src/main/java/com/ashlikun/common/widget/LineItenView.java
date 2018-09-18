package com.ashlikun.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashlikun.common.R;
import com.ashlikun.utils.other.DimensUtils;
import com.ashlikun.utils.ui.DrawableUtils;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/16 0016　21:11
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：类似我的里面的一行view
 */

public class LineItenView extends LinearLayout {
    Paint paint;
    private boolean isShowIcon;
    private boolean isShowArror;
    int bottomLineSize;
    int bottomLineColor;
    private int iconRes;
    private CharSequence title;
    private CharSequence subTitle;

    private TextView textView;
    private TextView subTextView;
    private ImageView imageView;

    public LineItenView(Context context) {
        this(context, null);
    }

    public LineItenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineItenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LineItenView);
        isShowIcon = a.getBoolean(R.styleable.LineItenView_liv_is_show_icon, true);
        isShowArror = a.getBoolean(R.styleable.LineItenView_liv_is_show_arror, true);
        bottomLineSize = a.getDimensionPixelSize(R.styleable.LineItenView_liv_bottom_line_size, DimensUtils.dip2px(context, 0));
        bottomLineColor = a.getColor(R.styleable.LineItenView_liv_bottom_line_color, 0xffcccccc);
        iconRes = a.getResourceId(R.styleable.LineItenView_liv_icon_res, 0);
        title = a.getString(R.styleable.LineItenView_liv_title);
        subTitle = a.getString(R.styleable.LineItenView_liv_sub_title);
        initView(context, a);
        a.recycle();
    }

    private void initView(Context context, TypedArray a) {
        LayoutInflater.from(context).inflate(R.layout.view_line_item, this, true);
        textView = findViewById(R.id.textView);
        subTextView = findViewById(R.id.subTitle);
        imageView = findViewById(R.id.imageView);

        if (a.hasValue(R.styleable.LineItenView_liv_title_size)) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    a.getDimensionPixelSize(R.styleable.LineItenView_liv_title_size, 0));
        }
        if (a.hasValue(R.styleable.LineItenView_liv_title_color)) {
            textView.setTextColor(a.getColor(R.styleable.LineItenView_liv_title_color, 0));
        }
        if (a.hasValue(R.styleable.LineItenView_liv_sub_title_size)) {
            subTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    a.getDimensionPixelSize(R.styleable.LineItenView_liv_sub_title_size, 0));
        }
        if (a.hasValue(R.styleable.LineItenView_liv_sub_title_color)) {
            subTextView.setTextColor(a.getColor(R.styleable.LineItenView_liv_sub_title_color, 0));
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(bottomLineColor);

        if (bottomLineSize > 0) {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom() + bottomLineSize);
        }
        if (isShowArror) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_arrow_right);
            DrawableUtils.createTextDraw(subTextView, drawable)
                    .location(3)
                    .set();
        }
        imageView.setVisibility(isShowIcon ? VISIBLE : GONE);
        if (!isShowIcon) {
            ((LayoutParams) textView.getLayoutParams()).setMargins(0, 0, 0, 0);
        } else {
            ((LayoutParams) textView.getLayoutParams()).setMargins(DimensUtils.dip2px(context, 20), 0, 0, 0);
        }
        imageView.setImageResource(iconRes);
        textView.setText(title);
        subTextView.setText(subTitle);
    }

    public TextView getTitleView() {
        return textView;
    }

    public TextView getSubTitleView() {
        return subTextView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
        textView.setText(title);
    }

    public void setSubTitle(CharSequence title) {
        this.subTitle = title;
        subTextView.setText(title);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bottomLineSize > 0) {
            canvas.drawRect(0, getHeight() - bottomLineSize, getWidth(), getHeight(), paint);
        }
    }


}
