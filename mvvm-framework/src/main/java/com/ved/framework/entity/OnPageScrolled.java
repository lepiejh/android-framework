package com.ved.framework.entity;

public class OnPageScrolled {
    public int position;
    public float positionOffset;
    public int positionOffsetPixels;

    public OnPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.position = position;
        this.positionOffset = positionOffset;
        this.positionOffsetPixels = positionOffsetPixels;
    }
}
