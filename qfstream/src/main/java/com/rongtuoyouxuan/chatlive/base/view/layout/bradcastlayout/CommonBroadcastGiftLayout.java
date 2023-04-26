package com.rongtuoyouxuan.chatlive.base.view.layout.bradcastlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveMarqueeEntity;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomBannerGift;
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils;
import com.rongtuoyouxuan.chatlive.stream.R;

/**
 * 描述：
 *
 * @time 2019/9/26
 */
public class CommonBroadcastGiftLayout extends BaseBroadcastItemLayout implements IBaseBradcastGiftLayout {

    private ConstraintLayout rootView;
    private TextView tvFromUserName;
    private TextView tvFromUserName2;
    private TextView tvSendTip;
    private TextView tvFToUserName;
    private TextView tvFToUserName2;
    private ImageView ivGiftImage;
    private ImageView ivLuckyImage;
    private TextView tvGiftNum;

    public CommonBroadcastGiftLayout(Context context) {
        this(context, null);
    }

    public CommonBroadcastGiftLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonBroadcastGiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setClipChildren(false);
        inflate(context, R.layout.qf_stream_layout_broadcast_gift_common, this);
        initView(context);
    }

    private void initView(Context context) {
        rootView = findViewById(R.id.rl_visitor_in);
        tvFromUserName = findViewById(R.id.tvFromUserName);
        tvFromUserName2 = findViewById(R.id.tvFromUserName2);
        tvSendTip = findViewById(R.id.tvSendTip);
        tvFToUserName = findViewById(R.id.tvFToUserName);
        tvFToUserName2 = findViewById(R.id.tvFToUserName2);
        ivGiftImage = findViewById(R.id.ivGiftImage);
        ivLuckyImage = findViewById(R.id.ivLuckyImage);
        tvGiftNum = findViewById(R.id.tvGiftNum);
    }

    @SuppressLint("SetTextI18n")
    public void bindData(final RoomBannerGift radioInfo) {
        if (null == radioInfo) {
            return;
        }
        if (null != radioInfo.from) {
            if (radioInfo.type == 2) {
                tvFromUserName2.setVisibility(View.VISIBLE);
                tvFromUserName2.setText(getContext().getString(R.string.live_game_marquee_tip_1));
            }
            if (!TextUtils.isEmpty(radioInfo.from.nick)) {
                tvFromUserName.setText(radioInfo.from.nick);
            } else {
                tvFromUserName.setText("");
            }
        }

        if (radioInfo.type == 0) {
            //世界礼物
            tvSendTip.setText(R.string.stream_user_card_send);

            if (null != radioInfo.to) {
                if (!TextUtils.isEmpty(radioInfo.to.nick)) {
                    tvFToUserName.setText(radioInfo.to.nick);
                } else {
                    tvFToUserName.setText("");
                }
            }

            ivGiftImage.setVisibility(View.VISIBLE);
            ivLuckyImage.setVisibility(View.GONE);
            GlideUtils.loadImage(getContext(), radioInfo.giftUrl, ivGiftImage);

            tvGiftNum.setText("x" + radioInfo.count);
            tvGiftNum.setVisibility(View.VISIBLE);

            rootView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    LiveMarqueeEntity entity = new LiveMarqueeEntity(radioInfo.roomId,
                            0, "", "",
                            false);
                    LiveRoomHelper.INSTANCE.getLiveMarqueeClick().post(entity);
                }
            });
        } else {
            tvSendTip.setText(R.string.stream_user_card_send);

            if (null != radioInfo.to) {
                if (!TextUtils.isEmpty(radioInfo.to.nick)) {
                    tvFToUserName.setText(radioInfo.to.nick);
                } else {
                    tvFToUserName.setText("");
                }
            }

            ivGiftImage.setVisibility(View.VISIBLE);
            ivLuckyImage.setVisibility(View.GONE);
            GlideUtils.loadImage(getContext(), radioInfo.giftUrl, ivGiftImage);

            tvGiftNum.setText("x" + radioInfo.count);
            tvGiftNum.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTextSelected(boolean selected) {

    }

    @Override
    public void release() {
        if (null != tvFromUserName) {
            tvFromUserName.setText("");
        }
        if (null != tvFToUserName) {
            tvFToUserName.setText("");
        }
        if (null != ivGiftImage) {
            ivGiftImage.setImageBitmap(null);
        }
        if (null != tvGiftNum) {
            tvGiftNum.setText("");
        }
        setOnClickListener(null);
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
    }

}
