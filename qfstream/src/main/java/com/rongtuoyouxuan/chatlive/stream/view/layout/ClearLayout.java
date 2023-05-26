package com.rongtuoyouxuan.chatlive.stream.view.layout;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel;

public class ClearLayout extends RelativeLayout {

    private StreamControllerViewModel mControllerViewModel;

    public ClearLayout(Context context) {
        this(context,null);
    }

    public ClearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.qf_stream_layout_clear,this);
        setVisibility(GONE);
        initView(context);
        initViewModel(context);
    }

    private void initViewModel(Context context) {
        if((FragmentActivity) context instanceof StreamActivity){
            mControllerViewModel = ViewModelUtils.get((FragmentActivity) context, StreamControllerViewModel.class);
        }else{
            mControllerViewModel = ViewModelUtils.getLive(StreamControllerViewModel.class);

        }
        mControllerViewModel.mClearLayoutVisibility.observeOnce((LifecycleOwner) context, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                setVisibility(aBoolean?VISIBLE:GONE);
            }
        });
    }

    private void initView(Context context) {
        findViewById(R.id.ll_clear_screen).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                mControllerViewModel.mControllerVisibility.setValue(true);
            }
        });
    }
}
