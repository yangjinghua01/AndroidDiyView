package com.rgsc.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.rgsc.myapplication.widgetView.ColorTrackTextView;
import com.rgsc.myapplication.widgetView.PropressBar;
import com.rgsc.myapplication.widgetView.QQStepView;
import com.rgsc.myapplication.R;
import com.rgsc.myapplication.widgetView.ShapeVIew;


public class MainActivity extends AppCompatActivity {
    private ColorTrackTextView color_text_trantion;
    private QQStepView qqStepView;
    private Button btn;
    private PropressBar progress_bar;
    private ShapeVIew shap_view;

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
        shap_view = findViewById(R.id.shap_view);
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
                progress_bar.setmPropress((int) (animatedValue / 20));
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

    public void exchange(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shap_view.exchange();
                        }
                    });
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}