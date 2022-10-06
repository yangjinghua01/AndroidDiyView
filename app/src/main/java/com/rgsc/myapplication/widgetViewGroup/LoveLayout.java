package com.rgsc.myapplication.widgetViewGroup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.rgsc.myapplication.LoveTypeEvaluator;
import com.rgsc.myapplication.R;

import java.util.Random;

public class LoveLayout extends RelativeLayout {
    //    随机数
    private Random mRandom;
    //    图片资源
    private int[] mImageRes;
    private int mWidth, mHeight;
    //    控件宽高
    private int mDrawableWidth, mDrawableHeight;
    private Interpolator[] mInterpolator;

    public LoveLayout(Context context) {
        this(context, null);
    }

    public LoveLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRandom = new Random();
        mImageRes = new int[]{R.drawable.pl_blue, R.drawable.pl_red, R.drawable.pl_yellow};
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.pl_blue);
        mDrawableWidth = drawable.getIntrinsicWidth();
        mDrawableHeight = drawable.getIntrinsicHeight();
        mInterpolator = new Interpolator[]{new AccelerateDecelerateInterpolator(),new AccelerateInterpolator(),new DecelerateInterpolator(),new LinearInterpolator()};

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    /**
     * 点赞的效果
     */
    public void addLove() {
//添加一个imageview在底部
        ImageView loveImg = new ImageView(getContext());
//        设置资源
        loveImg.setImageResource(mImageRes[mRandom.nextInt(mImageRes.length - 1)]);
//        添加在底部中心
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(CENTER_HORIZONTAL);
        loveImg.setLayoutParams(params);
        addView(loveImg);
//        添加效果：有放大和透明度的变化
        AnimatorSet animation = getAnimation(loveImg);
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(loveImg);
            }
        });
        animation.start();
    }

    public AnimatorSet getAnimation(ImageView iv) {
        AnimatorSet allAnimator = new AnimatorSet();
        AnimatorSet innerAnimator = new AnimatorSet();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(iv, "alpha", 0.3f, 1.0f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 0.3f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(iv, "scaleX", 0.3f, 1.0f);
        innerAnimator.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        innerAnimator.setDuration(350);
        allAnimator.playSequentially(innerAnimator, getBezierAnimator(iv));
//        innerAnimator.start();
        return allAnimator;
    }

    private Animator getBezierAnimator(ImageView iv) {
        PointF point0 = new PointF(mWidth / 2, mHeight - mDrawableHeight / 2);
        PointF point1 = getPoint(1);
//        new PointF(mRandom.nextInt(mWidth), mRandom.nextInt(mHeight/2)+mHeight/2);
        PointF point2 = getPoint(2);
//        new PointF(mRandom.nextInt(mWidth), mRandom.nextInt(mHeight/2)+mHeight/2);
        PointF point3 = new PointF(mRandom.nextInt(mWidth), 0);
        LoveTypeEvaluator typeEvaluator = new LoveTypeEvaluator(point1, point2);
        ValueAnimator bezierAnimator = ObjectAnimator.ofObject(typeEvaluator, point0, point3);
        bezierAnimator.setInterpolator(mInterpolator[mRandom.nextInt(mInterpolator.length-1)]);
        bezierAnimator.setDuration(7000);
        bezierAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                iv.setX(pointF.x);
                iv.setY(pointF.y);
                float t = animation.getAnimatedFraction();
                iv.setAlpha(1-t+0.2f);
            }
        });
        return bezierAnimator;
    }

    private PointF getPoint(int index) {
        return new PointF(mRandom.nextInt(mWidth)-mDrawableWidth, mRandom.nextInt(mHeight / 2) + (index - 1) * (mHeight / 2));
    }
}
