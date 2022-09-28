package com.rgsc.myapplication.widgetViewGroup;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ListDataScreenView extends LinearLayout {
    //用来创建头部用来存放Tab
    private LinearLayout mMenuTabView;
    //
    private Context context;
    //创建FrameLayout用来存放阴影
    private FrameLayout mMenuMiddleView;
    //阴影
    private View mShadowView;
    //    阴影颜色
    private int mShadowColor = Color.parseColor("#999999");
    //    用来存放菜单内容
    private FrameLayout mMenuContainerView;

    public ListDataScreenView(Context context) {
        this(context, null);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListDataScreenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initLayout();
    }

    /**
     * 实例化好布局
     */
    private void initLayout() {
//        1创建头部
        mMenuTabView = new LinearLayout(context);
        mMenuTabView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMenuTabView);
//        创建frameLayout 用来存放阴影（view）+菜单布局
        mMenuMiddleView = new FrameLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight =1;
        mMenuTabView.setLayoutParams(layoutParams);
        addView(mMenuMiddleView);
//        创建阴影不用设置LayoutParams
        mShadowView = new View(context);
        mShadowView.setBackgroundColor(mShadowColor);
        mMenuMiddleView.addView(mShadowView);
//        创建菜单用来存放菜单内容
        mMenuContainerView = new FrameLayout(context);
        mMenuContainerView.setBackgroundColor(Color.GRAY);
        mMenuMiddleView.addView(mMenuContainerView);

    }
}
