package com.ved.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;

import com.ved.framework.R;

import androidx.appcompat.widget.AppCompatCheckBox;

/**
 * <FrameLayout
 *         android:layout_width="62dp"
 *         android:layout_height="62dp"
 *         android:layout_alignParentRight="true">
 *         <com.ved.framework.widget.LargeTouchCheckBox
 *             android:layout_width="25dp"
 *             android:layout_height="25dp"
 *             android:id="@+id/del_cb"
 *             app:additionBottom="30dp"
 *             app:additionLeft="30dp"
 *             app:additionTop="7dp"
 *             app:additionRight="7dp"
 *             android:button="@drawable/selector_select"
 *             android:layout_gravity="right"
 *             />
 *     </FrameLayout>
 *
 *   LargeTouchCheckBox需要在外层套一个layout，layout的大小就是你想要的点击范围
 *   （宽=checkbox.width+leftpadding+rightpadding，高以此类推）
 *
 *      final View parent = (View) this.getParent();
 *  parent.setTouchDelegate(new TouchDelegate(new Rect(left
 *                     - mTouchAdditionLeft, top - mTouchAdditionTop, right
 *                     + mTouchAdditionRight, bottom + mTouchAdditionBottom), this));
 *
 *   由于setTouchDelegate，所以一个viewgroup下只能有一个控件设置TouchDelegate，
 *   其他控件如果也想设置TouchDelegate只能在控件外层嵌套一层无用的FrameLayout。
 */
public class LargeTouchCheckBox extends AppCompatCheckBox {
    private final int TOUCH_ADDITION = 0;
    private int mTouchAdditionBottom = 0;
    private int mTouchAdditionLeft = 0;
    private int mTouchAdditionRight = 0;
    private int mTouchAdditionTop = 0;
    private int mPreviousLeft = -1;
    private int mPreviousRight = -1;
    private int mPreviousBottom = -1;
    private int mPreviousTop = -1;

    public LargeTouchCheckBox(Context context) {
        super(context);
    }

    public LargeTouchCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LargeTouchCheckBox(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LargeTouchableAreaView);
        int addition = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_addition, TOUCH_ADDITION);
        mTouchAdditionBottom = addition;
        mTouchAdditionLeft = addition;
        mTouchAdditionRight = addition;
        mTouchAdditionTop = addition;
        mTouchAdditionBottom = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_additionBottom,
                mTouchAdditionBottom);
        mTouchAdditionLeft = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_additionLeft,
                mTouchAdditionLeft);
        mTouchAdditionRight = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_additionRight,
                mTouchAdditionRight);
        mTouchAdditionTop = (int) a.getDimension(
                R.styleable.LargeTouchableAreaView_additionTop,
                mTouchAdditionTop);
        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (left != mPreviousLeft || top != mPreviousTop
                || right != mPreviousRight || bottom != mPreviousBottom) {
            mPreviousLeft = left;
            mPreviousTop = top;
            mPreviousRight = right;
            mPreviousBottom = bottom;
            final View parent = (View) this.getParent();
            parent.setTouchDelegate(new TouchDelegate(new Rect(left
                    - mTouchAdditionLeft, top - mTouchAdditionTop, right
                    + mTouchAdditionRight, bottom + mTouchAdditionBottom), this));
        }
    }


}
