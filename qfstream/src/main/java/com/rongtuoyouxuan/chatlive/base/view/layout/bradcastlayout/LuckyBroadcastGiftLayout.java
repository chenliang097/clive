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
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.im.LuckyBroadcastModel;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.rongtuoyouxuan.chatlive.stream.R;

import java.text.DecimalFormat;

public class LuckyBroadcastGiftLayout extends BaseBroadcastItemLayout implements IBaseBradcastGiftLayout {

    private TextView textView;
    private IMLiveViewModel imViewModel;

    public LuckyBroadcastGiftLayout(Context context) {
        this(context,null);
    }

    public LuckyBroadcastGiftLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LuckyBroadcastGiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initViewModel(context);
        inflate(context, R.layout.qf_stream_layout_broadcast_gift_lucky,this);
        initView(context);
    }

    private void initViewModel(Context context) {
        if((FragmentActivity) getContext() instanceof StreamActivity){
            imViewModel = ViewModelUtils.get((FragmentActivity) getContext(), IMLiveViewModel.class);
        }else{
            imViewModel = ViewModelUtils.getLive(IMLiveViewModel.class);

        }
    }

    private void initView(Context context) {
        textView = findViewById(R.id.tx_lucky_text);
    }

    public void bindData(LuckyBroadcastModel model) {
        String format = new DecimalFormat("#,###").format(model.score);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        try {
            addTextSpan(builder,String.format(getResources().getString(R.string.app_name),format),getResources().getColor(R.color.white),null);
            addTextSpan(builder,getResources().getString(R.string.app_name), Color.parseColor("#f3f78b"),null);
        } catch (Throwable e) {

        }
        textView.setText(builder);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void setTextSelected(boolean selected) {
        textView.setSelected(true);
    }

    @Override
    public void release() {
        setOnClickListener(null);
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
    }
}
