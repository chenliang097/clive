package com.rongtuoyouxuan.chatlive.base.view.layout;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils;
import com.rongtuoyouxuan.chatlive.base.view.model.SendEvent;
import com.rongtuoyouxuan.chatlive.base.viewmodel.ControllerViewModel;
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.EnterRoomBean;
import com.rongtuoyouxuan.chatlive.databus.DataBus;
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus;
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants;
import com.rongtuoyouxuan.chatlive.router.Router;
import com.rongtuoyouxuan.chatlive.router.bean.ISource;
import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity;
import com.rongtuoyouxuan.chatlive.stream.view.layout.BackPressListener;
import com.rongtuoyouxuan.chatlive.util.KeyBoardUtils;
import com.rongtuoyouxuan.chatlive.util.StringUtils;
import com.rongtuoyouxuan.qfcommon.widget.GifPannelView;

import cn.dreamtobe.kpswitch.util.KPSwitchConflictUtil;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout;

public abstract class RoomSendMessageLayout extends RelativeLayout implements View.OnClickListener, BackPressListener {

    public static final int TYPE_MESSAGE = 1;
    public static final int TYPE_NOTICE = 3;
    public static final int TYPE_AITE = 4;
    public int myType = TYPE_MESSAGE;
    protected ControllerViewModel mControllerViewModel;
    protected IMLiveViewModel imViewModel;
    private EditText roomMessageInput;
    private TextView roomMessageSent;
    private String saveUserNChat = "";//用户未发送的普通内容
    private String saveUserDChat = "";//用户未发送的弹幕内容
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private LinearLayout mLLMsgContainer;
    private String mTransLanguage = "";
    private View mInternalLayout;
    private ImageView gifImg;
    private TextView inputTextNumTxt;
    private EnterRoomBean.DataBean roomInfoBean;


    private Handler handler = new Handler(Looper.getMainLooper());

    public RoomSendMessageLayout(Context context) {
        this(context, null);
    }

    public RoomSendMessageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoomSendMessageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initViewModel(context);
        if((FragmentActivity) context instanceof StreamActivity){
            imViewModel = ViewModelUtils.get((FragmentActivity) context, IMLiveViewModel.class);
        }else{
            imViewModel = ViewModelUtils.getLive(IMLiveViewModel.class);

        }
        inflate(context, R.layout.qf_stream_layout_sendmessage, this);
        initView(context);
        initObserve(context);
    }

    private void initObserve(Context context) {
        imViewModel.showRechargeDialog.observe((LifecycleOwner) context, new Observer<Void>() {
            @Override
            public void onChanged(Void aVoid) {
                Router.toGoldToBuyDialog("12");
            }
        });
        mControllerViewModel.mMessageButton.observeOnce((LifecycleOwner) context, new Observer<SendEvent>() {
            @Override
            public void onChanged(@Nullable SendEvent event) {
                if (event.Event == TYPE_MESSAGE) {
                    myType = TYPE_MESSAGE;
                } else if (event.Event == TYPE_AITE) {
                    myType = TYPE_MESSAGE;
                    if (event.object != null) {
                        roomMessageInput.setText((String) event.object);
                        roomMessageInput.setSelection(((String) event.object).length());
                    }
                } else {
                    myType = event.Event;
                }
                roomMessageInput.setFocusable(true);
                roomMessageInput.setFocusableInTouchMode(true);
                roomMessageInput.requestFocus();
                mLLMsgContainer.setVisibility(VISIBLE);
                imViewModel.showPanel.setValue(true);
                roomMessageInput.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        KeyBoardUtils.showSoftInput(roomMessageInput);

                    }
                }, 50);
            }
        });

        imViewModel.streamIdLiveEvent.observe((LifecycleOwner) getContext(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mControllerViewModel.upUserInfo(s);
                gifImg.setVisibility(GONE);
                inputTextNumTxt.setVisibility(GONE);
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!TextUtils.isEmpty(mControllerViewModel.getStreamId())){
                    mControllerViewModel.upUserInfo(mControllerViewModel.getStreamId());
                }
            }
        }, 500);
        imViewModel.hideInputViewLiveEvent.observe((LifecycleOwner) getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                hidePanelAndKeyboard();
            }
        });

        imViewModel.clickGifPannelLiveEvent.observe((LifecycleOwner) getContext(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
//                imViewModel.showIGifPannelLiveEvent.setValue(true);
                mControllerViewModel.mMessageButton.setValue(new SendEvent(SendEvent.TYPE_MESSAGE, "showGif"));
            }
        });

        imViewModel.roomInfoLiveEvent.observe((LifecycleOwner) getContext(), new Observer<EnterRoomBean.DataBean>() {
            @Override
            public void onChanged(EnterRoomBean.DataBean dataBean) {
                roomInfoBean = dataBean;
            }
        });

        LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_ADJUST_PUBLIC_CHAT).observe((LifecycleOwner) getContext(), new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                imViewModel.showIGifPannelLiveEvent.setValue(false);
                imViewModel.showPanel.setValue(false);
            }
        });
    }

    public abstract void initViewModel(Context context);

    private void initView(Context context) {
        gifImg = findViewById(R.id.bottomInputGiftImg);
        mInternalLayout = findViewById(R.id.internal_layout);
        mLLMsgContainer = findViewById(R.id.ll_msg_container);
        roomMessageInput = findViewById(R.id.room_message_input);
        roomMessageSent = findViewById(R.id.room_message_sent);
        inputTextNumTxt = findViewById(R.id.bottomInputTextNumTxt);

        roomMessageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (myType == TYPE_MESSAGE || myType == TYPE_AITE) {
                    saveUserNChat = roomMessageInput.getText().toString();
                }
                if(s.length() > 0){
                    roomMessageSent.setTextColor(context.getResources().getColor(R.color.rt_c_FF2434));
                    roomMessageSent.setEnabled(true);
                }else{
                    roomMessageSent.setTextColor(context.getResources().getColor(R.color.c_20_black));
                    roomMessageSent.setEnabled(false);
                }
            }
        });
        mInternalLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mLLMsgContainer.getVisibility() == VISIBLE) {
                    hidePanelAndKeyboard();
                    return true;
                }
                return false;
            }
        });

        roomMessageInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String msg = roomMessageInput.getText().toString();
                    if (StringUtils.isEmpty(msg)) {
                        Toast.makeText(getContext(), R.string.message_room_chat_empty, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    if(roomInfoBean != null) {
                        imViewModel.sendLiveTxtMsg(String.valueOf(roomInfoBean.getRoom_id()), String.valueOf(roomInfoBean.getScene_id()),
                                roomInfoBean.getAnchor_id(), msg, roomInfoBean.is_super_admin(),
                                roomInfoBean.is_room_admin(), roomInfoBean.is_anchor(),
                                roomInfoBean.getUser_avatar(), DataBus.instance().USER_ID,
                                DataBus.instance().USER_NAME);
                    }
                    clearMessage();
                    return true;
                }
                return false;


            }
        });
