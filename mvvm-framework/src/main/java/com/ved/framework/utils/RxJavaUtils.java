package com.ved.framework.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxJavaUtils {
    private static final long DELAY_LOAD_TIME = 3000L;

    public static Disposable delayTimerLoad(Consumer<Long> consumer) {
        return delayTimerLoad(DELAY_LOAD_TIME, consumer);
    }

    public static Disposable delayTimerLoad(long delay, Consumer<Long> consumer) {
        return Observable.timer(delay, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }

    public static <T> ObservableTransformer<T, T> toUiThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public @NonNull ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
