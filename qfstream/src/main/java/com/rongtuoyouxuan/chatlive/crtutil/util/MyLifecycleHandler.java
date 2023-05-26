package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;


import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.rongtuoyouxuan.chatlive.crtlog.PLog;

public class MyLifecycleHandler implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = MyLifecycleHandler.class.getSimpleName();

    private static volatile MyLifecycleHandler mInstance;

    static {
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver());
    }

    private @Nullable WeakReference<Activity> currentActivity;
    private CopyOnWriteArrayList<Listener> mListeners = new CopyOnWriteArrayList<>();

    private MyLifecycleHandler() {

    }

    public static MyLifecycleHandler getInstance() {
        if (mInstance == null) {
            synchronized (MyLifecycleHandler.class) {
                if (mInstance == null) {
                    mInstance = new MyLifecycleHandler();
                }
            }
        }
        return mInstance;
    }

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    public void removeListener(Listener listener) {
        mListeners.remove(listener);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    public interface Listener {

        /**
         * 应用处于前台
         *
         * @param activity Activity
         */
        void onBecameForeground(Activity activity);

        /**
         * 应用处于后台
         *
         * @param activity Activity
         */
        void onBecameBackground(Activity activity);
    }

    static class AppLifecycleObserver implements LifecycleObserver {

        private final String TAG = AppLifecycleObserver.class.getSimpleName();

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onAppCreate() {
            //run the code we need
            PLog.d(TAG, "onAppCreate");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onEnterForeground() {
            Activity activity = getCurrentActivity();
            if (activity != null){
                for (Listener listener : MyLifecycleHandler.getInstance().mListeners) {
                    listener.onBecameForeground(activity);
                }
            }
            PLog.d(TAG, "onEnterForeground Activity:%s", activity != null ? activity.getClass().getName() : "");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onEnterBackground() {
            Activity activity = getCurrentActivity();
            if (activity != null){
                for (Listener listener : MyLifecycleHandler.getInstance().mListeners) {
                    listener.onBecameBackground(activity);
                }
            }
            PLog.d(TAG, "onEnterBackground Activity:%s", activity != null ? activity.getClass().getName() : "");
        }

        @Nullable
        private Activity getCurrentActivity() {
            if (MyLifecycleHandler.getInstance().currentActivity != null) {
                return MyLifecycleHandler.getInstance().currentActivity.get();
            }
            return null;
        }
    }
}