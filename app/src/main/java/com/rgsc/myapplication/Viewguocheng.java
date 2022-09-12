package com.rgsc.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Viewguocheng extends Activity {
    private TextView tx;
    private final static String TAG = "DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//活动的启动流程
        setContentView(R.layout.activity_viewguocheng); // --------->setContentView 源码
        tx = findViewById(R.id.textss);
        Log.i(TAG, "height1:" + tx.getMeasuredHeight());//高度 0
        tx.post(new Runnable() { //保存到queue 中什么都没干，会在dispatchAttachedToWindow会在测量完毕之后调用，中执行
            @Override
            public void run() {
                Log.i(TAG, "height:2" + tx.getMeasuredHeight());//高度值
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "height:3" + tx.getMeasuredHeight());//高度 0
    }
}