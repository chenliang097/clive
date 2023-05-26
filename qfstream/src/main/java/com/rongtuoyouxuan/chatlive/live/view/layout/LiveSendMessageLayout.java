package com.rongtuoyouxuan.chatlive.live.view.layout;

import android.content.Context;
import android.util.AttributeSet;

import androidx.fragment.app.FragmentActivity;

import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.view.layout.RoomSendMessageLayout;
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveControllerViewModel;
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveControllerViewModel;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;

public class LiveSendMessageLayout extends RoomSendMessageLayout {
    public LiveSendMessageLayout(Context context) {
        super(context);
    }

    public LiveSendMessageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveSendMessageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initViewModel(Context context) {
        if((FragmentActivity) context instanceof StreamActivity){
            mControllerViewModel = ViewModelUtils.get((FragmentActivity) context, LiveControllerViewModel.class);
        }else{
            mControllerViewModel = ViewModelUtils.getLive(LiveControllerViewModel.class);

        }
    }
}
