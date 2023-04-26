package com.rongtuoyouxuan.chatlive.base.utils;

import com.rongtuoyouxuan.chatlive.base.viewmodel.LiveBaseViewModel;
import com.rongtuoyouxuan.chatlive.live.view.activity.LiveRoomActivity;
import com.rongtuoyouxuan.chatlive.live.view.fragment.LiveRoomFragment;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.rongtuoyouxuan.libuikit.SimpleFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class ViewModelUtils {

    private static SimpleFragment mLiveRoomFragment;

    public static <T extends ViewModel> T get(FragmentActivity activity, @NonNull Class<T> modelClass) {
        if (LiveBaseViewModel.class.isAssignableFrom(modelClass)) {
            return  ViewModelProviders.of(activity, new LiveStreamViewModelFactory(getLiveStreamInfo(activity))).get(modelClass);
        } else {
            return  ViewModelProviders.of(activity).get(modelClass);
        }
    }

    private static LiveStreamInfo getLiveStreamInfo(FragmentActivity activity) {
        LiveStreamInfo liveStreamInfo;
        if (activity instanceof StreamActivity) {
            liveStreamInfo = ((StreamActivity)activity).getLiveStreamInfo();
        } else if (activity instanceof LiveRoomActivity) {
            liveStreamInfo = ((LiveRoomActivity)activity).getLiveStreamInfo();
        }else {
            throw new IllegalArgumentException("activity is error!");
        }
        return liveStreamInfo;
    }

    public static void setLiveFragment(SimpleFragment activity) {
        mLiveRoomFragment = activity;
    }
    public static <T extends ViewModel> T getLive(@NonNull Class<T> modelClass) {
        if (LiveBaseViewModel.class.isAssignableFrom(modelClass)) {
            return  new ViewModelProvider(mLiveRoomFragment, new LiveStreamViewModelFactory(getLiveLiveStreamInfo(mLiveRoomFragment))).get(modelClass);
        } else {
            return  new ViewModelProvider(mLiveRoomFragment).get(modelClass);
        }
    }

    private static LiveStreamInfo getLiveLiveStreamInfo(SimpleFragment activity) {
        LiveStreamInfo liveStreamInfo = null;
        if(activity instanceof LiveRoomFragment){
            liveStreamInfo = ((LiveRoomFragment)activity).getLiveStreamInfo();
        }
//        }else {
//            throw new IllegalArgumentException("activity is error!");
//        }
        return liveStreamInfo;
    }
}
