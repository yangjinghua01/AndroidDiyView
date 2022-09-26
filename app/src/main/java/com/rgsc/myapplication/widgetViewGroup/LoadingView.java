package com.rgsc.myapplication.widgetViewGroup;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.rgsc.myapplication.R;
import com.rgsc.myapplication.widgetView.ShapeVIew;

/**
 * 58加载数据动画
 */
public class LoadingView extends LinearLayout {
    private ShapeVIew shapeVIew;
    private View shadow;
    private int mTranslationDistance = 0;
    private static final int Animatot_duration = 350;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
        mTranslationDistance = dip2px(70);
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    /**
     * 初始化加载的布局
     */
    private void initLayout() {
//        加载loadingview
//        实例化view
        inflate(getContext(), R.layout.ui_loader, this);
//        添加到该view
        shapeVIew = findViewById(R.id.shap_view);
        shadow = findViewById(R.id.shadow);
        startfallAnimator();
    }

    //开始下落动画
    private void startfallAnimator() {
//        动画作用在谁身上
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(shapeVIew, "translationY", 0, mTranslationDistance);
        translationAnimator.setDuration(Animatot_duration);
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(shadow, "scaleX", 1f, 0.3f);
        scaleAnimator.setDuration(Animatot_duration);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(translationAnimator, scaleAnimator);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
//        监听动画执行完毕
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //                改变形状
                shapeVIew.exchange();
                startUpAnimator();
            }
        });
    }

    private void startUpAnimator() {
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(shapeVIew, "translationY", mTranslationDistance, 0);
        translationAnimator.setDuration(Animatot_duration);
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(shadow, "scaleX", 0.3f, 1f);
        scaleAnimator.setDuration(Animatot_duration);
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new DecelerateInterpolator());
        set.playTogether(translationAnimator, scaleAnimator);
//        监听动画执行完毕
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startfallAnimator();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                startRotationAnimator();
            }
        });
        set.start();
    }

    /**
     * 上抛的时候需要旋转
     */
    private void startRotationAnimator() {
        ObjectAnimator rotationAnimator = null;
        switch (shapeVIew.getmCurrentshape()) {
            case Circle:
                rotationAnimator = ObjectAnimator.ofFloat(shapeVIew, "rotation", mTranslationDistance, 0, 180);
                break;
            case Square:
//                180度
                rotationAnimator = ObjectAnimator.ofFloat(shapeVIew, "rotation", mTranslationDistance, 0, 180);
                break;
            case Triangle:
//                60度
                rotationAnimator = ObjectAnimator.ofFloat(shapeVIew, "rotation", mTranslationDistance, 0, -120);
                break;
        }
        rotationAnimator.setDuration(Animatot_duration);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.start();
    }
}
