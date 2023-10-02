package com.ved.framework.update;

import android.view.View;

import model.UiConfig;
import model.UpdateConfig;

public interface OnUpdateListener {
    void onInitUpdateUi(View view, UpdateConfig updateConfig, UiConfig uiConfig);
}
