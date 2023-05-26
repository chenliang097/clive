package com.rongtuoyouxuan.chatlive.base.view.layout.bradcastlayout;

import android.content.Context;
import android.graphics.Color;
import androidx.fragment.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.im.BlastBroadcastGiftModel;
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus;
import com.rongtuoyouxuan.chatlive.crtdatabus.config.ConfigManager;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;

public class BlastBroadcastGiftLayout extends BaseBroadcastItemLayout implements IBaseBradcastGiftLayout {

    private TextView tvMsg;
    private String giftName;
    private IMLiveViewModel imViewModel;

    public BlastBroadcastGiftLayout(Context context) {
        this(context,null);
    }

    public BlastBroadcastGiftLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BlastBroadcastGiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initViewModel();
        inflate(context, R.layout.qf_stream_layout_broadcast_gift_blast,this);
        initView(context);
    }

    private void initViewModel() {
        if((FragmentActivity) getContext() instanceof StreamActivity){
            imViewModel = ViewModelUtils.get((FragmentActivity) getContext(), IMLiveViewModel.class);
        }else{
            imViewModel = ViewModelUtils.getLive(IMLiveViewModel.class);

        }
    }

    private void initView(Context context) {
        tvMsg = findViewById(R.id.tx_blast_text);
    }


    public void bindData(final BlastBroadcastGiftModel model) {
        ConfigManager.GiftLocalInfo giftDesc = DataBus.instance().getConfigMananger().getGiftDesc(model.gift_id);
        giftName = giftDesc.name;
        SpannableStringBuilder builder = new SpannableStringBuilder();
        try {
            addTextSpan(builder,String.format(getResources().getString(R.string.app_name),
                    model.nick,giftName), Color.parseColor("#9b7738"),null);
        }catch (Exception e) {

        }
        tvMsg.setText(builder);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void setTextSelected(boolean selected) {
        tvMsg.setSelected(selected);
    }

    @Override
    public void release(){
        tvMsg.setText("");
        setOnClickListener(null);
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
    }

}
