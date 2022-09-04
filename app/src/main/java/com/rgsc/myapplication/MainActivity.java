package com.rgsc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private ColorTrackTextView color_text_trantion;
    private QQStepView qqStepView;
    private Button btn;
    private PropressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        qqStepView.setmStepMax(4000);
//        属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 2000);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                qqStepView.setmCurrentStep((int) animatedValue);
            }
        });
        valueAnimator.start();
    }

    private void initview() {
        qqStepView = findViewById(R.id.stepview);
        color_text_trantion = findViewById(R.id.color_text_trantion);
        btn = findViewById(R.id.jump);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ViewPageActivity.class);
                startActivity(i);
            }
        });
        progress_bar = findViewById(R.id.progress_bar);
        //        属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 2000);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                progress_bar.setmPropress((int)( animatedValue/20));
            }
        });
        valueAnimator.start();
    }


    public void RighttoLeft(View view) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                color_text_trantion.setMdirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                color_text_trantion.setmCurrentProgree(animatedValue);
            }
        });
        valueAnimator.start();
    }

    public void LefttoRight(View view) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                color_text_trantion.setMdirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                color_text_trantion.setmCurrentProgree(animatedValue);
            }
        });
        valueAnimator.start();
    }
}