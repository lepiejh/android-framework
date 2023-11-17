package com.ved.framework.net;

public interface ISeatError {
    void onErrorView();

    void onErrorHandler(int code);

    void onEmptyView();

    void onEmptyView(String error);
}
