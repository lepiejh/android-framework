package com.ved.framework.bus;


import androidx.annotation.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;


/**
 * 只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
 */
public class RxBus {
    private static volatile RxBus mDefaultInstance;
    private final Subject<Object> mBus;

    private final Map<Class<?>, Object> mStickyEventMap;

    public RxBus() {
        mBus = PublishSubject.create().toSerialized();
        mStickyEventMap = new ConcurrentHashMap<>();
    }

    public static RxBus getDefault() {
        if (mDefaultInstance == null) {
            synchronized (RxBus.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    /**
     * 发送事件
     */
    public void post(@Nullable Object event) {
        if (event != null) {
            mBus.onNext(event);
        }
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservable(@Nullable Class<T> eventType) {
        if (eventType != null){
            return mBus.ofType(eventType);
        }else {
            return null;
        }
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    public void reset() {
        mDefaultInstance = null;
    }

    /**
     * 发送一个新Sticky事件
     */
    public void postSticky(@Nullable Object event) {
        synchronized (mStickyEventMap) {
            if (event != null) {
                mStickyEventMap.put(event.getClass(), event);
            }
        }
        post(event);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(@Nullable final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            if (eventType != null) {
                Observable<T> observable = mBus.ofType(eventType);
                final Object event = mStickyEventMap.get(eventType);

                if (event != null) {
                    return Observable.merge(observable, Observable.create(emitter -> emitter.onNext(eventType.cast(event))));
                } else {
                    return observable;
                }
            }else {
                return null;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(@Nullable Class<T> eventType) {
        synchronized (mStickyEventMap) {
            if (eventType != null) {
                return eventType.cast(mStickyEventMap.get(eventType));
            } else {
                return null;
            }
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(@Nullable Class<T> eventType) {
        synchronized (mStickyEventMap) {
            if (eventType != null) {
                return eventType.cast(mStickyEventMap.remove(eventType));
            } else {
                return null;
            }
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }
}