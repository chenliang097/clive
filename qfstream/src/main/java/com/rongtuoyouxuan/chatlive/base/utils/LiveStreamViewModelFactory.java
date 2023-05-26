package com.rongtuoyouxuan.chatlive.base.utils;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.rongtuoyouxuan.chatlive.base.viewmodel.LiveBaseViewModel;
import com.rongtuoyouxuan.chatlive.base.viewmodel.LiveBaseViewModel;
import com.rongtuoyouxuan.chatlive.base.viewmodel.LiveBaseViewModel;

import java.lang.reflect.InvocationTargetException;

public class LiveStreamViewModelFactory implements ViewModelProvider.Factory {
    private LiveStreamInfo liveStreamInfo;

    public LiveStreamViewModelFactory(LiveStreamInfo liveStreamInfo) {
        this.liveStreamInfo = liveStreamInfo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (LiveBaseViewModel.class.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(LiveStreamInfo.class).newInstance(liveStreamInfo);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
