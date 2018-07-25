package com.ashlikun.baseproject.libcore.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ashlikun.baseproject.libcore.R;
import com.ashlikun.glideutils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者　　: 李坤
 * 创建时间:2017/8/27 0027　21:12
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：学校详情的图片展示
 */

public class ImageSchoolView extends ViewGroup {
    ArrayList<String> listDatas = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();

    public ImageSchoolView(Context context) {
        this(context, null);
    }

    public ImageSchoolView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSchoolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width / 2;
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            int left = 0, top = 0, right = 0, bottom = 0;
            ImageView childrenView = (ImageView) getChildAt(i);
            if (i == 0) {
                right = getMeasuredWidth() / 2;
                bottom = getMeasuredHeight();
            } else {
                left = getMeasuredWidth() / 2 + ((i + 1) % 2) * (getMeasuredWidth() / 4);
                top = getMeasuredHeight() / 2 * (i / 3);
                right = left + getMeasuredWidth() / 4;
                bottom = top + getMeasuredHeight() / 2;
            }
            childrenView.layout(left, top, right, bottom);
        }
    }

    public void setData(List<String> datas) {
        if (datas == null || datas.isEmpty()) {
            listDatas.clear();
            removeAllViews();
            return;
        }
        listDatas.clear();
        listDatas.addAll(datas);
        addImageView();

    }

    private void addImageView() {
        removeAllViews();
        for (int i = 0; i < listDatas.size(); i++) {
            ImageView image = getImageView(i);
            GlideUtils.show(image, listDatas.get(i));
            addView(image, generateDefaultLayoutParams());
        }
    }

    /**
     * 获得 ImageView 保证了 ImageView 的重用
     */
    private ImageView getImageView(final int position) {
        ImageView imageView;
        if (position < imageViews.size()) {
            imageView = imageViews.get(position);
        } else {
            imageView = new ImageView(getContext());
            imageView.setImageResource(R.color.gray_ee);
            imageViews.add(imageView);
        }
        imageView.setAdjustViewBounds(false);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        LayoutParams params = super.generateDefaultLayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.MATCH_PARENT;
        return params;
    }
}
