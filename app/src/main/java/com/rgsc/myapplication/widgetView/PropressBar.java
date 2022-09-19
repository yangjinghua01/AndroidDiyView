package com.rgsc.myapplication.widgetView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.rgsc.myapplication.R;

/**
 * 圆形的进度条
 */
public class PropressBar extends View {
    private int minnerBackground = Color.RED;
    private int mouterBackground = Color.BLUE;
    private int mroundWidth;
    private float mprogressTextsize;
    private int mprogressTextColor = Color.GREEN;
    private Paint mInnerPaint, mOuterPaint;
    private int mMax = 100;
    private int mPropress = 50;
    private Paint mTextPaint;

    public PropressBar(Context context) {
        this(context, null);
    }

    public PropressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PropressBar);
        minnerBackground = typedArray.getColor(R.styleable.PropressBar_innerBackground, minnerBackground);
        mouterBackground = typedArray.getColor(R.styleable.PropressBar_outerBackground, mouterBackground);
        mprogressTextColor = typedArray.getColor(R.styleable.PropressBar_progressTextColor, mprogressTextColor);
        mroundWidth = (int) typedArray.getDimension(R.styleable.PropressBar_roundWidth, dip2px(mroundWidth));
        mprogressTextsize = (int) typedArray.getDimensionPixelSize(R.styleable.PropressBar_roundWidth, sp2px(mprogressTextsize));
        typedArray.recycle();
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(minnerBackground);
        mInnerPaint.setStrokeWidth(mroundWidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mouterBackground);
        mOuterPaint.setStrokeWidth(mroundWidth);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mprogressTextColor);
        mTextPaint.setTextSize(mprogressTextsize);
    }


    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
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
        canvas.drawCircle(center, center, center - mroundWidth / 2, mInnerPaint);
//        画外圆形
        RectF rect = new RectF(0 + mroundWidth / 2, 0 + mroundWidth / 2, getWidth() - mroundWidth / 2, getHeight() - mroundWidth / 2);
        float percent = (float) mPropress / mMax;
        canvas.drawArc(rect, 0, percent * 360, false, mOuterPaint);
//画文字
        String text = percent*100 +"%";
        Rect rect1 = new Rect();
        mTextPaint.getTextBounds(text,0,text.length(),rect1);
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseline = getHeight()/2+dy;
        int dx = (int) (getWidth()/2 - rect1.width()/2);
        canvas.drawText(text,dx,baseline,mTextPaint);
    }

    public synchronized void setmMax(int mMax) {
        if (mMax <0){
            mMax =1;
        }
        this.mMax = mMax;
    }

    public synchronized void setmPropress(int mPropress) {
        if (mPropress <0){
            NullPointerException e = (NullPointerException) new Exception();
            throw e;
        }
        this.mPropress = mPropress;
        invalidate();
    }
}
