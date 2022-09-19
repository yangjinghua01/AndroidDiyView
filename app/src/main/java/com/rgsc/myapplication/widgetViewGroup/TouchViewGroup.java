package com.rgsc.myapplication.widgetViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TouchViewGroup extends LinearLayout {
    public TouchViewGroup(Context context) {
        super(context);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    //事件拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    //时间触摸
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
