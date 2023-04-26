package com.rongtuoyouxuan.chatlive;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.rongtuoyouxuan.qfcommon.notification.AppLifecycleCallback;
import com.rongtuoyouxuan.qfcommon.util.APIEnvironment;

public class DirectApp extends Application {
    private static final String TAG = "DirectApp";

    private static void initChat(Context context) {
//        IMSocketImpl.getInstance().initIM();
//        ImHelper.Companion.getInstance().init();
//        IMManager.getInstance().init(Utils.getApp());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Live.onConfigurationChanged(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Live.getLocalContext(base, this));
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APIEnvironment.INSTANCE.initEnvironment();
        //注册Activity生命周期
        registerActivityLifecycleCallbacks(new AppLifecycleCallback());
        initChat(this);
        Live.init(this);
    }
}
