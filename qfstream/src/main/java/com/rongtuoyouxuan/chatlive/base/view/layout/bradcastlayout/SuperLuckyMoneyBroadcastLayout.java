package com.rongtuoyouxuan.chatlive.base.view.layout.bradcastlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper;
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveMarqueeEntity;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.SuperLuckyMoneyBroadcastModel;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;

public class SuperLuckyMoneyBroadcastLayout extends BaseBroadcastItemLayout implements IBaseBradcastGiftLayout {

    private TextView tvMsg;
    private IMLiveViewModel imViewModel;

    public SuperLuckyMoneyBroadcastLayout(Context context) {
        this(context, null);
    }

    public SuperLuckyMoneyBroadcastLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperLuckyMoneyBroadcastLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initViewModel();
        inflate(context, R.layout.qf_stream_layout_broadcast_super_lucky_money, this);
        initView(context);
    }

    private void initViewModel() {
        if ((FragmentActivity) getContext() instanceof StreamActivity) {
            imViewModel = ViewModelUtils.get((FragmentActivity) getContext(), IMLiveViewModel.class);
        } else {
            imViewModel = ViewModelUtils.getLive(IMLiveViewModel.class);
        }
    }

    private void initView(Context context) {
        tvMsg = findViewById(R.id.tx_blast_text);
    }


    public void bindData(final SuperLuckyMoneyBroadcastModel model) {
        if (model == null) {
            return;
        }
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveMarqueeEntity entity = new LiveMarqueeEntity(model.roomId, 0, "", "",
                        false);
                LiveRoomHelper.INSTANCE.getLiveMarqueeClick().post(entity);
            }
        });
    }

    @Override
    public void setTextSelected(boolean selected) {
//        tvMsg.setSelected(selected);
    }

    @Override
    public void release() {
        tvMsg.setText("");
        setOnClickListener(null);
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
    }

}
