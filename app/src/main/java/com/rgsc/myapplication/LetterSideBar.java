package com.rgsc.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 侧边字母栏
 */
public class LetterSideBar extends View {
    private Paint mPaint;
    private int barTextColor = Color.BLUE;
    private int barTextSize = 20;
    private int barSelectColor = Color.BLACK;
    public static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private String TouchLettter;
    private MoveListener listener;

    public void setListener(MoveListener listener) {
        this.listener = listener;
    }

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSideBar);
        barTextColor = typedArray.getColor(R.styleable.LetterSideBar_barTextColor, barTextColor);
        barSelectColor = typedArray.getColor(R.styleable.LetterSideBar_barSelectColor, barSelectColor);
//       <attr name="roundWidth" format="dimension" /> 获取xml样式里的dimension属性的时候应该用getDimensionPixelSize 不然报错很麻烦
        barTextSize = (int) typedArray.getDimensionPixelSize(R.styleable.PropressBar_roundWidth, sp2px(barTextSize));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(barTextSize);
        mPaint.setColor(barTextColor);
    }

    //    sp 转 px
    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int textWidth = (int) mPaint.measureText("A");
//        计算指定宽度 =左右的padding + 字母的宽度(取决于画笔)
        int width = getPaddingLeft() + getPaddingRight() + textWidth;
//        高度不需要计算可以直接获取
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        画26个字母
        for (int i = 0; i < letters.length; i++) {
            int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
//            基线  ---->  知道每个字母的中心位置    第一个字母字母高度的一半 第二个前面的加上 自己的一半
            int LetterCenterY = itemHeight / 2 + i * itemHeight + getPaddingTop();
            Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
            int dy = (int) ((fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom);
            int baseLine = LetterCenterY + dy;
//            x 绘制在最中间 = 宽度/2 -文字/2
            int textWidth = (int) mPaint.measureText(letters[i]);
            int x = getWidth() / 2 - textWidth / 2;
//            当前字母要高亮
            if (letters[i].equals(TouchLettter)) {
                mPaint.setColor(barSelectColor);
                canvas.drawText(letters[i], x, baseLine, mPaint);
            } else {
                mPaint.setColor(barTextColor);
                canvas.drawText(letters[i], x, baseLine, mPaint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
//                 计算出当前触摸字母
                float currentMoveY = event.getY();   //------------------->这里可能会有负数
//                当前的y轴位置 除 字母高度 获得字母是哪个
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / letters.length;
                int currentPosition = (int) (currentMoveY / itemHeight);
                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                if (currentPosition > letters.length - 1) {
                    currentPosition = letters.length - 1;
                }
                TouchLettter = letters[currentPosition];
                if (listener != null) {
                    listener.select(TouchLettter, true);
                }
//                重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                listener.select("", false);
                mPaint.setColor(barTextColor);
                TouchLettter = "";
                invalidate();
                break;
        }
        return true;
    }

    public interface MoveListener {
        void select(String str, boolean touch);
    }
}
