package com.ved.framework.net;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.ved.framework.base.BaseViewModel;
import com.ved.framework.http.ResponseThrowable;
import com.ved.framework.utils.NetUtil;
import com.ved.framework.utils.RxUtils;
import com.ved.framework.utils.Utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * 网络请求
 *
 * @param <T> service interface
 * @param <K> 返回的数据类型
 */
public abstract class ARequest<T, K> {

    /**
     * 可自定义code 封装处理    继承 ApiDisposableObserver
     */
    public void request(@Nullable Activity activity, @Nullable BaseViewModel viewModel, @Nullable Class<? extends T> service, @Nullable IMethod<T, K> method, @Nullable IResponse<K> iResponse) {
        request(activity, viewModel, service, method, 0, iResponse);
    }

    public void request(@Nullable Activity activity, @Nullable BaseViewModel viewModel, @Nullable Class<? extends T> service, @Nullable IMethod<T, K> method, boolean isLoading, @Nullable IResponse<K> iResponse) {
        request(activity, viewModel, service, method, 0, isLoading, iResponse);
    }

    public void request(@Nullable Activity activity, @Nullable BaseViewModel viewModel, @Nullable Class<? extends T> service, @Nullable IMethod<T, K> method, int index, @Nullable IResponse<K> iResponse) {
        request(activity, viewModel, method, false, RetrofitClient.getInstance().create(service, index, null), iResponse);
    }

    public void request(@Nullable Activity activity, @Nullable BaseViewModel viewModel, @Nullable Class<? extends T> service, @Nullable IMethod<T, K> method, int index, boolean isLoading, @Nullable IResponse<K> iResponse) {
        request(activity, viewModel, method, isLoading, RetrofitClient.getInstance().create(service, index, null), iResponse);
    }

    @SuppressLint("CheckResult")
    public void request(@Nullable Activity activity, @Nullable BaseViewModel viewModel, @Nullable IMethod<T, K> method, boolean isLoading, @Nullable T t, @Nullable IResponse<K> iResponse) {
        if (isLoading && viewModel != null) {
            viewModel.showDialog();
        }
        if (NetUtil.getNetWorkStart(Utils.getContext()) == 1) {
            if (isLoading && viewModel != null) {
                viewModel.dismissDialog();
            }
            if (iResponse != null) {
                iResponse.onError("网络异常");
            }
            exceptionHandling(activity, "网络异常", -1);
        } else {
            try {
                if (method != null) {
                    Observable o = method.method(t);
                    if (viewModel != null) {
                        o.compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider())); // 请求与View周期同步
                    }
                    o.compose(RxUtils.schedulersTransformer())
                            .compose(RxUtils.exceptionTransformer())
                            .subscribe((Consumer<K>) response -> parseSuccess(viewModel, isLoading, iResponse, response), (Consumer<ResponseThrowable>) throwable -> parseError(viewModel, isLoading, iResponse, throwable, activity));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseSuccess(@Nullable BaseViewModel viewModel, boolean isLoading, IResponse<K> iResponse, K response) {
        if (isLoading && viewModel != null) {
            viewModel.dismissDialog();
        }
        iResponse.onSuccess(response);
    }

    private void parseError(@Nullable BaseViewModel viewModel, boolean isLoading, IResponse<K> iResponse, ResponseThrowable throwable, Activity activity) {
        if (isLoading && viewModel != null) {
            viewModel.dismissDialog();
        }
        if (throwable.getCause() instanceof ResultException) {
            ResultException resultException = (ResultException) throwable.getCause();
            exceptionHandling(activity, resultException.getErrMsg(), resultException.getErrCode());
            if (TextUtils.isEmpty(resultException.getErrMsg())) {
                iResponse.onError(throwable.message);
            } else {
                iResponse.onError(resultException.getErrMsg());
            }
        } else {
            iResponse.onError(throwable.message);
        }
    }

    public abstract void exceptionHandling(@Nullable Activity activity, @Nullable String error, int code);
}
