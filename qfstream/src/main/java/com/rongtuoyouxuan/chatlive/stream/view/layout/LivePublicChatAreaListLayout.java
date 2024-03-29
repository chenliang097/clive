package com.rongtuoyouxuan.chatlive.stream.view.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.GiftMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTAnnounceMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTBannedMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTBannedRelieveMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTEnterRoomMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTFollowMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTLeaveRoomMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTRoomManagerAddMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTRoomManagerRelieveMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTTxtMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.TxtMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.live.LiveRoomBean;
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus;
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveControllerViewModel;
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog;
import com.rongtuoyouxuan.chatlive.qfcommon.dialog.ShareDialog;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.rongtuoyouxuan.chatlive.stream.view.adapter.LivePublicChatAreaListAdapter;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil;
import com.rongtuoyouxuan.chatlive.libsocket.base.IMSocketBase;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.MessageContent;
import com.rongtuoyouxuan.chatlive.crtuikit.widget.divider.HorizontalDividerItemDecoration;

import java.util.List;

public class LivePublicChatAreaListLayout extends RelativeLayout {

    private RecyclerView mRecyclerView;
    private boolean mCanAutoScroll = true; //自动滑动
    private LinearLayoutManager linearLayoutManager;
    private LivePublicChatAreaListAdapter danmakuListAdapter;
    private View mNewMessage;
    private IMLiveViewModel imViewModel;
    private String roomId = "";
    private Context mContext;
    //    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private int newMessageNum = 0;
    private TextView newMessageNumTxt;
    private LiveControllerViewModel mControllerViewModel;
    private LiveRoomBean liveRoomBean;
    private ShareDialog shareDialog;
    private String shareUrl = "";
    /**
     * 聊天列表渐变
     */
    private int layerId;

    private Observer<RTTxtMsg> textObserver = new Observer<RTTxtMsg>() {
        @Override
        public void onChanged(@Nullable RTTxtMsg txtMsg) {
            ULog.d("clll", "textObserver：" + txtMsg.getContent());
            txtMsg.setType(2);
            addMessageToList(txtMsg);
        }
    };

    private Observer<RTAnnounceMsg> announceObserver = new Observer<RTAnnounceMsg>() {
        @Override
        public void onChanged(@Nullable RTAnnounceMsg announceMsg) {
            addMessageToList(announceMsg);
        }
    };

    private Observer<RTEnterRoomMsg> enterObserver = new Observer<RTEnterRoomMsg>() {
        @Override
        public void onChanged(@Nullable RTEnterRoomMsg enterRoomMsg) {
            addMessageToList(enterRoomMsg);
        }
    };

    private Observer<RTFollowMsg> followObserver = new Observer<RTFollowMsg>() {
        @Override
        public void onChanged(@Nullable RTFollowMsg followMsg) {
            addMessageToList(followMsg);
        }
    };

    private Observer<RTLeaveRoomMsg> leaveObserver = new Observer<RTLeaveRoomMsg>() {
        @Override
        public void onChanged(@Nullable RTLeaveRoomMsg leaveRoomMsg) {
            addMessageToList(leaveRoomMsg);
        }
    };

    private Observer<RTBannedMsg> bannedObserver = new Observer<RTBannedMsg>() {
        @Override
        public void onChanged(@Nullable RTBannedMsg bannedMsg) {
            LaToastUtil.showShort(mContext.getString(R.string.stream_msg_banner));
//            addMessageToList(bannedMsg);
        }
    };

    private Observer<RTBannedRelieveMsg> bannedRelieveObserver = new Observer<RTBannedRelieveMsg>() {
        @Override
        public void onChanged(@Nullable RTBannedRelieveMsg bannedRelieveMsg) {
            LaToastUtil.showShort(mContext.getString(R.string.stream_msg_banner_relieve));
//            addMessageToList(bannedRelieveMsg);
        }
    };

    private Observer<RTRoomManagerAddMsg> managerAObserver = new Observer<RTRoomManagerAddMsg>() {
        @Override
        public void onChanged(@Nullable RTRoomManagerAddMsg roomManagerAddMsg) {
            LaToastUtil.showShort(mContext.getString(R.string.stream_msg_room_manager));
            imViewModel.getRoomManagerLiveData().setValue(true);
//            addMessageToList(roomManagerAddMsg);
        }
    };

    private Observer<RTRoomManagerRelieveMsg> managerRelieveObserver = new Observer<RTRoomManagerRelieveMsg>() {
        @Override
        public void onChanged(@Nullable RTRoomManagerRelieveMsg rtRoomManagerRelieveMsg) {
            LaToastUtil.showShort(mContext.getString(R.string.stream_msg_room_manager_relieve));
//            addMessageToList(rtRoomManagerRelieveMsg);
            imViewModel.getRoomManagerLiveData().setValue(false);
        }
    };

