package com.rgsc.myapplication;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureListener implements GestureDetector.OnGestureListener {
    @Override
    public boolean onDown(MotionEvent e) {
//        用户按下屏幕就会触发；
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
//        如果是按下的时间超过瞬间，而且在按下的时候没有松开或者是拖动的，那么onShowPress就会执行。
    }

    /**
     * 触发顺序：
     * 点击一下非常快的（不滑动）Touchup：
     * onDown->onSingleTapUp->onSingleTapConfirmed
     * 点击一下稍微慢点的（不滑动）Touchup：
     * onDown->onShowPress->onSingleTapUp->onSingleTapConfirmed
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
//        从名子也可以看出,一次单独的轻击抬起操作,也就是轻击一下屏幕，立刻抬起来，才会有这个触发，当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以也就不会触发这个事件

        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        在屏幕上拖动事件。无论是用手拖动view，或者是以抛的动作滚动，都会多次触发,这个方法       在ACTION_MOVE动作发生时就会触发
        /**
         *  滑屏：手指触动屏幕后，稍微滑动后立即松开
         *     onDown-----》onScroll----》onScroll----》onScroll----》………----->onFling
         *     拖动
         *     onDown------》onScroll----》onScroll------》onFiling
         *
         * 可见，无论是滑屏，还是拖动，影响的只是中间OnScroll触发的数量多少而已，最终都会触发onFling事件！
         */
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
//        长按触摸屏，超过一定时长，就会触发这个事件
//        触发顺序：onDown->onShowPress->onLongPress

    }

    /**
     * @param e1        第1个ACTION_DOWN MotionEvent
     * @param e2        最后一个ACTION_MOVE MotionEvent
     * @param velocityX X轴上的移动速度，像素/秒
     * @param velocityY Y轴上的移动速度，像素/秒
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        滑屏，用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发

        return false;
    }
}
