package com.ved.framework.base;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ved.framework.utils.KLog;
import com.ved.framework.utils.UIUtils;
import com.ved.framework.utils.bland.code.ScreenUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import com.yanxinim.classroom.ui.base.BaseActivity;

public class OnlyOpaqueCompatActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //为了解决Android自level 1以来的[安装完成点击“Open”后导致的应用被重复启动]的Bug
        if (!isTaskRoot()){
            Intent intent = getIntent();
            String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && UIUtils.equals(intentAction,Intent.ACTION_MAIN)){
                finish();
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
//            HookSystem.Companion.getInstance().hookSystemHandler();
        }else {
            //如果是8.0系统的手机，并且认为是透明主题的Activity
            if (Build.VERSION.SDK_INT == 26 && this.isTranslucentOrFloating()) {
                //通过反射取消方向的设置，这样绕开系统的检查，避免闪退
                boolean result = this.fixOrientation();
                KLog.i("only_opaque","result : "+result);
            }
        }
        super.onCreate(savedInstanceState);
        if (isRegular()) {
            if (ScreenUtils.getScreenWidth() < ScreenUtils.getScreenHeight()){
                if (getRequestedOrientation() == SCREEN_ORIENTATION_UNSPECIFIED) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        }
    }

    public boolean isRegular(){
        return true;
    }

    public OnlyOpaqueCompatActivity() {
        super();
    }

    @ContentView
    public OnlyOpaqueCompatActivity(@LayoutRes int contentLayoutId) {
        super(contentLayoutId);
    }

    //通过反射判断是否是透明页面
    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    //通过反射将方向设置为 SCREEN_ORIENTATION_UNSPECIFIED，绕开系统的检查
    private boolean fixOrientation() {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(this);
            if (o != null) {
                o.screenOrientation = SCREEN_ORIENTATION_UNSPECIFIED;
            }
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
