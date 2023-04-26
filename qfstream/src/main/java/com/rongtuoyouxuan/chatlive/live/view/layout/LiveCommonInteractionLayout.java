package com.rongtuoyouxuan.chatlive.live.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.ntfmsg.BannedMsg;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.rongtuoyouxuan.chatlive.util.UIUtils;

public class LiveCommonInteractionLayout extends RelativeLayout {
    private LinearLayout.LayoutParams layoutParams;
    private IMLiveViewModel imViewModel;

    public LiveCommonInteractionLayout(Context context) {
        this(context, null);
    }

    public LiveCommonInteractionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveCommonInteractionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private Observer<BannedMsg> bannedObserver = new Observer<BannedMsg>() {
        @Override
        public void onChanged(BannedMsg bannedMsg) {
//            if(bannedMsg.bannedType == 1){
//                setVisibility(GONE);
//            }else{
//                setVisibility(VISIBLE);
//            }
        }
    };

    private void init(Context context) {
        inflate(context, R.layout.qf_stream_live_layout_intercation_common, this);
        if((FragmentActivity) context instanceof StreamActivity){
            imViewModel = ViewModelUtils.get((FragmentActivity) context, IMLiveViewModel.class);
        }else{
            imViewModel = ViewModelUtils.getLive(IMLiveViewModel.class);

        }

        layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        imViewModel.streamIdLiveEvent.observe((FragmentActivity) context, new Observer<String>(){
            @Override
            public void onChanged(String s) {
                registerObserver(s);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        imViewModel.streamIdLiveEvent.observe((LifecycleOwner) getContext(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                setVisibility(VISIBLE);

            }
        });
        imViewModel.showPanel.observe((LifecycleOwner) getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (layoutParams != null) {
                    if (aBoolean) {
                        layoutParams.bottomMargin = (int) getContext().getResources().getDimension(R.dimen.dp_4);
                        setVisibility(VISIBLE);
                    } else {
                        layoutParams.bottomMargin = (int) getContext().getResources().getDimension(R.dimen.dp_55);
                        setVisibility(VISIBLE);
                    }
                    setLayoutParams(layoutParams);
                }
            }
        });

        imViewModel.showIGifPannelLiveEvent.observe((LifecycleOwner) getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (layoutParams != null) {
                    if (aBoolean) {
                        layoutParams.bottomMargin = (int) UIUtils.dip2px(getContext(), 290);
                        setVisibility(VISIBLE);
                    } else {
                        layoutParams.bottomMargin = (int) getContext().getResources().getDimension(R.dimen.dp_55);
                        setVisibility(VISIBLE);
                    }
                    setLayoutParams(layoutParams);
                }
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unRegisterObserver(imViewModel.getStreamId());
    }

    private void registerObserver(String streamId) {
//        IMSocketImpl.getInstance().chatRoom(streamId).bannedCallback.observe(bannedObserver);
    }

    private void unRegisterObserver(String streamId) {
//        IMSocketImpl.getInstance().chatRoom(streamId).bannedCallback.removeObserver(bannedObserver);
    }
}
