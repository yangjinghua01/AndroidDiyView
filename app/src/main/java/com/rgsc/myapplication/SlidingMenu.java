package com.rgsc.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.core.view.ViewCompat;

public class SlidingMenu extends HorizontalScrollView {
    private Context mContext;
    //    菜单的宽度
    private int mMenuWidth;
    private View menuView, contentView;
//    快速滑动处理
    private GestureDetector gestureDetector;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        float rightMargin = typedArray.getDimension(R.styleable.SlidingMenu_menuRightMargin, ScreenUtils.dip2px(mContext, 50));
        mMenuWidth = (int) (ScreenUtils.getScreenWidth(mContext) - rightMargin);
        typedArray.recycle();
    }

    //    1.宽度不对  指定宽高
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //这个方法是布局加载完毕才会执行的方法
//        获取Linearlayout
        ViewGroup contianer = (ViewGroup) getChildAt(0);
        int childCount = contianer.getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("必须是两个布局");
        }
        menuView = contianer.getChildAt(0);
        ViewGroup.LayoutParams menuParams = menuView.getLayoutParams();
        menuParams.width = mMenuWidth;
        menuView.setLayoutParams(menuParams);
        contentView = contianer.getChildAt(1);
        ViewGroup.LayoutParams contentParams = contentView.getLayoutParams();
        contentParams.width = ScreenUtils.getScreenWidth(getContext());
        contentView.setLayoutParams(contentParams);
//        指定宽高内容页的宽度是屏幕的宽度
//        菜单页的宽度是屏幕的宽度 - 右边的一小部分距离（自定义属性）
    }
//    处理右边的缩放，左边的缩放和透明度


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("DEBUG", "L:--> " + l + ",T:-->" + t + ",oldl:-->" + oldl + ",oldt:-->" + oldt);
//        算一个梯度值
        float scale = 1f * l / mMenuWidth;
//        右边的缩放 最小是0.7f，最大是1f
        float rightScale = 0.6f + 0.4f * scale;
//        设置右边的缩放
//        缩放的中心点位置
        ViewCompat.setPivotX(contentView,0);
        ViewCompat.setPivotY(contentView,contentView.getMeasuredHeight()/2);
        ViewCompat.setScaleX(contentView,rightScale);
        ViewCompat.setScaleY(contentView,rightScale);
//        菜单是 半透明到完全透明
//        缩放 0.7f 到 1f
        float leftAlphla = 0.3f+ (1-scale)*0.7f;
        ViewCompat.setAlpha(menuView,leftAlphla);
        float leftScale = 0.7f+(1-scale)*0.3f;
        ViewCompat.setScaleX(menuView,leftScale);
        ViewCompat.setScaleY(menuView,leftScale);
        ViewCompat.setTranslationX(menuView,(l*0.25f));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(mMenuWidth, 0);
    }
//    手指抬起二选一要么关闭要么打开

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            手指抬起根据当前滚动的距离来判断
            int currentScrollX = getScrollX();
            if (currentScrollX > mMenuWidth / 2) {
//                关闭菜单
                closeMenu();
            } else {
//                打开菜单
                openMenu();
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单 滚动到0 的位置
     */
    private void openMenu() {
        smoothScrollTo(0, 0);
    }

    /**
     * 关闭菜单
     * 滚动到mMenuWidth的位置
     */
    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
    }

}
