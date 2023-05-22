package com.rongtuoyouxuan.chatlive.base.view.layout;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo;
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.view.activity.BaseLiveStreamActivity;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTGiftMsg;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.libgift.GiftShowManagerV2;
import com.rongtuoyouxuan.libgift.interfaces.IGiftShowManager;
import com.rongtuoyouxuan.libgift.view.layout.GiftShowView;
import com.rongtuoyouxuan.libsocket.base.IMSocketBase;

import java.util.LinkedList;

/**
 * Describe:
 *
 * @author Ning
 * @date 2019/6/26
 */
public class BaseLiveGiftView extends LinearLayout {

    private Observer<RTGiftMsg> giftObserver = new Observer<RTGiftMsg>() {
        @Override
        public void onChanged(@Nullable RTGiftMsg roomMessage) {
            if (!TextUtils.isEmpty(roomMessage.getRoomIdStr())) {
                if (groupCombo.size() > 100) {
                    groupCombo.removeFirst();
                }
                GroupCombo maxCombo = LinkedList_get(groupCombo, roomMessage.getRoomIdStr() + roomMessage.getUserId());
                if (maxCombo != null) {
                    if (roomMessage.getNum() > maxCombo.combo) {
                        maxCombo.combo = roomMessage.getNum();
                    } else {
                        ULog.d("clll", "GroupCombo------");
                        roomMessage.setNum(maxCombo.combo);
                    }
                } else {
                    groupCombo.add(new GroupCombo(roomMessage.getRoomIdStr() + roomMessage.getUserId(), roomMessage.getNum(), System.currentTimeMillis()));
                }
            }
//            mGiftShowManager.add(roomMessage, context);
            mGiftShowManagerV2.add(roomMessage, context);
        }
    };

    private LinearLayout llShowGift;
    private GiftShowView[] giftShowViews;
//    private IGiftShowManager mGiftShowManager;
    private IGiftShowManager mGiftShowManagerV2;
    private Context context;
//    private GiftListViewModel mGiftListViewModel;
    private int originGiftMarginTop = 0;

    class GroupCombo {
        String groupId;
        int combo;
        long time;

        GroupCombo(String groupId, int combo, long time) {
            this.groupId = groupId;
            this.combo = combo;
            this.time = time;
        }
    }

    private LinkedList<GroupCombo> groupCombo = new LinkedList<>();

    private GroupCombo LinkedList_get(LinkedList<GroupCombo> list, String groupId) {
        for (GroupCombo groupCombo : list) {
            if (groupCombo.groupId.equals(groupId)) {
                long curTime = System.currentTimeMillis();
                ULog.e("clll", "time---" + curTime + "--pretime--" + groupCombo.time);
                if((curTime - groupCombo.time) > 3000){
                    groupCombo.combo = 1;
                }else{
                    groupCombo.combo = groupCombo.combo + 1;
                }
                groupCombo.time = curTime;
                return groupCombo;
            }
        }
        return null;
    }

    private IMLiveViewModel imViewModel;

    public BaseLiveGiftView(Context context) {
        this(context, null);
    }

    public BaseLiveGiftView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLiveGiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        imViewModel = ViewModelUtils.get((FragmentActivity) context, IMLiveViewModel.class);
//        mGiftListViewModel = ViewModelProviders.of((FragmentActivity) context).get(GiftListViewModel.class);
        initView(context);
        initManager();
        getHostId();
    }

    private void getHostId() {
        if ((context instanceof BaseLiveStreamActivity) ||
                (context instanceof ContextWrapper && ((ContextWrapper) context).getBaseContext() instanceof BaseLiveStreamActivity)) {
            BaseLiveStreamActivity activity = ((BaseLiveStreamActivity) context);
            LiveStreamInfo liveStreamInfo = activity.getLiveStreamInfo();
            if (!TextUtils.isEmpty(liveStreamInfo.roomId)) {
//                mGiftShowManager.setHostId(liveStreamInfo.hostId);
                mGiftShowManagerV2.setHostId(liveStreamInfo.roomId);
            }
        }
    }

    private void initManager() {
        //显示礼物管理
//        mGiftShowManager = new GiftShowManager(llShowGift);
        mGiftShowManagerV2 = new GiftShowManagerV2(llShowGift, giftShowViews);
//        mGiftListViewModel.giftDlgPosChanged.observeOnce((LifecycleOwner) getContext(), new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) {
//                PLog.e("giftpos", integer + "");
//                View view = ((Activity) getContext()).findViewById(R.id.livegiftview);
//                if (originGiftMarginTop == 0) {
//                    MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
//                    originGiftMarginTop = layoutParams.topMargin;
//                }
//                if (integer > 0) {
//                    int height = view.getHeight();
//                    int marginTop = integer - height;
//                    setLayoutParams(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, marginTop);
//                } else {
//                    int marginTop = originGiftMarginTop;
//                    setLayoutParams(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, marginTop);
//                }
//            }
//        });
    }

    private void initView(Context context) {
        inflate(context, R.layout.pl_stream_layout_small_gift, this);
        llShowGift = findViewById(R.id.ll_show_gift);
        giftShowViews = new GiftShowView[]{findViewById(R.id.giftShowView1), findViewById(R.id.giftShowView2)};
    }

    @Override
    protected void onDetachedFromWindow() {
        IMSocketBase.instance().room(imViewModel.getRoomId()).giftMsg.removeObserver(giftObserver);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    private void setLayoutParams(View view, int width, int height, int topMargin) {
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = topMargin;
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }

    private int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public void setHostId(String roomId) {
        IMSocketBase.instance().room(roomId).giftMsg.observe(giftObserver);
//        mGiftShowManager.setHostId(hostId);
        mGiftShowManagerV2.setHostId(roomId);
    }
}
