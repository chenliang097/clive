package com.rongtuoyouxuan.chatlive.streaming;
public interface IStreamingLifecycle {
    void onCreate();

    void onRestart();

    void onResume();

    void onPause();

    void onDestroy();
}
