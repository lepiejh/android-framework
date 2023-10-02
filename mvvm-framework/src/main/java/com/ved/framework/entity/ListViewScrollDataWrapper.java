package com.ved.framework.entity;

public class ListViewScrollDataWrapper {
    public int firstVisibleItem;
    public int visibleItemCount;
    public int totalItemCount;
    public int scrollState;

    public ListViewScrollDataWrapper(int scrollState, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
        this.scrollState = scrollState;
    }
}
