package com.ved.framework.entity;

public class NestScrollDataWrapper {
    public int scrolved;
    public int scrollY;
    public int oldScrolved;
    public int oldScrollY;

    public NestScrollDataWrapper(int scrolved, int scrollY, int oldScrolved, int oldScrollY) {
        this.scrolved = scrolved;
        this.scrollY = scrollY;
        this.oldScrolved = oldScrolved;
        this.oldScrollY = oldScrollY;
    }
}
