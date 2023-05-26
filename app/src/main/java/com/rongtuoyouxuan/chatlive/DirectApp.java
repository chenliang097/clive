package com.rongtuoyouxuan.chatlive;

import android.app.Application;

import com.rongtuoyouxuan.chatlive.crtlive.Live;

public class DirectApp extends Application {
    private static final String TAG = "DirectApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Live.init(this);
    }
}
