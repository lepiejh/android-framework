package com.ved.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * @Des syyk_android
 * @Time 2023/10/20
 * @Author Peng-BinBin
 **/
public class CustomViewPager extends ViewPager {
    // 构造函数
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 返回false以禁止滑动
        return false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // 返回false以禁止滑动
        return false;
    }
}
