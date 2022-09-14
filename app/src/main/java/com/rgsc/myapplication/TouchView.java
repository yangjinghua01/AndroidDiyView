package com.rgsc.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TouchView extends View {
    private final static String TAG = "DEBUG";
    public TouchView(Context context) {
        super(context);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "View onTouchEvent: "+event.getAction());
        return super.onTouchEvent(event);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return true;
    }
}
/**
 * 现象 onTouchListener  ---> OnTouch --->OnClickListener 三个都有的情况下 前提是OnTouchListener返回的是false
 * onTouchListener.DOWN ---->OnTouch.DOWN --->OnTOuchListener
 * -->OnTouch.MOVE -->OnTouchListener.UP --->OnTouch.Up
 *现象 onTouchListener  ---> OnTouch --->OnClickListener 三个都有的情况下 前提是OnTouchListener返回的是ture
 * OnTouch.DOWN --->OnTOuchListener -->OnTouch.MOVE -->OnTouchListener.UP
 *
 * dispatchTouchEvent事件分发
 *          boolean result = false;
 *          ListenerInfo li = mListenerInfo;
 *          ListenerInfo：存放了关于View的所有Listener信息如OnTouchListener   onclickListener
 *           if (li != null && li.mOnTouchListener != null
 *                     && (mViewFlags & ENABLED_MASK) == ENABLED//是否是可用的。
 *                     && li.mOnTouchListener.onTouch(this, event)) { //如果返回false reslut = false 如果返回的是true result = true
 *                 result = true;
 *             }
 *
 *             if (!result && onTouchEvent(event)) { //如果result = false 就会执行onTouchEvent 如果result是true就不会执行OnTouchEvent事件
 *                 result = true;
 *             }
 *             //返回了
 *             return result;
 *点击事件是在View的OnTouchEvent里面的。
 * case MotionEvent.ACTION_UP: 里面调用了  PerformClick(); 调用点击事件 li.mOnClickListener.onClick(this);
 *
 * onTouch（一般都会被我们复写）
 *
 */