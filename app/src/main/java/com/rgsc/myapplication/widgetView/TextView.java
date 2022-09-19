package com.rgsc.myapplication.widgetView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.rgsc.myapplication.R;

public class TextView extends View {
    private String mText;
    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;
    private Paint mPaint;

    //    构造函数会在代码里new的时候调用也就是在活动或者碎片中直接new
    public TextView(Context context) {
        this(context, null);
    }

    //    在布局中使用
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //    在布局中使用layout自定义样式
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        获取类属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextView);
        mText = typedArray.getString(R.styleable.TextView_mytext);
        mTextColor = typedArray.getColor(R.styleable.TextView_mytextColor, mTextColor);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextView_mytextSize, sp2px(mTextSize));

        //        回收
        typedArray.recycle();
        mPaint = new Paint();
//        抗锯齿
        mPaint.setAntiAlias(true);
//        设置画笔的大小和颜色
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    /**
     * 自定义view的测量方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //        布局的宽高都是由这个方法指定
        //        指定控件的宽高
//        获取宽高模式
        //MeasureSpec.AT_MOST :在布局中指定了wrap_content
        //MeasureSpec.EXACTLY:在布局中指定了确切的值
        //MeasureSpec.UNSPECIFIED尽可能的大
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightNMode = MeasureSpec.getMode(heightMeasureSpec);
//        1.确定的值，这个时候不需要计算，给的多少就是多少
        //        2.给的是wrap_content 需要计算

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
//            计算宽度 与字体的长度有关  字体的大小 用画笔来测量
            Rect bounds = new Rect();
//            获取文本的宽度
            mPaint.getTextBounds(mText, 0, mText.length(), bounds);
            width = bounds.width()+getPaddingRight()+getPaddingLeft();
        }
        if (heightNMode == MeasureSpec.AT_MOST){
            Rect rect = new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),rect);
            height = rect.height()+getPaddingTop()+getBottom();
        }
//        设置控件的宽高
        setMeasuredDimension(width,height);


    }

    /**
     * 用于绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        画文本
//        canvas.drawText();
//        画弧形
//        canvas.drawArc();
//        画圆形
//        canvas.drawCircle();
/**
 * 画文字
 * text 要画的文字
 * x 开始的位置
 * y 基线
 */
//dy 代表的是： 高度的一半 baseLine 的距离
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseline = getHeight()/2+dy;
        int x = getPaddingLeft();
        canvas.drawText(mText,x,baseline,mPaint);
    }

    /**
     * 处理事件分发的
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
//            手指抬起
            case MotionEvent.ACTION_UP:
                break;
//                手指按下
            case MotionEvent.ACTION_DOWN:
                break;
//                手指移动
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onTouchEvent(event);
    }
    /**
     * 自定义属性
     * 自定义属性就是用来配置的，Android：text=“yjh”是系统的一个自定义属性
     * （1） 在res下的values下面新建attrs.xml
     * (2)在布局中使用,生命命名空间，然后在自己自定义的view中使用。
     * （3）在自定义的view中获取属性
     */
}
