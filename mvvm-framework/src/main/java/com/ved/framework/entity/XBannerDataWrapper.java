package com.ved.framework.entity;

import android.view.View;

import com.stx.xhb.xbanner.XBanner;

public class XBannerDataWrapper {
    public XBanner banner;
    public Object model;
    public View view;
    public int position;

    public XBannerDataWrapper(XBanner banner, Object model, View view, int position) {
        this.banner = banner;
        this.model = model;
        this.view = view;
        this.position = position;
    }
}
