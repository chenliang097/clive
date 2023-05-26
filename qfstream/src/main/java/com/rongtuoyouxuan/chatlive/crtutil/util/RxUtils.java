package com.rongtuoyouxuan.chatlive.crtutil.util;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public final class RxUtils {

    private RxUtils() {
    }

    //Disposable
    public static void disposable(Disposable subscription) {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    public static void disposableList(ArrayList<Disposable> list) {
        if (list == null || list.size() == 0) return;
        for (Disposable disposable : list) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}