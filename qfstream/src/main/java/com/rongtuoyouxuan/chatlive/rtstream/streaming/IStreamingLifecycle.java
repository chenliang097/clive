package com.rongtuoyouxuan.chatlive.rtstream.streaming;
public interface IStreamingLifecycle {
    void onCreate();

    void onRestart();

    void onResume();

    void onPause();

    void onDestroy();
}
