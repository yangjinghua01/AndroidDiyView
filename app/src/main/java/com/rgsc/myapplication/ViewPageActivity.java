package com.rgsc.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewPageActivity extends AppCompatActivity {
    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private LinearLayout mIndicatorContainer;//变成通用的
    private List<ColorTrackTextView> mIndicators = new ArrayList<>();
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        mIndicatorContainer = findViewById(R.id.indicator_view);
        mViewPager = findViewById(R.id.view_pager);
        initIndicator();
        initViewPager();
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("TAG", "onPageScrolled: " + position);
//                滚动过程中会不断的回调
                ColorTrackTextView left = mIndicators.get(position);
                left.setMdirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setmCurrentProgree(1 - positionOffset);
                try {
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setMdirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setmCurrentProgree(positionOffset);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageSelected(int position) {
//                Log.i("TAG", "onPageSelected: ");
//选中销毁掉
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
//            设置也颜色
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            mIndicatorContainer.addView(colorTrackTextView);
            mIndicators.add(colorTrackTextView);
        }
    }
}