    private Observer<GiftMsg> giftObserver = new Observer<GiftMsg>() {
        @Override
        public void onChanged(@Nullable GiftMsg giftMsg) {
//            addMessageToList(baseMsg);
        }
    };

    public LivePublicChatAreaListLayout(Context context) {
        this(context, null);
    }

    public LivePublicChatAreaListLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LivePublicChatAreaListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        if(!TextUtils.isEmpty(imViewModel.getStreamId())){
//            registerObserver(imViewModel.getStreamId());
//        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!TextUtils.isEmpty(roomId)) {
            unregisterObserver(roomId);
        }

    }

    private void registerObserver(String streamId) {
        roomId = streamId;
        IMSocketBase.instance().setCurUserId(DataBus.instance().USER_ID);
        IMSocketBase.instance().room(streamId).chmsg.observe(textObserver);
        IMSocketBase.instance().room(streamId).announceMsg.observe(announceObserver);
        IMSocketBase.instance().room(streamId).enterRoomMsg.observe(enterObserver);
        IMSocketBase.instance().room(streamId).followMsg.observe(followObserver);
        IMSocketBase.instance().room(streamId).leaveRoomMsg.observe(leaveObserver);
        IMSocketBase.instance().room(DataBus.instance().USER_ID).bannerMsg.observe(bannedObserver);
        IMSocketBase.instance().room(DataBus.instance().USER_ID).bannerRelieveMsg.observe(bannedRelieveObserver);
        IMSocketBase.instance().room(DataBus.instance().USER_ID).roomManagerAddMsg.observe(managerAObserver);
        IMSocketBase.instance().room(DataBus.instance().USER_ID).roomManagerRelieveMsg.observe(managerRelieveObserver);
    }

    private void unregisterObserver(String streamId) {
        IMSocketBase.instance().room(streamId).chmsg.removeObserver(textObserver);
        IMSocketBase.instance().room(streamId).announceMsg.removeObserver(announceObserver);
        IMSocketBase.instance().room(streamId).enterRoomMsg.removeObserver(enterObserver);
        IMSocketBase.instance().room(streamId).followMsg.removeObserver(followObserver);
        IMSocketBase.instance().room(streamId).leaveRoomMsg.removeObserver(leaveObserver);
        IMSocketBase.instance().room(DataBus.instance().USER_ID).bannerMsg.removeObserver(bannedObserver);
        IMSocketBase.instance().room(DataBus.instance().USER_ID).bannerRelieveMsg.removeObserver(bannedRelieveObserver);
        IMSocketBase.instance().room(DataBus.instance().USER_ID).roomManagerAddMsg.removeObserver(managerAObserver);
        IMSocketBase.instance().room(DataBus.instance().USER_ID).roomManagerRelieveMsg.removeObserver(managerRelieveObserver);

    }


    private void init(Context context) {
        initViewModel(context);
        inflate(context, R.layout.qf_stream_layout_chatlist, this);
        initView(context);
        initData(context);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initData(Context context) {
        imViewModel.systemMsgLiveEvent.observeOnce((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String content) {
                if (TextUtils.isEmpty(content)) return;
                ULog.d("clll", "textObserver：" + content);
                RTTxtMsg txtMsg = new RTTxtMsg();
                txtMsg.setType(1);
                txtMsg.setContent(content);
//                MessageContent messageContent = MessageContent.fromString(MsgActions.ACTION_TEXT);
                addMessageToList(txtMsg);
            }
        });

        imViewModel.isShowChatView.observe((LifecycleOwner) context, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    mRecyclerView.setVisibility(VISIBLE);
                } else {
                    newMessageNum = 0;
                    mNewMessage.setVisibility(INVISIBLE);
                    mRecyclerView.setVisibility(INVISIBLE);
                }
            }
        });

        imViewModel.joinGroupSuccess.observe((LifecycleOwner) context, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
//                addMessageToList(getLiveConvention("Connect Success"));
//                imViewModel.sendEnterRoom();

            }
        });

        imViewModel.joinGroupFail.observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String string) {

//                addMessageToList(getLiveConvention("Connect fail " + string));
            }
        });

        imViewModel.streamIdLiveEvent.observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                registerObserver(s);
            }
        });

        imViewModel.selfSendTxtLiveEvent.observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (mNewMessage.getVisibility() == VISIBLE) {
                    mNewMessage.setVisibility(GONE);
                    toBottom();
                }
            }
        });

        //获取分享Url
        imViewModel.shareUrlLiveEvent.observe((LifecycleOwner) context, s -> shareUrl = s);

    }

    private BaseMsg getLiveConvention(String content) {
        BaseMsg baseMsg = new BaseMsg();
//        MessageContent messageContent = MessageContent.fromString(MsgActions.ACTION_TEXT);
        baseMsg.messageType = MessageContent.MSG_TEXT.type;
        TxtMsg txtMsg = new TxtMsg();
        txtMsg.type = 1;
        txtMsg.content = content;
        baseMsg.body = txtMsg;
        return baseMsg;
    }

    private void initViewModel(Context context) {
        if ((FragmentActivity) context instanceof StreamActivity) {
            imViewModel = ViewModelUtils.get((FragmentActivity) context, IMLiveViewModel.class);
            mControllerViewModel = ViewModelUtils.get((FragmentActivity) context, LiveControllerViewModel.class);

        } else {
            imViewModel = ViewModelUtils.getLive(IMLiveViewModel.class);
            mControllerViewModel = ViewModelUtils.getLive(LiveControllerViewModel.class);


        }
//        imViewModel = ViewModelUtils.get((FragmentActivity) context, IMLiveViewModel.class);
    }

    private void initView(Context context) {
        mNewMessage = findViewById(R.id.rl_new_chat);
        newMessageNumTxt = findViewById(R.id.tv_new_chat);
        mRecyclerView = findViewById(R.id.room_message_list);
        linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .sizeResId(R.dimen.dp_6).colorResId(R.color.transparent).build());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //当前RecyclerView显示出来的最后一个的item的position
                        int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                        //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                        //如果相等则说明已经滑动到最后了
                        if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                            mCanAutoScroll = true;
                            if (mNewMessage.getVisibility() == VISIBLE) {
                                newMessageNum = 0;
                                mNewMessage.setVisibility(GONE);
                            }
                        } else {
                            mCanAutoScroll = false;
                        }
                }
            }
        });
        danmakuListAdapter = new LivePublicChatAreaListAdapter(context);
        mRecyclerView.setAdapter(danmakuListAdapter);

        mNewMessage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                newMessageNum = 0;
                mNewMessage.setVisibility(GONE);
                mCanAutoScroll = true;
                toBottom();
            }
        });
    }

    public void doTopGradualEffect(RecyclerView rvMessageList) {
        if (rvMessageList == null) {
            return;
        }
        final Paint paint = new Paint();
        final Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        paint.setXfermode(xfermode);
        final LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, 100.0f,
                new int[]{0, Color.BLACK}, null, Shader.TileMode.CLAMP);

        rvMessageList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(canvas, parent, state);
                paint.setXfermode(xfermode);
                paint.setShader(linearGradient);
                canvas.drawRect(0.0f, 0.0f, parent.getRight(), 200.0f, paint);
                paint.setXfermode(null);
                canvas.restoreToCount(layerId);
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                layerId = c.saveLayer(0.0f, 0.0f, (float) parent.getWidth(), (float) parent.getHeight(),
                        paint, Canvas.ALL_SAVE_FLAG);
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
            }
        });
    }

    @SuppressLint("StringFormatInvalid")
    public void addMessageToList(BaseRoomMessage message) {
        List<BaseRoomMessage> data = danmakuListAdapter.getData();
        data.add(message);
        int size = data.size();
        if (size > 230) {
            List<BaseRoomMessage> submsg = data.subList(size - 230 + 59, size);
            danmakuListAdapter.setNewData(submsg);
        } else {
            danmakuListAdapter.notifyDataSetChanged();
        }

        if (mCanAutoScroll) {
            toBottom();
        } else {
            newMessageNum = ++newMessageNum;
            newMessageNumTxt.setText(mContext.getString(R.string.new_message, newMessageNum));
            mNewMessage.setVisibility(VISIBLE);
        }
    }

    public void toBottom() {
        linearLayoutManager.scrollToPosition(danmakuListAdapter.getItemCount() - 1);
    }

    //进入直播间消息
//    public void addLiveJoinConvention(LiveJoinRoomMsg msg) {
//        BaseMsg baseMsg = new BaseMsg();
//        baseMsg.messageType = MessageContent.MSG_LIVE_JOIN.type;
//        if(imViewModel!= null && imViewModel.getAnchorId().equals(msg.from.userId)){
//            msg.userType = 10;
//        }
//        baseMsg.body = msg;
//        addMessageToList(baseMsg);
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.d("TouchEvent", ">>>LivePublicChatAreaListLayout onInterceptTouchEvent>>>" + ev.getAction());
        requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }
}