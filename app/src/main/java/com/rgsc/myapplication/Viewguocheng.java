package com.rgsc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Viewguocheng extends Activity {
    private TextView tx;
    private final static String TAG = "DEBUG";
    private TagLayout tag_group;
    private TouchView touchView;
    private List<String> mItems = new ArrayList<>(

    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//活动的启动流程
        setContentView(R.layout.activity_viewguocheng); // --------->setContentView 源码
        tx = findViewById(R.id.textss);
//        流式布局控件实例化
        tag_group = findViewById(R.id.tag_group);
        touchView = findViewById(R.id.touch_id);

        touchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "MainTouch: "+event.getAction());
                return true;
            }
        });
        touchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick:");
            }
        });




        Log.i(TAG, "height1:" + tx.getMeasuredHeight());//高度 0
        tx.post(new Runnable() { //保存到queue 中什么都没干，会在dispatchAttachedToWindow会在测量完毕之后调用，中执行
            @Override
            public void run() {
                Log.i(TAG, "height:2" + tx.getMeasuredHeight());//高度值
            }
        });
//        标签后台获取
        mItems.add("1111");
        mItems.add("11112222");
        mItems.add("3333222");
        mItems.add("1hahf");
        mItems.add("fadsf");
        mItems.add("1111sdafg45");
        mItems.add("3333222");
        mItems.add("1hahf");
        mItems.add("fadsf");
        mItems.add("1111sdafg45");
//        tag_group.addTags(mItems);
        tag_group.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mItems.size();
            }

            @Override
            public View getView(int Position, ViewGroup parent) {
                TextView view = (TextView) LayoutInflater.from(Viewguocheng.this).inflate(R.layout.item_tag, parent, false);
                view.setText(mItems.get(Position));
//                这里可以做交互操作
                return view;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "height:3" + tx.getMeasuredHeight());//高度 0
    }
}
/**
 * 自定义view的套路
 * 1.自定义属性，获取自定义属性（达到配置的效果）
 * 2.onMeasure方法用于测量计算自己的宽高 前提是继承继承自View，如果是继承自己已有的TextView，Button，已经给你计算好了宽高。
 * 3.onDraw 用于绘制自己的显示
 * 4.onTouch 用于与用户交互
 */
/**
 * 自定义ViewGroup的套路
 * 1.自定义属性，获取自定义属性（达到配置的效果）很少有
 * 2.onMeasure（）方法，for循环测量子View，根据子View的宽高来计算自己的宽高。
 * 3.onDraw方法一般是不会调用的。如果需要绘制需要实现dispatchDraw方法
 * 4.onlayout（）方法用来摆放子View。 前提是不是GONE的前提下。
 * 5.很多情况下不会继承自ViewGroup往往继承系统已经提供好的ViewGroup。如ViewPager ScrollView。
 */
/**
 * View的Touch事件分发
 *
 */