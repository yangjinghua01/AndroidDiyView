package com.rgsc.myapplication.widgetViewGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.rgsc.myapplication.adapter.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {
    private int childCount;
    private BaseAdapter mAdapter;
    private List<List<View>> mChildViews = new ArrayList<>();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 摆放子view
     *
     */
    // 2.1 onMeasure() 指定宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 清空集合
        mChildViews.clear();

        int childCount = getChildCount();

        // 获取到宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);

        // 高度需要计算
        int height = getPaddingTop() + getPaddingBottom();

        // 一行的宽度
        int lineWidth = getPaddingLeft();

        ArrayList<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);

        // 子View高度不一致的情况下
        int maxHeight = 0;

        for (int i = 0; i < childCount; i++) {
            // 2.1.1 for循环测量子View
            View childView = getChildAt(i);
            // 这段话执行之后就可以获取子View的宽高，因为会调用子View的onMeasure
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            // margin值 ViewGroup.LayoutParams 没有 就用系统的MarginLayoutParams
            // 想想 LinearLayout为什么有？
            // LinearLayout有自己的 LayoutParams  会复写一个非常重要的方法
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();

            // 什么时候需要换行，一行不够的情况下 考虑 margin
            if (lineWidth + (childView.getMeasuredWidth() + params.rightMargin + params.leftMargin) > width) {
                // 换行,累加高度  加上一行条目中最大的高度
                height += maxHeight;
                lineWidth = childView.getMeasuredWidth() + params.rightMargin + params.leftMargin;
                childViews = new ArrayList<>();
                mChildViews.add(childViews);
            } else {
                lineWidth += childView.getMeasuredWidth() + params.rightMargin + params.leftMargin;
                maxHeight = Math.max(childView.getMeasuredHeight() + params.bottomMargin + params.topMargin, maxHeight);
            }

            childViews.add(childView);
        }

        height += maxHeight;

        Log.e("TAG", "width -> " + width + " height-> " + height);
        // 2.1.2 根据子View计算和指定自己的宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 摆放子View
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left, top = getPaddingTop(), right, bottom;
        int maxHeight = 0;
        for (List<View> childViews : mChildViews) {
            left = getPaddingLeft();

            for (View childView : childViews) {
                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                left += params.leftMargin;
                int childTop = top + params.topMargin;
                right = left + childView.getMeasuredWidth();
                bottom = childTop + childView.getMeasuredHeight();
                Log.e("TAG", childView.toString());

                Log.e("TAG", "left -> " + left + " top-> " + childTop + " right -> " + right + " bottom-> " + bottom);

                // 摆放
                childView.layout(left, childTop, right, bottom);
                // left 叠加
                left += childView.getMeasuredWidth() + params.rightMargin;
                int childHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                maxHeight = Math.max(maxHeight, childHeight);
            }
            // 不断的叠加top值
            top += maxHeight;
        }
    }
//    这种方式代码的耦合度太高了
    public void addTags(List<String> mItems) {

    }

    public void setAdapter(BaseAdapter Adapter) {
        if (Adapter==null){
            throw new NullPointerException("未设置设配器");
        }
//        清空所有子view
        removeAllViews();
        mAdapter =null;
        mAdapter =Adapter;
//        获取数量
        int childCount =Adapter.getCount();
        for (int i = 0; i <childCount; i++) {
//            通过位置获取View
            View view = mAdapter.getView(i,this);
            addView(view);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}
//View事件分发的回顾
//onTouchListener(返回false)   --->  onTouchEvent --> OnclickListener
//如果onTouchListener返回true只会执行OnTouchListener
//dispatchTouchEvent（） -> onTouchEvent()


//ViewGroup的事件分发
//dispatchTouchEvent（）源码
//onTouchEvent（） 源码
//onInterceptTouchEvent（） 源码



