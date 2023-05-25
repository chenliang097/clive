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

    @Override
    public void onCreate() {
        super.onCreate();
        Live.init(this);
    }
}
