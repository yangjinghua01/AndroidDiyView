package com.rgsc.myapplication.widgetView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.rgsc.myapplication.R;

public class DownLoadProperBar extends View {
    private int outAndInnerborderColor = Color.GRAY;//默认灰色的边框
    private int circularRingWidth = 20;
    private int circularRingBlowColor = Color.WHITE; //默认底色是白色
    private int circularRingAbounColor = Color.BLUE; //默认进度的颜色是蓝色
    private Paint outAndInnerPaint;
    private final int borderWidth = 1;

    public DownLoadProperBar(Context context) {
        this(context, null);
    }

    public DownLoadProperBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownLoadProperBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DownLoadProperBar);
        outAndInnerborderColor = typedArray.getColor(R.styleable.DownLoadProperBar_outAndInnerborderColor, outAndInnerborderColor);
        circularRingBlowColor = typedArray.getColor(R.styleable.DownLoadProperBar_circularRingBlowColor, circularRingBlowColor);
        circularRingAbounColor = typedArray.getColor(R.styleable.DownLoadProperBar_circularRingAbounColor, circularRingAbounColor);
        circularRingWidth = (int) typedArray.getDimension(R.styleable.DownLoadProperBar_circularRingWidth, dip2px(circularRingWidth));
        typedArray.recycle();
        initoutAndInnerPaint();
    }

    private void initoutAndInnerPaint() {
        outAndInnerPaint = new Paint();
        outAndInnerPaint.setAntiAlias(true);
        outAndInnerPaint.setColor(outAndInnerborderColor);
        outAndInnerPaint.setStrokeWidth(dip2px(borderWidth));
        outAndInnerPaint.setStyle(Paint.Style.STROKE);
    }


    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //        只保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //        画内圆形
        int center = getWidth() / 2;
        int padding = getPaddingLeft();
        /**
         *cx：圆心的x坐标。
         * cy：圆心的y坐标。
         * radius：圆的半径。
         * paint：绘制时所使用的画笔。
         */
        canvas.drawCircle(center, center, center - borderWidth - padding, outAndInnerPaint);



//        画内圆环

    }

    private void finish() {

    }

    public void setOutAndInnerborderColor(int outAndInnerborderColor) {
        this.outAndInnerborderColor = outAndInnerborderColor;
    }

    public void setCircularRingWidth(int circularRingWidth) {
        this.circularRingWidth = circularRingWidth;
    }

    public void setCircularRingBlowColor(int circularRingBlowColor) {
        this.circularRingBlowColor = circularRingBlowColor;
    }

    public void setCircularRingAbounColor(int circularRingAbounColor) {
        this.circularRingAbounColor = circularRingAbounColor;
    }
}
