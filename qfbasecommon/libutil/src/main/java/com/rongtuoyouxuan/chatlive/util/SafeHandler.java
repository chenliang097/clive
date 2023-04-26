package com.rongtuoyouxuan.chatlive.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by zhangdaoqiang on 4/12/17.
 */

public class SafeHandler extends Handler {
    private WeakReference<Object> reference;

    public SafeHandler() {
        super();
    }

    public SafeHandler(Callback callback) {
        super(callback);
    }

    public SafeHandler(Looper looper) {
        super(looper);
    }

    public SafeHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    public SafeHandler(WeakReference<Object> reference) {
        this();
        this.reference = reference;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        Object o = reference.get();
        if (null == o) return;

        safeMessage(o, msg);
    }

    protected void safeMessage(Object obj, Message msg) {

    }
}
