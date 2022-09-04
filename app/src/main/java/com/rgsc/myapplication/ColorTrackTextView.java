package com.rgsc.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ColorTrackTextView extends androidx.appcompat.widget.AppCompatTextView {
    //    1.实现一个文字两种颜色 - 绘制不变色字体的画笔
    private Paint mOriginPaint;
    //    2.实现一个文字两种颜色的 -绘制变色字体的画笔
    private Paint mChangePaint;
    //    3.实现一个文字两种颜色 - 当前的进度
    private float mCurrentProgree = 0.2f;
    //    朝向
    private Direction mdirection = Direction.LEFT_TO_RIGHT;
    private int originColor, changeColor;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        originColor = typedArray.getColor(R.styleable.ColorTrackTextView_originColor, getTextColors().getDefaultColor());
        changeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor());
        mOriginPaint = getpaintColor(originColor);
        mChangePaint = getpaintColor(changeColor);
        typedArray.recycle();

    }

    /**
     * 初始画笔
     *
     * @param
     */
    private Paint getpaintColor(int color) {
        Paint paint = new Paint();
//        为画笔设置颜色
        paint.setColor(color);
//        设置抗锯齿
        paint.setAntiAlias(true);
//        防抖动
        paint.setDither(true);
//        设置字体大小就是TextView的字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    //1.一个文字两种颜色
//利用clipRect 的api可以裁剪 左边用一个画笔去画 右边用另一个画笔去画    不断的改变中间值
    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        注释super自己花
//        canvas.clipRect()   clipRec表示裁剪区域
//        根据进度吧中间值算出来
        int middle = (int) (mCurrentProgree * getWidth());
        if (mdirection == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, mChangePaint, 0, middle);
//绘制变色的
            drawText(canvas, mOriginPaint, middle, getWidth());
        } else {
            drawText(canvas, mChangePaint, getWidth() - middle, getWidth());
            drawText(canvas, mOriginPaint, 0, getWidth() - middle);
        }
    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save(); //保存画布
//        绘制不变色的
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);

        String text = getText().toString();
//        获取字体的宽度
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
//        基线
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseline = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseline, paint);//这么画还是只有一种颜色
        canvas.restore(); // 释放画布
    }

    public void setMdirection(Direction mdirection) {
        this.mdirection = mdirection;
    }

    public void setmOriginPaint(Paint mOriginPaint) {
        this.mOriginPaint = mOriginPaint;
    }

    public void setmChangePaint(Paint mChangePaint) {
        this.mChangePaint = mChangePaint;
    }

    public void setmCurrentProgree(float mCurrentProgree) {
        this.mCurrentProgree = mCurrentProgree;
        invalidate();
    }

    public void setOriginColor(int originColor) {
        this.originColor = originColor;
    }

    public void setChangeColor(int changeColor) {
        this.mChangePaint.setColor(changeColor);
    }
}
