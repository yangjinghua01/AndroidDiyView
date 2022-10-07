package com.rgsc.myapplication.parallax.animation;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.rgsc.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ParallaxViewPager extends ViewPager {
    private List<ParallaxFragment> mParallaxFragments;

    public ParallaxViewPager(@NonNull Context context) {
        this(context, null);
    }

    public ParallaxViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mParallaxFragments = new ArrayList<>();
    }

    /**
     * 设置布局数组
     *
     * @param layoutIds
     */
    public void setLayout(FragmentManager fm, int[] layoutIds) {
        mParallaxFragments.clear();
//        实例化fragment
        for (int layoutId : layoutIds) {
            ParallaxFragment fragment = new ParallaxFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY, layoutId);
            fragment.setArguments(bundle);
            mParallaxFragments.add(fragment);
        }
//        设置我们的viewpage适配器
        setAdapter(new ParallaxPageAdapter(fm));
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                滚动
                ParallaxFragment outFragment = mParallaxFragments.get(position);
                List<View> parallaxViews = outFragment.getParallaxViews();
                for (View parallaxView : parallaxViews) {
                    ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                    parallaxView.setTranslationX((-positionOffsetPixels) * tag.translationXOut);
                    parallaxView.setTranslationY((-positionOffsetPixels) * tag.translationYOut);
                }
                try {
                    ParallaxFragment inFragment = mParallaxFragments.get(position + 1);
                    parallaxViews = inFragment.getParallaxViews();
                    for (View parallaxView : parallaxViews) {
                        ParallaxTag tag = (ParallaxTag) parallaxView.getTag(R.id.parallax_tag);
                        parallaxView.setTranslationX((getMeasuredWidth() - positionOffsetPixels) * tag.translationXIn);
                        parallaxView.setTranslationY((getMeasuredWidth() - positionOffsetPixels) * tag.translationYIn);
                    }
                } catch (Exception e) {
                    e.toString();
                }
            }

            @Override
            public void onPageSelected(int position) {
//滚动到那个了
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ParallaxPageAdapter extends FragmentPagerAdapter {

        public ParallaxPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mParallaxFragments.get(position);
        }

        @Override
        public int getCount() {
            return mParallaxFragments.size();
        }
    }
}
