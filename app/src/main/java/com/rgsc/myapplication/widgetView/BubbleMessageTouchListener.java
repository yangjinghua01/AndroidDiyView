package com.rgsc.myapplication.widgetView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rgsc.myapplication.R;
import com.rgsc.myapplication.utils.BubbleUtils;

/**
 * 监听View的触摸
 */
public class BubbleMessageTouchListener implements View.OnTouchListener, MessageBubbleView.MessageBubbleListner {
    //    原来的View
    private View mStatiView;
    private WindowManager mWindowManager;
    private MessageBubbleView mMessageBubbleViewl;
    private WindowManager.LayoutParams mParams;
    private Context mContext;
    //    消失动画
    private FrameLayout mBomFrame;
    private ImageView mBomImageView;
    private MessageBubbleView.BubleDispearListener mDispearListenerl;

    public BubbleMessageTouchListener(View view, Context context, MessageBubbleView.BubleDispearListener dispearListener) {
        mStatiView = view;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mMessageBubbleViewl = new MessageBubbleView(context);
        mMessageBubbleViewl.setMessageBubbleListner(this);
        mParams = new WindowManager.LayoutParams();
        mParams.format = PixelFormat.TRANSPARENT;
        mContext = context;
        mBomFrame = new FrameLayout(mContext);
        mBomImageView = new ImageView(mContext);
        mBomImageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mBomFrame.addView(mBomImageView);
        this.mDispearListenerl = dispearListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStatiView.setVisibility(View.INVISIBLE);
                mWindowManager.addView(mMessageBubbleViewl, mParams);
//                初始化贝瑟尔的点
                int[] loaction = new int[2];
                mStatiView.getLocationOnScreen(loaction);
                Bitmap bitmap = getBitmapByView(mStatiView);
                mMessageBubbleViewl.initPoint(loaction[0] + mStatiView.getWidth() / 2, loaction[1] + mStatiView.getHeight() / 2 - BubbleUtils.getStatusBarHeight(mContext));
//                给拖拽设计一个bitmap
                mMessageBubbleViewl.setDragBitmap(bitmap);
                break;
            case MotionEvent.ACTION_UP:
                mMessageBubbleViewl.handleActionUp();
                break;
            case MotionEvent.ACTION_MOVE:
                mMessageBubbleViewl.updatePoint(event.getRawX(), event.getRawY() - BubbleUtils.getStatusBarHeight(mContext));
                break;
        }
        return true;
    }

    /**
     * 从一个View中获取一个bitmap
     *
     * @param mStatiView
     * @return
     */
    private Bitmap getBitmapByView(View mStatiView) {
        mStatiView.buildDrawingCache();
        Bitmap bitmap = mStatiView.getDrawingCache();
        return bitmap;
    }

    @Override
    public void restore() {
//把消息
        mWindowManager.removeView(mMessageBubbleViewl);
//        原来的显示
        mStatiView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismiss(PointF pointF) {
//执行消失动画
        mWindowManager.removeView(mMessageBubbleViewl);
//        要在mWindowManager 添加动画
        mWindowManager.addView(mBomFrame, mParams);
        mBomImageView.setBackgroundResource(R.drawable.anim_bubble_pop);
        AnimationDrawable drawable = (AnimationDrawable) mBomImageView.getBackground();
        mBomImageView.setX(pointF.x - drawable.getIntrinsicWidth() / 2);
        mBomImageView.setY(pointF.y - drawable.getIntrinsicHeight() / 2);
        drawable.start();
//        动画执行完移除这个mBomFrame
        mBomImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWindowManager.removeView(mBomFrame);
//                通知消失
                if (mDispearListenerl!=null){
                    mDispearListenerl.disminss();
                }
            }
        }, getAnimationDrawableTime(drawable));
    }

    private long getAnimationDrawableTime(AnimationDrawable drawable) {
        int numberOfFrames = drawable.getNumberOfFrames();
        long time = 0;
        for (int i = 0; i < numberOfFrames; i++) {
            time += drawable.getDuration(i);
        }
        return time;
    }
}
