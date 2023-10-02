package com.ved.framework.utils;


import android.os.Handler;
import android.os.Message;

import com.ved.framework.base.BaseActivity;

import java.lang.ref.WeakReference;

public abstract class MemoryLeakHandler<T extends BaseActivity> extends Handler {
    private final WeakReference<T> mActivity;

    public MemoryLeakHandler(T activity) {
        mActivity = new WeakReference<T>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        T activity = mActivity.get();
        super.handleMessage(msg);
        handleMessage(msg,activity);
    }

    protected abstract void handleMessage(Message msg, T activity);
}
