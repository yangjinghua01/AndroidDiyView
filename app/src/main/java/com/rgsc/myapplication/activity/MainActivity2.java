package com.rgsc.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.rgsc.myapplication.R;

public class MainActivity2 extends AppCompatActivity {
    private TextView tv_gesture;
    private GestureDetector mGesture; // 声明一个手势检测器对象
    private String desc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();

    }
//    private void initWidget() {
//        tv_gesture = findViewById(R.id.tv_gesture);
//        // 创建一个手势检测器
//        mGesture = new GestureDetector(this, new MyGestureListener());
//    }
    // 定义一个手势检测监听器
    final class MyGestureListener implements GestureDetector.OnGestureListener {

        // 在手势按下时触发
        public final boolean onDown(MotionEvent event) {
            // onDown的返回值没有作用，不影响其它手势的处理
            return true;
        }

        // 在手势飞快掠过时触发
        public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float offsetX = e1.getX() - e2.getX();
            float offsetY = e1.getY() - e2.getY();
            if (Math.abs(offsetX) > Math.abs(offsetY)) { // 水平方向滑动
                if (offsetX > 0) {
                    desc = String.format("%s%s 您向左滑动了一下\n", desc );
                } else {
                    desc = String.format("%s%s 您向右滑动了一下\n", desc);
                }
            } else { // 垂直方向滑动
                if (offsetY > 0) {
                    desc = String.format("%s%s 您向上滑动了一下\n", desc);
                } else {
                    desc = String.format("%s%s 您向下滑动了一下\n", desc);
                }
            }
            tv_gesture.setText(desc);
            return true;
        }

        // 在手势长按时触发
        public final void onLongPress(MotionEvent event) {
            desc = String.format("%s%s 您长按了一下下\n", desc);
            tv_gesture.setText(desc);
        }

        // 在手势滑动过程中触发
        public final boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        // 在已按下但还未滑动或松开时触发
        public final void onShowPress(MotionEvent event) {}

        // 在轻点弹起时触发，也就是点击时触发
        public boolean onSingleTapUp(MotionEvent event) {
            desc = String.format("%s%s 您轻轻点了一下\n", desc);
            tv_gesture.setText(desc);
            // 返回true表示我已经处理了，别处不要再处理这个手势
            return true;
        }
    }

}