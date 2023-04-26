package com.rongtuoyouxuan.chatlive.arch;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class LiveEvent<T> {
    private MutableLiveData<Event<T>> mData;

    public LiveEvent(T value) {
        mData = new MutableLiveData<>();
        mData.setValue(new Event<>(value, true));
    }

    public LiveEvent() {
        mData = new MutableLiveData<>();
    }

    public void postValue(T value) {
        mData.postValue(new Event<>(value, false));
    }

    public void postCall() {
        mData.postValue(new Event<>((T)null, false));
    }

    public void call() {
        mData.setValue(new Event<>((T)null, false));
    }

    public T getValue() {
        return mData.getValue().peekContent();
    }

    public void setValue(T value) {
        mData.setValue(new Event<>(value, false));
    }

    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<T> observer) {
        mData.observe(owner, new Observer<Event<T>>() {
            @Override
            public void onChanged(@Nullable Event<T> event) {
                if (event != null) {
                    if (event.getIsInit()) {
                        return;
                    }
                    observer.onChanged(event.peekContent());
                }
            }
        });
    }

    @MainThread
    public void observeOnce(@NonNull LifecycleOwner owner, @NonNull final Observer<T> observer) {
        mData.observe(owner, new Observer<Event<T>>() {
            @Override
            public void onChanged(@Nullable Event<T> event) {
                if (event != null) {
                    if (event.getIsInit()) {
                        return;
                    }
                    if (event.getHasBeenHandled()) {
                        return;
                    }

                    event.setHasBeenHandled();
                    observer.onChanged(event.peekContent());
                }
            }
        });
    }
}