//        findViewById(R.id.room_message_list).setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (mLLMsgContainer.getVisibility() == VISIBLE) {
//                    hidePanelAndKeyboard();
//                }
//                return false;
//            }
//        });
        roomMessageSent.setOnClickListener(this);
        gifImg.setOnClickListener(this);
    }

    public void clearMessage() {
        //清除缓存的聊天内容
        saveUserNChat = "";
        saveUserDChat = "";
        roomMessageInput.setText("");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.room_message_sent) {
            String msg = roomMessageInput.getText().toString();
            if (StringUtils.isEmpty(msg)) {
                Toast.makeText(getContext(), R.string.message_room_chat_empty, Toast.LENGTH_SHORT).show();
            } else {
                switch (myType) {
                    case TYPE_MESSAGE:
                        //公聊区聊天消息
                        if(roomInfoBean != null) {
                            imViewModel.sendLiveTxtMsg(String.valueOf(roomInfoBean.getRoom_id()), String.valueOf(roomInfoBean.getScene_id()),
                                    roomInfoBean.getAnchor_id(), msg, roomInfoBean.is_super_admin(),
                                    roomInfoBean.is_room_admin(), roomInfoBean.is_anchor(),
                                    roomInfoBean.getUser_avatar(), DataBus.instance().USER_ID,
                                    DataBus.instance().USER_NAME);
                        }
                        break;
                    case TYPE_NOTICE:
                        //发送公共
                        imViewModel.sendCommonMessage(msg);
                        break;
                }
                clearMessage();
            }
        }
//        else if(id == R.id.bottomInputGiftImg){
//            imViewModel.showIGifPannelLiveEvent.setValue(true);
//            Router.toGifListDialog(getContext(), imViewModel.getStreamId(), ISource.FROM_LIVE_ROOM);
//            mLLMsgContainer.setVisibility(GONE);
//            KeyboardUtil.hideKeyboard(roomMessageInput);
//        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
        KeyboardUtil.detach((Activity) getContext(), onGlobalLayoutListener);
    }

    @Override
    public boolean onBackPress() {
        if (mLLMsgContainer.getVisibility() == VISIBLE) {
            hidePanelAndKeyboard();
            return true;
        }
        return false;
    }

    private void hidePanelAndKeyboard() {
        mLLMsgContainer.setVisibility(GONE);
        KeyboardUtil.hideKeyboard(roomMessageInput);
        imViewModel.showPanel.setValue(false);
    }
}
