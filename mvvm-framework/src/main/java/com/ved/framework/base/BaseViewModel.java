package com.ved.framework.base;

import android.app.Application;
import android.os.Bundle;

import com.trello.rxlifecycle4.LifecycleProvider;
import com.ved.framework.bus.RxBus;
import com.ved.framework.bus.RxSubscriptions;
import com.ved.framework.bus.event.SingleLiveEvent;
import com.ved.framework.bus.event.eventbus.EventBusUtil;
import com.ved.framework.bus.event.eventbus.MessageEvent;
import com.ved.framework.entity.ParameterField;
import com.ved.framework.permission.IPermission;
import com.ved.framework.utils.Constant;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * Created by ved on 2017/6/15.
 */
public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements IBaseViewModel, Consumer<Disposable> {
    protected M model;
    private UIChangeLiveData uc;
    //弱引用持有
    private WeakReference<LifecycleProvider> lifecycle;
    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private CompositeDisposable mCompositeDisposable;
    private Disposable mEventSubscription;

    public BaseViewModel(@NonNull Application application) {
        this(application, null);
    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
        mCompositeDisposable = new CompositeDisposable();
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public boolean openEventSubscription() {
        return false;
    }

    @Override
    public boolean onEventSticky() {
        return false;
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycle.get();
    }

    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }

    public void showDialog() {
        showDialog("请稍后...");
    }

    public void showDialog(String title) {
        uc.showDialogEvent.postValue(title);
    }

    public void dismissDialog() {
        uc.dismissDialogEvent.call();
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startActivityEvent.postValue(params);
    }

    public void sendReceiver(){
        sendReceiver(null);
    }

    public void sendReceiver(Bundle bundle) {
        uc.sendReceiverEvent.postValue(bundle);
    }

    public void startActivityForResult(Class<?> clz,int requestCode) {
        startActivityForResult(clz, null,requestCode);
    }

    public void startActivityForResult(Class<?> clz, Bundle bundle,int requestCode) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        params.put(ParameterField.REQUEST_CODE,requestCode);
        uc.getStartActivityForResultEvent().postValue(params);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CANONICAL_NAME, canonicalName);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startContainerActivityEvent.postValue(params);
    }

    public void requestPermissions(IPermission iPermission, String... permissions){
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.PERMISSION,iPermission);
        params.put(Constant.PERMISSION_NAME,permissions);
        uc.requestPermissionEvent.postValue(params);
    }

    public void callPhone(String phoneNumber){
        Map<String, Object> params = new HashMap<>();
        params.put(Constant.PHONE_NUMBER,phoneNumber);
        uc.requestCallPhoneEvent.postValue(params);
    }

    /**
     * 关闭界面
     */
    public void finish() {
        uc.finishEvent.call();
    }

    /**
     * 返回上一层
     */
    public void onBackPressed() {
        uc.onBackPressedEvent.call();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @Override
    public void onCreate() {
        getUC().getOnLoadEvent().call();
    }

    public void sendRxEvent(MessageEvent<?> messageEvent){
        if (onEventSticky()) {
            RxBus.getDefault().postSticky(messageEvent);
        } else {
            RxBus.getDefault().post(messageEvent);
        }
    }

    public void sendEvent(MessageEvent<?> messageEvent){
        if (onEventSticky()) {
            EventBusUtil.sendStickyEvent(messageEvent);
        } else {
            EventBusUtil.sendEvent(messageEvent);
        }
    }

    private void onStartEventSubscription(){
        if (onEventSticky()) {
            mEventSubscription = RxBus.getDefault().toObservableSticky(MessageEvent.class).subscribe(this::onEvent, this::onError);
        } else {
            mEventSubscription = RxBus.getDefault().toObservable(MessageEvent.class).subscribe(this::onEvent, this::onError);
        }
        RxSubscriptions.add(mEventSubscription);
    }

    @Override
    public void onEvent(MessageEvent<?> event){
    }

    public void onError(Throwable throwable){
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void registerRxBus() {
        if (openEventSubscription()){
            onStartEventSubscription();
        }
    }

    @Override
    public void removeRxBus() {
        if (openEventSubscription()){
            if (mEventSubscription != null){
                RxSubscriptions.remove(mEventSubscription);
            }
        }
    }

    @Override
    public void receiveEvent(MessageEvent<?> event) {
    }

    @Override
    public void receiveStickyEvent(MessageEvent<?> event) {
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        try {
            if (model != null) {
                model.onCleared();
            }
            if (mCompositeDisposable != null) {
                mCompositeDisposable.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(Disposable disposable) throws Exception {
        addSubscribe(disposable);
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<String> showDialogEvent;
        private SingleLiveEvent<Void> dismissDialogEvent;
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Bundle> sendReceiverEvent;
        private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
        private SingleLiveEvent<Map<String, Object>> startActivityForResultEvent;
        private SingleLiveEvent<Void> finishEvent;
        private SingleLiveEvent<Void> onBackPressedEvent;
        private SingleLiveEvent<Void> onLoadEvent;
        private SingleLiveEvent<Map<String, Object>> requestPermissionEvent;
        private SingleLiveEvent<Map<String, Object>> requestCallPhoneEvent;

        public SingleLiveEvent<Map<String, Object>> getRequestCallPhoneEvent() {
            return requestCallPhoneEvent = createLiveData(requestCallPhoneEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getRequestPermissionEvent() {
            return requestPermissionEvent = createLiveData(requestPermissionEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityForResultEvent() {
            return startActivityForResultEvent = createLiveData(startActivityForResultEvent);
        }

        public SingleLiveEvent<String> getShowDialogEvent() {
            return showDialogEvent = createLiveData(showDialogEvent);
        }

        public SingleLiveEvent<Void> getDismissDialogEvent() {
            return dismissDialogEvent = createLiveData(dismissDialogEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public SingleLiveEvent<Bundle> getReceiverEvent() {
            return sendReceiverEvent = createLiveData(sendReceiverEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
            return startContainerActivityEvent = createLiveData(startContainerActivityEvent);
        }

        public SingleLiveEvent<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public SingleLiveEvent<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        public SingleLiveEvent<Void> getOnLoadEvent() {
            return onLoadEvent = createLiveData(onLoadEvent);
        }

        private <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent<>();
            }
            return liveData;
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
            super.observe(owner, observer);
        }
    }
}
