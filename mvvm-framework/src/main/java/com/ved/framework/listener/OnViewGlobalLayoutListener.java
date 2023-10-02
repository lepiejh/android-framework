package com.ved.framework.listener;

import android.view.View;
import android.view.ViewTreeObserver;

public class OnViewGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private final int maxHeight;
    private final View view;

    public OnViewGlobalLayoutListener(View view,int maxHeight) {
        this.maxHeight = maxHeight;
        this.view = view;
    }

    @Override
    public void onGlobalLayout() {
        if (view.getHeight() > maxHeight){
            view.getLayoutParams().height = maxHeight;
        }
    }
}
