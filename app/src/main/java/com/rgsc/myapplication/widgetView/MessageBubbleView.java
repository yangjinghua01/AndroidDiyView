package com.rgsc.myapplication.widgetView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MessageBubbleView extends View {
    private PointF mFixationPoint, mDrafPoin;
    //    拖拽圆的半径
    private int mDragRadius = 10;
    //画笔
    private Paint mPaint;
    private int mFixationRadiusMin = 3;
    private int mFixacttionRadius;
    private int mFixacttionRadiusMax = 7;

    public MessageBubbleView(Context context) {
        this(context, null);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragRadius = dip2px(mDragRadius);
        mFixacttionRadiusMax = dip2px(mFixacttionRadiusMax);
        mFixationRadiusMin = dip2px(mFixationRadiusMin);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);


    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        画两个圆
        if (mDrafPoin == null || mFixationPoint == null) {
            return;
        }
//        画拖拽圆
        canvas.drawCircle(mDrafPoin.x, mDrafPoin.y, mDragRadius, mPaint);
//        画固定圆 有一个初始化大小，而且他的半径是随着距离的增大而减小 小到一定程度就不见了。
        double distance = getDistance(mDrafPoin, mFixationPoint);
        mFixacttionRadius = (int) (mFixacttionRadiusMax - distance / 14);
        if (mFixacttionRadius > mFixationRadiusMin) {
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixacttionRadius, mPaint);
        }

    }

    private double getDistance(PointF point1, PointF pointF2) {
        return Math.sqrt((point1.x - pointF2.x) * (point1.x - pointF2.x) + (point1.y - pointF2.y) * (point1.y - pointF2.y));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                手指按下置顶当前的位置
                float downX = event.getX();
                float downY = event.getY();
                initPoint(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                updatePoint(moveX, moveY);
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        invalidate();
        return true;
    }

    /**
     * 跟新拖动位置
     *
     * @param moveX
     * @param moveY
     */
    private void updatePoint(float moveX, float moveY) {
        mDrafPoin.x = moveX;
        mDrafPoin.y = moveY;
    }

    /**
     * 初始化位置
     *
     * @param downX
     * @param downY
     */
    private void initPoint(float downX, float downY) {
        mFixationPoint = new PointF(downX, downY);
        mDrafPoin = new PointF(downX, downY);
    }
}
