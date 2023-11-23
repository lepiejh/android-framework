package com.ved.framework.net;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;

import com.ved.framework.base.BaseViewModel;
import com.ved.framework.http.ResponseThrowable;
import com.ved.framework.utils.NetUtil;
import com.ved.framework.utils.RxUtils;
import com.ved.framework.utils.Utils;

import androidx.annotation.Nullable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

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
        request(activity, viewModel, method, service,index,false,iResponse);
    }

    public void request(@Nullable Activity activity, @Nullable BaseViewModel viewModel, @Nullable Class<? extends T> service, @Nullable IMethod<T, K> method, int index, boolean isLoading, @Nullable IResponse<K> iResponse) {
        request(activity, viewModel, method, service,index,isLoading,iResponse);
    }

    public void request(boolean isLoading,@Nullable BaseViewModel viewModel, @Nullable Class<? extends T> service, @Nullable IMethod<T, K> method,  View view,ISeatSuccess seatSuccess,ISeatError seatError, @Nullable IResponse<K> iResponse) {
        request(isLoading,viewModel, method, service,view,seatSuccess,seatError,iResponse);
    }


    @SuppressLint("CheckResult")
    public void request(boolean isLoading,@Nullable BaseViewModel viewModel,@Nullable IMethod<T, K> method,@Nullable Class<? extends T> service,View view,ISeatSuccess seatSuccess,ISeatError seatError,@Nullable IResponse<K> iResponse) {
        if (view!= null) {
            seatSuccess.onStateView();
        }
        if (isLoading&&viewModel!=null)
        {
            viewModel.showDialog();
        }

        if (NetUtil.getNetWorkStart(Utils.getContext()) == 1) {

            if (isLoading&&viewModel!=null)
            {
                viewModel.dismissDialog();
            }

            if (iResponse != null) {
                iResponse.onError("网络异常");
                if (view != null) {
                    //手机无网络
                    seatSuccess.onNoNetworkView();
                }
                if (isLoading&&viewModel!=null)
                {
                    viewModel.dismissDialog();
                }
            }
        } else {
            try {
                if (method != null) {
                    Observable o = method.method(RetrofitClient.getInstance().create(service, 0, null));
                    if (viewModel != null) {
                        o.compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider())); // 请求与View周期同步
                    }
                    o.compose(RxUtils.schedulersTransformer())
                            .compose(new ObservableTransformer() {
                                @Override
                                public ObservableSource apply(Observable observable) {
                                    return observable
                                            .onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                                                @Override
                                                public ObservableSource apply(Throwable throwable) throws Throwable {
                                                    parseError( isLoading,viewModel,view,seatError, iResponse);
                                                    return Observable.error(throwable);
                                                }
                                            });
                                }
                            })
                            .subscribe((Consumer<K>) response -> parseSuccess(isLoading,viewModel,view, iResponse, response),(Consumer<ResponseThrowable>) throwable -> parseError( isLoading,viewModel,view,seatError, iResponse, throwable));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void parseSuccess(boolean isLoading,@Nullable BaseViewModel viewModel,View viewState, IResponse<K> iResponse, K response) {
        if (viewState!= null) {
            viewState.setVisibility(View.GONE);
        }
        if (isLoading&&viewModel!=null)
        {
            viewModel.dismissDialog();
        }
        iResponse.onSuccess(response);
    }

    private void parseError(boolean isLoading,@Nullable BaseViewModel viewModel,View viewState,ISeatError seatError,IResponse<K> iResponse) {

        if (isLoading&&viewModel!=null)
        {
            viewModel.dismissDialog();
        }

        if (viewState!= null) {
            seatError.onErrorView();
            iResponse.onError("");
        }

    }

    private void parseError(boolean isLoading,@Nullable BaseViewModel viewModel,View viewState,ISeatError seatError,IResponse<K> iResponse, ResponseThrowable throwable) {

        if (isLoading&&viewModel!=null)
        {
            viewModel.dismissDialog();
        }

        if (viewState!= null) {
            seatError.onErrorView();
            if (throwable.getCause() instanceof ResultException)
            {
                ResultException resultException = (ResultException) throwable.getCause();
                seatError.onErrorView();
                seatError.onErrorHandler(resultException.getErrCode());

                if (TextUtils.isEmpty(resultException.getErrMsg())) {
                    iResponse.onError(throwable.message);
                    seatError.onEmptyView();
                } else {
                    iResponse.onError(resultException.getErrMsg());
                    seatError.onEmptyView();
                }
            } else {
                iResponse.onError(throwable.message);
                seatError.onEmptyView(throwable.message);
            }

        }

    }

    @SuppressLint("CheckResult")
    public void request(@Nullable Activity activity, @Nullable BaseViewModel viewModel, @Nullable IMethod<T, K> method,@Nullable Class<? extends T> service,int index,boolean isLoading, @Nullable IResponse<K> iResponse) {
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
                    Observable o = method.method(RetrofitClient.getInstance().create(service, index, null));
                    if (viewModel != null) {
                        o.compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider())); // 请求与View周期同步
                    }
                    o.compose(RxUtils.schedulersTransformer())
                            .compose(new ObservableTransformer() {
                                @Override
                                public ObservableSource apply(Observable observable) {
                                    return observable
                                            .onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                                                @Override
                                                public ObservableSource apply(Throwable throwable) throws Throwable {
                                                    parseError(viewModel, isLoading, iResponse);
                                                    return Observable.error(throwable);
                                                }
                                            });
                                }
                            })
                            .subscribe((Consumer<K>) response -> parseSuccess(viewModel, isLoading, iResponse, response),(Consumer<ResponseThrowable>) throwable -> parseError(viewModel, isLoading, iResponse, throwable, activity));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void parseError(@Nullable BaseViewModel viewModel, boolean isLoading, IResponse<K> iResponse) {
        if (isLoading && viewModel != null) {
            viewModel.dismissDialog();
        }
        iResponse.onError("");
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
