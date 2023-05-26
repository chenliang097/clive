package com.rongtuoyouxuan.chatlive.stream.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel;
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamViewModel;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class NetworkSpeedView extends RelativeLayout {

    private TextView netspeed;
    private TextView livestate;
    private StreamViewModel mStreamViewModel;
    private TextView socketstate;
    private StreamControllerViewModel mControllerViewModel;
    private IMLiveViewModel imViewModel;

    public NetworkSpeedView(Context context) {
        this(context,null);
    }

    public NetworkSpeedView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NetworkSpeedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStreamViewModel = ViewModelUtils.get((FragmentActivity) context, StreamViewModel.class);
        mControllerViewModel = ViewModelUtils.get((FragmentActivity) context, StreamControllerViewModel.class);
        imViewModel = ViewModelUtils.get((FragmentActivity) context, IMLiveViewModel.class);
        initView(context);
        initState(context);
    }

    private void initView(Context context) {
        inflate(getContext(), R.layout.qf_stream_layout_network_speed_view, this);
        setVisibility(GONE);
        livestate = findViewById(R.id.tv_host_room_publish_state);
        socketstate = findViewById(R.id.tv_host_room_socket_state);
        netspeed = findViewById(R.id.tv_host_room_net_speed);

        // TODO 差直播其他状态回调
        livestate.setText(getResources().getString(R.string.socket_state_disconnect));
        netspeed.setTextColor(getResources().getColor(R.color.white));
        netspeed.setShadowLayer(1.0f, 1.0f, 1.0f, getResources().getColor(R.color.c_cccccc));

        //TODO 差长链状态回调
        socketstate.setText(String.format(getResources().getString(R.string.socket_state_connect_format),getResources().getString(R.string.socket_state_connecting)));
    }

    private void initState(Context context) {
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer val) {
                if (val <= 50) {
                    netspeed.setTextColor(getResources().getColor(R.color.c_fc3838));
                    netspeed.setShadowLayer(1.0f, 1.0f, 1.0f, getResources().getColor(R.color.c_ff4c83));
                } else {
                    netspeed.setTextColor(getResources().getColor(R.color.white));
                    netspeed.setShadowLayer(1.0f, 1.0f, 1.0f, getResources().getColor(R.color.c_cccccc));
                }
                livestate.setText(getResources().getString(R.string.socket_state_success));
            }
        };
        mStreamViewModel.getNetworkSpeed().observe((LifecycleOwner) context, observer);

        imViewModel.joinGroupSuccess.observeOnce((LifecycleOwner) context, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                socketstate.setText(String.format(getResources().getString(R.string.socket_state_connect_format),getResources().getString(R.string.socket_state_normal)));
            }
        });

        imViewModel.joinGroupFail.observeOnce((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                socketstate.setText(String.format(getResources().getString(R.string.socket_state_connect_format),getResources().getString(R.string.socket_state_disconnect)));
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("chatFail","1");

            }
        });

        mControllerViewModel.mNetworkSpeedVisibility.observeOnce((LifecycleOwner) context, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    setVisibility(VISIBLE);
                } else {
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
                    layoutParams.topMargin = 180;
                    setLayoutParams(layoutParams);
                    livestate.setVisibility(GONE);
                    socketstate.setVisibility(GONE);
                }
            }
        });


    }
}
