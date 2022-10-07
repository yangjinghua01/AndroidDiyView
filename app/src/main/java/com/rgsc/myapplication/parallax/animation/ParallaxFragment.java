package com.rgsc.myapplication.parallax.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.rgsc.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 视差动画的fragment
 */
public class ParallaxFragment extends Fragment implements LayoutInflaterFactory {
    public static final String LAYOUT_ID_KEY = "Layout_id_key";
    private CompatViewInflater mCompatViewInflater;

    // 存放所有的需要位移的View
    private List<View> mParallaxViews = new ArrayList<>();

    private int[] mParallaxAttrs = new int[]{R.attr.translationXIn,
            R.attr.translationXOut, R.attr.translationYIn, R.attr.translationYOut};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        加载布局
        int layoutId = getArguments().getInt(LAYOUT_ID_KEY);
//        view 创建的时候解析属性  这里传inflater 单例设计模式 代表着所有的View的创建都会是该fragment去创建的。
        inflater = inflater.cloneInContext(getActivity());
        LayoutInflaterCompat.setFactory(inflater, this);
        return inflater.inflate(layoutId, container, false);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // View都会来这里,创建View
        // 拦截到View的创建  获取View之后要去解析
        // 1. 创建View
        // If the Factory didn't handle it, let our createView() method try
        View view = createView(parent, name, context, attrs);

        // 2.1 一个activity的布局肯定对应多个这样的 SkinView
        if (view != null) {
            // Log.e("TAG", "我来创建View");
            // 解析所有的我们自己关注属性
            analysisAttrs(view, context, attrs);
        }
        return view;
    }

    private void analysisAttrs(View view, Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, mParallaxAttrs);
        if (array != null && array.getIndexCount() != 0) {
           /* float xIn = array.getFloat(0,0f);
            float xOut = array.getFloat(1,0f);
            float yIn = array.getFloat(2,0f);
            float yOut = array.getFloat(3,0f);*/
            int n = array.getIndexCount();
            ParallaxTag tag = new ParallaxTag();
            for (int i = 0; i < n; i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case 0:
                        tag.translationXIn = array.getFloat(attr, 0f);
                        break;
                    case 1:
                        tag.translationXOut = array.getFloat(attr, 0f);
                        break;
                    case 2:
                        tag.translationYIn = array.getFloat(attr, 0f);
                        break;
                    case 3:
                        tag.translationYOut = array.getFloat(attr, 0f);
                        break;
                }
            }
            // 自定义属性怎么存? 还要一一绑定  在View上面设置一个tag
            view.setTag(R.id.parallax_tag, tag);
            //Log.e("TAG",tag.toString());
            mParallaxViews.add(view);
        }
        array.recycle();
    }

    public View createView(View parent, final String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;
        if (mCompatViewInflater == null) {
            mCompatViewInflater = new CompatViewInflater();
        }
        final boolean inheritContext = isPre21 && true && shouldInheritContext((ViewParent) parent);
        return mCompatViewInflater.createView(parent, name, context, attrs, inheritContext, isPre21, true);
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        while (true) {
            if (parent == null) {
                return true;
            } else if (!(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                return false;
            }
            parent = parent.getParent();
        }
    }

    public List<View> getParallaxViews() {
        return mParallaxViews;
    }
}
