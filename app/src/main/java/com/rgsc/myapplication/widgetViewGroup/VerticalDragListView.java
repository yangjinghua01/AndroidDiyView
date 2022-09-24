package com.rgsc.myapplication.widgetViewGroup;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class VerticalDragListView extends FrameLayout {
    private ViewDragHelper mViewDragHelper;
    private final String TAG = "DEBUG";
    private View mDragListView;
    private int mMenuHeight;
    private boolean mMenuisOpen = false;

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, mViewDragHelperCallBack);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        Log.i(TAG, "onTouchEvent: " + event.getAction());
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("使用此控件需要两个子View");
        }
        mDragListView = getChildAt(1);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        mMenuHeight = getChildAt(0).getHeight();
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mMenuHeight = getChildAt(0).getHeight();
        }
    }

    //    拖动子view
    private ViewDragHelper.Callback mViewDragHelperCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
//        指定那个子View可以拖动 就是child
            return mDragListView == child;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
//            垂直拖动的位置
            if (top <= 0) {
                top = 0;
            }
            if (top >= mMenuHeight) {
                return mMenuHeight;
            }
            return top;
        }

//        @Override
//        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
////            水平拖动的位置
//            return left;
//        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mDragListView.getTop() > mMenuHeight / 2 && releasedChild == mDragListView) {
//                滚动到菜单的高度（打开）
                mViewDragHelper.settleCapturedViewAt(0, mMenuHeight);
                mMenuisOpen = true;
            } else {
//                滚动到0的位置 （关闭）
                mViewDragHelper.settleCapturedViewAt(0, 0);
                mMenuisOpen = false;
            }
            invalidate();
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private float mDownY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (mMenuisOpen) {
            return true;
        }
//        向下滑动拦截不给listview处理
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                mViewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                if ((moveY - mDownY) > 0&&!canChildScrollUp()) {
//                    向上滑动拦截不让listview做处理
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
    public boolean canChildScrollUp(){
        if (Build.VERSION.SDK_INT<14){
            if (mDragListView instanceof AbsListView){
                final AbsListView absListView = (AbsListView) mDragListView;
                return absListView.getChildCount()>0&&absListView.getChildAt(0).getTop()<absListView.getPaddingTop();
            }else {
                return ViewCompat.canScrollVertically(mDragListView,-1)||mDragListView.getScrollX()>0;
            }
        }else {
            return ViewCompat.canScrollHorizontally(mDragListView,-1);
        }
    }
}
