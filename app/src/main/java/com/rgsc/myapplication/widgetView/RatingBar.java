package com.rgsc.myapplication.widgetView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.rgsc.myapplication.R;

public class RatingBar extends View {
    private int gradeNumber = 5;
    private Bitmap mStarNormalBitmap, mStarFocusBitmap;
    private int mCurrentGradel = 0;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        gradeNumber = typedArray.getInt(R.styleable.RatingBar_gradeNumber, gradeNumber);
        int starNormalId = typedArray.getResourceId(R.styleable.RatingBar_starNormal, 0);
        if (starNormalId == 0) {
            throw new RuntimeException("请设置属性starNormal");
        }
        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), starNormalId);
        int starFocusId = typedArray.getResourceId(R.styleable.RatingBar_starFocus, 0);
        if (starFocusId == 0) {
            throw new RuntimeException("请设置属性starFocus");
        }
        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(), starFocusId);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        高度
        int height = mStarFocusBitmap.getHeight();
        int width = mStarFocusBitmap.getWidth() * gradeNumber;
        setMeasuredDimension(width + getPaddingLeft() * 5, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < gradeNumber; i++) {
            if (mCurrentGradel > i) {
                canvas.drawBitmap(mStarFocusBitmap, mStarFocusBitmap.getWidth() * i + getPaddingLeft() * i, 0, null);
            }else {
                canvas.drawBitmap(mStarNormalBitmap, mStarFocusBitmap.getWidth() * i + getPaddingLeft() * i, 0, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float moveX = event.getX();//event.getRawX()  获取相对于屏幕的位置   event.getX()  相对于当前控件的位置。
                int currentGrade = 0;
                for (int i = 0; i < gradeNumber; i++) {
                     currentGrade  = (int) (moveX/(mStarNormalBitmap.getWidth())+1);
                }
                if (currentGrade<0){
                    currentGrade =0;
                }
                if (currentGrade > gradeNumber){
                    currentGrade = gradeNumber;
                }
                Log.i("DEBUG", "onTouchEvent: "+currentGrade);
//                刷新显示
                mCurrentGradel = currentGrade;
                invalidate();
        }
        return true;  //这里要返回true 消费事件
    }
}
