package com.rongtuoyouxuan.chatlive.stream.view.layout;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.AttributeSet;

import com.rongtuoyouxuan.chatlive.base.view.layout.BaseRoomTitleView;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StartStreamBean;
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamViewModel;

public class StreamRoomTitleView extends BaseRoomTitleView {

    private StreamViewModel streamViewModel;

    public StreamRoomTitleView(Context context) {
        super(context);
    }

    public StreamRoomTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StreamRoomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        initData(context);
    }

    private void initData(Context context) {
        streamViewModel.startStreamModel.observe((LifecycleOwner) context, new Observer<StartStreamBean>() {
            @Override
            public void onChanged(@Nullable StartStreamBean startStreamModel) {
            }
        });
    }

    @Override
    public void initViewModel(Context context) {
        streamViewModel = ViewModelUtils.get((FragmentActivity) context, StreamViewModel.class);
    }
}
