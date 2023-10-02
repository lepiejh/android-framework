package com.ved.framework.utils.compression;

import java.io.File;

import androidx.annotation.Nullable;

public interface OnCompressListener {

    /**
     * Fired when the compression is started, override to handle in your own code
     */
    void onStart();

    /**
     * Fired when a compression returns successfully, override to handle in your own code
     */
    void onSuccess(@Nullable File file);

    /**
     * Fired when a compression fails to complete, override to handle in your own code
     */
    void onError(@Nullable Throwable e);
}
