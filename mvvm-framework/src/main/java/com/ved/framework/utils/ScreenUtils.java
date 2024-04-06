package com.ved.framework.utils;

import android.content.res.Configuration;
import android.util.TypedValue;

public class ScreenUtils {
    /**
     * 获取屏幕高度(px)
     */
    public static int getScreenHeight() {
        return Utils.getContext().getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕宽度(px)
     */
    public static int getScreenWidth() {
        return Utils.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int dp2px(float dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Utils.getContext().getResources().getDisplayMetrics()) + 0.5f);
    }

    /**
     * 判断是否平板设备
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice() {
        return (Utils.getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
