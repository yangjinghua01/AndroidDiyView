package com.rgsc.myapplication.widgetView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

        Path bezeierPath = getBezeierPath();
        if (bezeierPath != null) {
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixacttionRadius, mPaint);
            canvas.drawPath(bezeierPath, mPaint);
        }

    }

    /**
     * 获取贝瑟尔路径
     *
     * @return
     */
    private Path getBezeierPath() {
        double distance = getDistance(mDrafPoin, mFixationPoint);
        mFixacttionRadius = (int) (mFixacttionRadiusMax - distance / 14);
        if (mFixacttionRadius < mFixationRadiusMin) {
//            超过一定距离贝瑟尔和固定圆就不画了
            return null;
        }
        Path bezeierPath = new Path();
//        求角
//        求斜率
        float dy = (mDrafPoin.y - mFixationPoint.y);
        float dx = (mDrafPoin.x - mFixationPoint.x);
        float tanA = dy / dx;
        double arcTanA = Math.atan(tanA);
//        p0
        float p0x = (float) (mFixationPoint.x + mFixacttionRadius * Math.sin(arcTanA));
        float p0y = (float) (mFixationPoint.y - mFixacttionRadius * Math.cos(arcTanA));
//        p1
        float p1x = (float) (mDrafPoin.x + mDragRadius * Math.sin(arcTanA));
        float p1y = (float) (mDrafPoin.y - mDragRadius * Math.cos(arcTanA));
        //        p2
        float p2x = (float) (mDrafPoin.x - mDragRadius * Math.sin(arcTanA));
        float p2y = (float) (mDrafPoin.y + mDragRadius * Math.cos(arcTanA));
//        p3
        float p3x = (float) (mFixationPoint.x - mDragRadius * Math.sin(arcTanA));
        float p3y = (float) (mFixationPoint.y + mDragRadius * Math.cos(arcTanA));
//        拼装贝瑟尔的曲线路径
        bezeierPath.moveTo(p0x, p0y);
//        两个点 第一个点（控制点）
        PointF controlPoint = getcontrolPoint();
        bezeierPath.quadTo(controlPoint.x, controlPoint.y, p1x, p1y);
//        画第二条
        bezeierPath.lineTo(p2x, p2y);
        bezeierPath.quadTo(controlPoint.x, controlPoint.y, p3x, p3y);
        bezeierPath.close();
        return bezeierPath;
    }

    /**
     * 控制点
     *
     * @return
     */
    private PointF getcontrolPoint() {
        return new PointF((mDrafPoin.x + mFixationPoint.x) / 2, (mDrafPoin.y + mFixationPoint.y) / 2);
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
