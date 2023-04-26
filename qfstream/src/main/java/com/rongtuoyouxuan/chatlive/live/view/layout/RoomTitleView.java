package com.rongtuoyouxuan.chatlive.live.view.layout;

import android.content.Context;
import android.util.AttributeSet;

import com.rongtuoyouxuan.chatlive.base.view.layout.BaseRoomTitleView;

public class RoomTitleView extends BaseRoomTitleView {

//    private LiveControllerViewModel liveControllerViewModel;

    public RoomTitleView(Context context) {
        super(context);
    }

    public RoomTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        initData(context);
    }

    @Override
    public void initViewModel(Context context) {
//        liveControllerViewModel = ViewModelUtils.get((FragmentActivity) context, LiveControllerViewModel.class);
    }

    private void initData(Context context) {
//        liveControllerViewModel.getRoomInfoLiveData().observe((LifecycleOwner) context, new Observer<LiveRoomBean>() {
//            @Override
//            public void onChanged(@Nullable LiveRoomBean liveRoomBean) {
//                if (liveRoomBean != null && liveRoomBean.getData() != null && liveRoomBean.getData().getLive() != null) {
//                    setTitel(liveRoomBean.getData().getLive().getTitle());
//                }
//            }
//        });
    }
}
