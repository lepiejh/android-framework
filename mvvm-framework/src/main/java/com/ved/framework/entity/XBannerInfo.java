package com.ved.framework.entity;

import com.stx.xhb.xbanner.entity.BaseBannerInfo;

public class XBannerInfo implements BaseBannerInfo {
    private final String info;

    public XBannerInfo(String info) {
        this.info = info;
    }

    @Override
    public Object getXBannerUrl() {
        return info;
    }

    @Override
    public String getXBannerTitle() {
        return "";
    }
}
