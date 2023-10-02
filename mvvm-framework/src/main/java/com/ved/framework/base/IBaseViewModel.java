package com.ved.framework.base;

import com.ved.framework.bus.event.eventbus.MessageEvent;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by ved on 2017/6/15.
 */
public interface IBaseViewModel extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny(LifecycleOwner owner, Lifecycle.Event event);

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    /**
     * 注册RxBus
     */
    void registerRxBus();

    /**
     * 移除RxBus
     */
    void removeRxBus();

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    void receiveEvent(MessageEvent<?> event);

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    void receiveStickyEvent(MessageEvent<?> event);

    /**
     * 执行RxBus事件
     * @param event  事件
     */
    void onEvent(MessageEvent<?> event);

    /**
     * 是否开启粘性的RxBus事件
     * @return   true:开启  false:关闭
     */
    boolean onEventSticky();

    /**
     * 是否启动RxBus
     * @return  true:开启  false:关闭
     */
    boolean openEventSubscription();
}
