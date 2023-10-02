package com.ved.framework.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.bytedance.boost_multidex.BoostMultiDexApplication;
import com.ved.framework.utils.Utils;
import com.ved.framework.utils.album.GlideAlbumLoader;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import me.jessyan.autosize.AutoSizeConfig;
import update.UpdateAppUtils;

/**
 * Created by ved on 2017/6/15.
 */

public class BaseApplication extends BoostMultiDexApplication {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        AutoSizeConfig.getInstance().setCustomFragment(true);
        UpdateAppUtils.init(this);
        setApplication(this);
        Album.initialize(AlbumConfig.newBuilder(this).setAlbumLoader(new GlideAlbumLoader()).build());
    }

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     */
    public synchronized void setApplication(@NonNull Application application) {
        sInstance = application;
        //初始化工具类
        Utils.init(application);
        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getAppManager().removeActivity(activity);
            }
        });
    }

    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }
}
