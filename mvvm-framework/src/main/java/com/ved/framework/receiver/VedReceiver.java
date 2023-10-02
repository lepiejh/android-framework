package com.ved.framework.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ved.framework.base.BaseActivity;

public class VedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (context instanceof BaseActivity){
            ((BaseActivity) context).onReceive(intent);
        }
    }
}
