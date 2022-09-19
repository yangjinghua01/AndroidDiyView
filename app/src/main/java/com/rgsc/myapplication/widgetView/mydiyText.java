//package com.rgsc.myapplication;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//
//public class mydiyText extends View {
//    private String text;
//    private int diyColor = Color.BLACK;
//    private Paint mPain;
//
//
//
//    public mydiyText(Context context) {
//        this(context,null);
//    }
//
//    public mydiyText(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs,0);
//    }
//
//    public mydiyText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.mydiyText);
//        text = typedArray.getString(R.styleable.mydiyText_mytext);
//        diyColor = typedArray.getColor(R.styleable.mydiyText_mycolor,diyColor);
//        typedArray.recycle();
//        mPain = new Paint();
//        mPain.setAntiAlias(true);
//        mPain.setColor(diyColor);
//        mPain.setTextSize(1f);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int WidthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int HeightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int heigth = MeasureSpec.getSize(heightMeasureSpec);
//        if (WidthMode == MeasureSpec.AT_MOST){
//            Rect rect = new Rect();
//            mPain.getTextBounds(text,0,text.length(),rect);
//             width = rect.width();
//        }
//        if (HeightMode == MeasureSpec.AT_MOST){
//            Rect rect = new Rect();
//            mPain.getTextBounds(text,0,text.length(),rect);
//            heigth = rect.height();
//        }
//        setMeasuredDimension(width,heigth);
//
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        Paint.FontMetricsInt fontMetricsInt = mPain.getFontMetricsInt();
//        int dy = (fontMetricsInt.bottom - fontMetricsInt.top)/2-fontMetricsInt.bottom;
//        int baseline = getHeight()/2+dy;
//        int x = getPaddingLeft();
//        canvas.drawText(text,x,baseline,mPain);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
//    }
//}
