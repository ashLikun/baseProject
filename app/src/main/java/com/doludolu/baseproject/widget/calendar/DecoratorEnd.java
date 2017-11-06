package com.doludolu.baseproject.widget.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.List;

import static com.ashlikun.utils.other.DimensUtils.dip2px;

/**
 * 作者　　: 李坤
 * 创建时间: 2017/8/31　16:27
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：开始和结束标记
 */

public class DecoratorEnd implements DayViewDecorator, LineBackgroundSpan {
    protected Context context;
    private String showText;
    List<CalendarDay> selectData;
    private int textHeigth;
    private int padding;
    private int textSize;

    public DecoratorEnd(Context context) {
        this.context = context;
        textHeigth = dip2px(context, 20);
        padding = dip2px(context, 2);
        textSize = dip2px(context, 14);
    }

    /**
     * * 参数简介：坐标系是文本显示的横向区域
     * 即：文本横向是充满父区域，高度自适应
     * 所以：原点是：中间显示区域的左上角
     */
    //绘制上班
    @Override
    public void drawBackground(Canvas c, Paint p,
                               int left, int right, int top, int baseline, int bottom,
                               CharSequence text, int start, int end, int lnum) {

        int size = right - left;//正方形大小
        //移动原点到正方形左上角
        c.save();
        c.translate(0, (-(size - bottom) / 2));
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        float textWidth = paint.measureText(showText, 0, 2) + padding * 2;
        float startX = size - textWidth;
        float startY = size - textHeigth;

        paint.setColor(Color.parseColor("#FFFF4081"));
        RectF rectF = new RectF(startX, startY, startX + textWidth, startY + textHeigth);
        c.drawRoundRect(rectF, padding * 2, padding * 2, paint);

        paint.setColor(Color.WHITE);
        c.drawColor(Color.parseColor("#22212121"));//背景色
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();//文字的4根线，baseLine为0，top为负值， bottom为正数
        int baselineText = (textHeigth - fontMetrics.top - fontMetrics.bottom) / 2;
        c.drawText(showText, startX + padding, startY + baselineText, paint);
        c.restore();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (selectData != null) {
            if (selectData.size() >= 2) {
                return isStart(day);
            }
        }
        return false;
    }

    private boolean isStart(CalendarDay day) {
        CalendarDay start = selectData.get(0);
        for (int i = 1; i < selectData.size(); i++) {
            if (selectData.get(i).isAfter(start)) {
                start = selectData.get(i);
            }
        }
        if (day.equals(start)) {
            showText = "结束";
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(this);
    }

    public void setSelectData(List<CalendarDay> selectData) {
        this.selectData = selectData;
    }
}