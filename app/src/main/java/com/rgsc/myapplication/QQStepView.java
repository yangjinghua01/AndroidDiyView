package com.rgsc.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class QQStepView extends View {
    private Paint mPaint;
    private int mOuterColor;
    private int minnerColor;
    private int mBorerWidth=90;
    private int mstepTextSize;
    private int mstepTextColor = Color.RED;
    //    总共的
    private int mStepMax;
    private int mCurrentStep;
    private Paint mPaintInner;
    private Paint mTextPaint;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        1.分析效果
//        2.确定自定义属性，编写attrs.xml
//        3.在布局中使用
//        4.在自定义view中获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.QQStepView); //这里是两个参数
        mOuterColor = typedArray.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        minnerColor = typedArray.getColor(R.styleable.QQStepView_innerColor, minnerColor);
        mstepTextColor = typedArray.getColor(R.styleable.QQStepView_stepTextColor, mstepTextColor);
        mBorerWidth = (int) typedArray.getDimension(R.styleable.QQStepView_borerWidth, mBorerWidth);
        mstepTextSize = (int) typedArray.getDimension(R.styleable.QQStepView_stepTextSize, mstepTextSize);

        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mBorerWidth);
        mPaint.setColor(mOuterColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);//画笔实心
        mPaintInner = new Paint();
        mPaintInner.setAntiAlias(true);
        mPaintInner.setStrokeWidth(mBorerWidth);
        mPaintInner.setColor(minnerColor);
        mPaintInner.setStrokeCap(Paint.Cap.ROUND);
        mPaintInner.setStyle(Paint.Style.STROKE);//画笔实心
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mstepTextColor);
        mTextPaint.setTextSize(mstepTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        调用者在布局文件中可能 wrap_content
//        获取模式
//        宽度不一致 取最小值 确保正方形
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width > height ? height : width, height > width ? width : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        6.画外圆弧
        int center = getWidth() / 2;
        int radius = getWidth() / 2 - mBorerWidth;
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(rectF, 135, 270, false, mPaint);
//       7.画内圆弧
        float sweepAngle  = (float) mCurrentStep / mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle*270, false, mPaintInner);
//        8画文字
        if (mStepMax== 0)return;
        String stepText = String.valueOf(mCurrentStep);
        Rect rect = new Rect();
        mTextPaint.getTextBounds(stepText,0,stepText.length(),rect);
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseline = getHeight()/2+dy;
        int dx = getWidth()/2 - rect.width()/2;
        canvas.drawText(stepText,dx,baseline,mTextPaint);
    }

    public void setmStepMax(int mStepMax) {
        this.mStepMax = mStepMax;
    }

    public void setmCurrentStep(int mCurrentStep) {
        this.mCurrentStep = mCurrentStep;
//        不断绘制
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
