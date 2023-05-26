package com.rongtuoyouxuan.chatlive.crtutil.arch;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class LiveCallback<T> {
    private List<Observer<T>> observerList = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Deprecated
    @MainThread
    public void observe(@NonNull Observer<T> observer) {
        observerList.add(observer);
    }

    @MainThread
    public void observeWithReturn(@NonNull ObserverWithReturn<T> observer) {
        observerList.add(observer);
    }

    @MainThread
    public void observe(LifecycleOwner lifecycleOwner,  @NonNull final Observer<T> observer) {
        if (observerList.contains(observer)) {
            return;
//            throw new IllegalArgumentException("LiveCallback contain observer!");
        }
        observerList.add(observer);
        if (lifecycleOwner != null) {
            Lifecycle lifecycle = lifecycleOwner.getLifecycle();
            LifecycleEventObserver lifecycleEventObserver = (LifecycleEventObserver) (source, event) -> {
                if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                    observerList.remove(observer);
                }
            };
            lifecycle.removeObserver(lifecycleEventObserver);
            lifecycle.addObserver(lifecycleEventObserver);
        }
    }

    @MainThread
    public void removeObserver(Observer<T> observer) {
        observerList.remove(observer);
    }

    public void setValue(T value) {
        if (value == null) {
            return;
        }

        if (isMainThread()) {
            dispatch(value);
        } else {
            postValue(value);
        }
    }

    public void call(){
        if (isMainThread()) {
            dispatch(null);
        } else {
            postValue(null);
        }
    }

    @MainThread
    public T callWithReturn(Object obj) {
        if (isMainThread()) {
            return dispatchWithReturn(obj);
        }
        return null;
    }

    private boolean isMainThread() {
        return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
    }

    public void postValue(final T value) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                dispatch(value);
            }
        });
    }

    private void dispatch(T value) {
        for (Observer<T> observer : observerList) {
            observer.onChanged(value);
        }
    }

    private T dispatchWithReturn(Object obj) {
        for (Observer<T> observer : observerList) {
            if (observer instanceof ObserverWithReturn) {
                ObserverWithReturn<T> observerWithReturn = (ObserverWithReturn<T>)observer;
                return observerWithReturn.getValue(obj);
            }
        }
        return null;
    }

    public interface ObserverWithReturn<T> extends Observer<T> {
        T getValue(Object obj);
    }
}
