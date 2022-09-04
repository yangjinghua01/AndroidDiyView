package com.rgsc.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ShapeVIew extends View {
    private Shape mCurrentshape = Shape.Circle;
    public ShapeVIew(Context context) {
        this(context, null);
    }

    public ShapeVIew(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeVIew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        画正方形

//        画三角形

//        画圆形
    }
    public enum Shape{
        Circle ,Square,Triangle
    }
}
