package com.rgsc.myapplication.widgetViewGroup;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.rgsc.myapplication.adapter.BaseMenuAdapter;

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
    private BaseMenuAdapter menuAdapter;

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
        setOrientation(VERTICAL);
//        1创建头部
        mMenuTabView = new LinearLayout(context);
        mMenuTabView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mMenuTabView);
//        创建frameLayout 用来存放阴影（view）+菜单布局
        mMenuMiddleView = new FrameLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight = 1;
        mMenuMiddleView.setLayoutParams(layoutParams);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mmenuContainerHeight = (int) (height*0.75/100);
        ViewGroup.LayoutParams layoutParams = mMenuContainerView.getLayoutParams();
        layoutParams.height = mmenuContainerHeight;
        mMenuContainerView.setLayoutParams(layoutParams);
    }

    /**
     * 设置Adapter
     *
     * @param menuAdapter
     */
    public void setMenuAdapter(BaseMenuAdapter menuAdapter) {
        if (menuAdapter == null) {
            throw new NullPointerException("设配器为空");
        }
        this.menuAdapter = menuAdapter;
        int count = menuAdapter.getCount();
        for (int i = 0; i < count; i++) {
//            获取tab
            View tabView = menuAdapter.getTabView(i, mMenuTabView);
            mMenuTabView.addView(tabView);
            LinearLayout.LayoutParams params = (LayoutParams) tabView.getLayoutParams();
            params.weight =1;
            tabView.setLayoutParams(params);
//            菜单的内容
            View menuView = menuAdapter.getMenuView(i, mMenuContainerView);
            menuView.setVisibility(GONE);
            mMenuContainerView.addView(menuView);

        }
    }
}
