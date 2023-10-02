package com.ved.framework.http;


import com.ved.framework.utils.ToastUtils;
import com.ved.framework.utils.Utils;

import io.reactivex.rxjava3.observers.DisposableObserver;

/**
 * Created by ved on 2017/5/10.
 * 统一的Code封装处理。该类仅供参考，实际业务逻辑, 根据需求来定义，
 */

public abstract class ApiDisposableObserver<T> extends DisposableObserver<T> {
    protected abstract void onResult(T response);
    protected abstract void onError(String error);

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onError(e.getMessage());
        if (e instanceof ResponseThrowable) {
            ResponseThrowable rError = (ResponseThrowable) e;
            ToastUtils.showLong(rError.message);
            return;
        }
        //其他全部甩锅网络异常
        ToastUtils.showLong("网络错误");
    }

    @Override
    public void onStart() {
        super.onStart();
        // if  NetworkAvailable no !   must to call onCompleted
        if (!NetworkUtil.isNetworkAvailable(Utils.getContext())) {
            onError("网络错误");
            onComplete();
        }
    }

    @Override
    public void onNext(Object o) {
        onResult((T) o);
    }
}