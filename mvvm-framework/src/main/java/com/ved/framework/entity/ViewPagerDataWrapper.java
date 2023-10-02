package com.ved.framework.entity;

public class ViewPagerDataWrapper {
    public float positionOffset;
    public float position;
    public int positionOffsetPixels;
    public int state;

    public ViewPagerDataWrapper(float position, float positionOffset, int positionOffsetPixels, int state) {
        this.positionOffset = positionOffset;
        this.position = position;
        this.positionOffsetPixels = positionOffsetPixels;
        this.state = state;
    }
}
