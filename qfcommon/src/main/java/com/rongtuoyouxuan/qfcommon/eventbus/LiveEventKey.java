package com.rongtuoyouxuan.qfcommon.eventbus;


public class LiveEventKey<T> {
    public LiveEventKey() {
    }

    public String getId() {
        return String.valueOf(hashCode());
    }
}
