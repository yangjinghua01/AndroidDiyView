package com.rgsc.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ShapeVIew extends View {
    private Shape mCurrentshape = Shape.Circle;
    private Paint mPaint;
    private Path mPaht;

    public ShapeVIew(Context context) {
        this(context, null);
    }

    public ShapeVIew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeVIew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (mCurrentshape) {
            //        画圆形
            case Circle:
                int center = getWidth() / 2;
                mPaint.setColor(Color.YELLOW);
                canvas.drawCircle(center, center, center, mPaint);
                break;
            //        画正方形
            case Square:
                mPaint.setColor(Color.RED);
                canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
                break;
            //        画三角形
            case Triangle:
                mPaint.setColor(Color.GREEN);
                if (mPaht == null) {
                    mPaht = new Path();
                    mPaht.moveTo(getWidth()/2,0);
                    mPaht.lineTo(0, (float) ((getWidth()/2)*Math.sqrt(3)));
                    mPaht.lineTo(getWidth(), (float) ((getWidth()/2)*Math.sqrt(3)));
                    mPaht.close();
                }
                canvas.drawPath(mPaht, mPaint);
                break;
        }


    }

    public void exchange() {
        switch (mCurrentshape) {
            //        画圆形
            case Circle:
                mCurrentshape = Shape.Square;
                break;
            //        画正方形
            case Square:
                mCurrentshape = Shape.Triangle;
                break;
            //        画三角形
            case Triangle:
//                canvas.d
                mCurrentshape = Shape.Circle;
                break;
        }
        invalidate();
    }

    public enum Shape {
        Circle, Square, Triangle
    }
}
