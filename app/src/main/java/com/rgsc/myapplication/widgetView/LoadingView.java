package com.rgsc.myapplication.widgetView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.rgsc.myapplication.R;

public class LoadingView extends View {
    //    旋转动画执行的时间
    private final long ROTARTION_ANIMATION_TIME = 1000;
    //    当前大圆旋转的角度
    private float mCuttentRotationAngle = 0F;
    //    小圆的颜色列表
    private int[] mCircColors;
    //    大圆里面包含很多个小圆的半径 - 整宽度的1/4
    private float mRotationRadius;
    //   每个小圆的半径 - 大圆半径的1/8
    private float mCircleRadius;
    //画笔
    private Paint mPaint;
    //    中心点
    private int mCenterX, mCenterY;
    //    整体的颜色背景
    private int mSolashColor = Color.WHITE;
    //当前动画状态
    private LoadingState mLoadingState;
    //    当前大圆的半径
    private float mcurrentRotationRadius = mRotationRadius;
    //    空心圆的半径
    private float mHoleRadius = 0F;
    //    屏幕的一半对角线
    private float mDiagonalDist;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        获得小圆的颜色列表
        mCircColors = getContext().getResources().getIntArray(R.array.splash_circle_colors);
    }

    private boolean mInitParams = false;

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mInitParams) {
            initParams();
        }
        if (mLoadingState == null) {
            mLoadingState = new RotationState();
        }
        mLoadingState.draw(canvas);
    }

    private void initParams() {
        mRotationRadius = getMeasuredWidth() / 4;
        mCircleRadius = mRotationRadius / 8;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mCenterX = getMeasuredWidth() / 2;
        mCenterY = getMeasuredHeight() / 2;
        mDiagonalDist = (float) Math.sqrt(mCenterX * mCenterX + mCenterY * mCenterY);
        mInitParams = true;
    }


    private void drawRotationAnimator(Canvas canvas) {
        canvas.drawColor(mSolashColor);
        double percentAngle = Math.PI * 2 / mCircColors.length;
        for (int i = 0; i < mCircColors.length; i++) {
            mPaint.setColor(mCircColors[i]);
            double currentAngle = percentAngle * i + mCuttentRotationAngle;
            int cx = (int) (mCenterX + mRotationRadius * Math.cos(currentAngle));
            int cy = (int) (mCenterY + mRotationRadius * Math.sin(currentAngle));
            canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
        }
    }

    /**
     * 消失
     */
    public void disappear() {
//        开始聚合
//        关闭动画
        if (mLoadingState instanceof RotationState) {
            RotationState rotationState = (RotationState) mLoadingState;
            rotationState.cancle();
            mLoadingState = new MergeState();
        }

    }

    public abstract class LoadingState {
        public abstract void draw(Canvas canvas);

        public abstract void cancle();
    }

    /**
     * 旋转动画
     */
    public class RotationState extends LoadingState {
        private ValueAnimator valueAnimator;

        public RotationState() {
            //        定义变量 不断的去改变 用属性动画 旋转0 到 360
            if (valueAnimator == null) {
                valueAnimator = ObjectAnimator.ofFloat(0f, 2 * (float) Math.PI);
                valueAnimator.setDuration(ROTARTION_ANIMATION_TIME);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mCuttentRotationAngle = (float) animation.getAnimatedValue();
//                重新绘制
                        invalidate();
                    }
                });
//        不断的反复执行
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.setRepeatCount(-1);
                valueAnimator.start();
            }

        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mSolashColor);
            double percentAngle = Math.PI * 2 / mCircColors.length;
            for (int i = 0; i < mCircColors.length; i++) {
                mPaint.setColor(mCircColors[i]);
                double currentAngle = percentAngle * i + mCuttentRotationAngle;
                int cx = (int) (mCenterX + mRotationRadius * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mRotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }
        }

        @Override
        public void cancle() {
            valueAnimator.cancel();
        }
    }

    /**
     * 聚合动画
     */
    public class MergeState extends LoadingState {
        private ValueAnimator valueAnimator;

        public MergeState() {
            valueAnimator = ObjectAnimator.ofFloat(mRotationRadius, 0);
            valueAnimator.setDuration(ROTARTION_ANIMATION_TIME / 2);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mcurrentRotationRadius = (float) animation.getAnimatedValue();
//                重新绘制
                    invalidate();
                }
            });
//        不断的反复执行
            valueAnimator.setInterpolator(new AnticipateInterpolator(3f));
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mLoadingState = new ExpandState();
                }
            });
            valueAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(mSolashColor);
            double percentAngle = Math.PI * 2 / mCircColors.length;
            for (int i = 0; i < mCircColors.length; i++) {
                mPaint.setColor(mCircColors[i]);
                double currentAngle = percentAngle * i + mCuttentRotationAngle;
                int cx = (int) (mCenterX + mcurrentRotationRadius * Math.cos(currentAngle));
                int cy = (int) (mCenterY + mcurrentRotationRadius * Math.sin(currentAngle));
                canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
            }
        }

        @Override
        public void cancle() {

        }
    }

    /**
     * 展开动画
     */
    public class ExpandState extends LoadingState {
        private ValueAnimator valueAnimator;

        public ExpandState() {
            valueAnimator = ObjectAnimator.ofFloat(0, mDiagonalDist);
            valueAnimator.setDuration(ROTARTION_ANIMATION_TIME / 2);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
//        不断的反复执行
            valueAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            float strokeWidth = mDiagonalDist - mHoleRadius;
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setColor(mSolashColor);
            mPaint.setStyle(Paint.Style.STROKE);
            float radius = strokeWidth / 2 + mHoleRadius;
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);

        }

        @Override
        public void cancle() {

        }
    }
}
