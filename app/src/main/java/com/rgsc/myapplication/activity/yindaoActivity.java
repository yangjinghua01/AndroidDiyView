package com.rgsc.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.rgsc.myapplication.R;
import com.rgsc.myapplication.parallax.animation.ParallaxViewPager;
import com.rgsc.myapplication.widgetView.LoadingView;

public class yindaoActivity extends AppCompatActivity {
    //先把布局和fragment创建好
    private ParallaxViewPager parallax_view_pager;
    //查找控件
    private LoadingView loadingview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yindao);
//      findViewByid给ViewPager设置Adapter，意味着代码全部写到Activity来了，封装（自定义）


//        parallax_view_pager = findViewById(R.id.parallax_view_pager);
//        parallax_view_pager.setLayout(getSupportFragmentManager(), new int[]{R.layout.fragment_page_first, R.layout.fragment_page_second, R.layout.fragment_page_third});

//模拟耗时操作
        loadingview = findViewById(R.id.loadingview);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingview.disappear();
            }
        },5000);
    }
}