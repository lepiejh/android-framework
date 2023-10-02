package com.ved.framework.net;

import androidx.annotation.Nullable;

public interface IResponse<T> {
    void onSuccess(@Nullable T t);

    void onError(@Nullable String msg);
}
