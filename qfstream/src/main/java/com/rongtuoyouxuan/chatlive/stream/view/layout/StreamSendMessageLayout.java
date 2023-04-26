package com.rongtuoyouxuan.chatlive.stream.view.layout;

import android.content.Context;
import android.util.AttributeSet;

import androidx.fragment.app.FragmentActivity;

import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.view.layout.RoomSendMessageLayout;
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel;

public class StreamSendMessageLayout extends RoomSendMessageLayout {

    public StreamSendMessageLayout(Context context) {
        super(context);
    }

    public StreamSendMessageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StreamSendMessageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initViewModel(Context context) {
        mControllerViewModel = ViewModelUtils.get((FragmentActivity) context, StreamControllerViewModel.class);
    }

}
