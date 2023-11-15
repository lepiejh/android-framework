package com.ved.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @Des 处理跟PhotoView事件冲突
 * @Time 2023/10/18
 * @Author Peng-BinBin
 **/
public class MViewPage extends ViewPager {
    public MViewPage(@NonNull Context context) {
        super(context);
    }

    public MViewPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
