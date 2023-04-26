package com.rongtuoyouxuan.chatlive.base.view.layout.bradcastlayout;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import androidx.fragment.app.FragmentActivity;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomBannerGift;
import com.rongtuoyouxuan.chatlive.databus.DataBus;
import com.rongtuoyouxuan.chatlive.databus.config.ConfigManager;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.zhihu.matisse.dialog.DiySystemDialog;

/**
 * 描述：
 *
 * @time 2019/9/26
 */
public class LuckyCommonBroadcastGiftLayout extends BaseBroadcastItemLayout implements IBaseBradcastGiftLayout {

    private TextView tvMsg;
    private String giftName;
    private IMLiveViewModel imViewModel;

    public LuckyCommonBroadcastGiftLayout(Context context) {
        this(context,null);
    }

    public LuckyCommonBroadcastGiftLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LuckyCommonBroadcastGiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initViewModel();
        inflate(context, R.layout.qf_stream_layout_broadcast_gift_lucky_common,this);
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
        tvMsg = findViewById(R.id.tx_lucky_text);
    }



    public void showOutDialog(final RoomBannerGift radioInfo){
        DiySystemDialog.Builder dialog = new DiySystemDialog.Builder(getContext());
        dialog.setMessage(String.format(getContext().getString(R.string.app_name),radioInfo.to.nick));
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        dialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (radioInfo != null && radioInfo.to != null && radioInfo.to.uid > 0) {
//                    Router.toLiveRoomActivity(String.valueOf(radioInfo.to.uid),"", "", ISource.CLICK_BROADCAST);
                }
            }
        });
        dialog.create().show();
    }

    public void bindData(final RoomBannerGift radioInfo) {
        ConfigManager.GiftLocalInfo giftDesc = DataBus.instance().getConfigMananger().getGiftDesc(radioInfo.giftid);
        giftName = giftDesc.name;
        SpannableStringBuilder builder = new SpannableStringBuilder();
        try {
            addTextSpan(builder,String.format(getResources().getString(R.string.app_name),radioInfo.from.nick,radioInfo.to.nick,giftName,radioInfo.count), Color.parseColor("#f3f78b"),null);
        } catch (Throwable e) {

        }
        tvMsg.setText(builder);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioInfo.to != null && radioInfo.to.uid > 0 && !imViewModel.isHostId(radioInfo.to.uid)) {
                    showOutDialog(radioInfo);
                }
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
