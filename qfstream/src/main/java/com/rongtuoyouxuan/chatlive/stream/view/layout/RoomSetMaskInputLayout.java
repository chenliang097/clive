package com.rongtuoyouxuan.chatlive.stream.view.layout;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.chatlive.crtutil.util.KeyBoardUtils;
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil;

public class RoomSetMaskInputLayout extends RelativeLayout {

    private EditText roomMessageInput;
    private TextView roomMessageSent;
    private LinearLayout mLLMsgContainer;

    public RoomSetMaskInputLayout(Context context) {
        this(context, null);
    }

    public RoomSetMaskInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoomSetMaskInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.qf_stream_layout_set_mask_input, this);
        initView(context);
        initObserve(context);
    }

    private void initObserve(Context context) {
    }

    private void initView(Context context) {
        mLLMsgContainer = findViewById(R.id.ll_msg_container);
        roomMessageInput = findViewById(R.id.room_message_input);
        roomMessageSent = findViewById(R.id.room_message_sent);
        mLLMsgContainer.setVisibility(VISIBLE);
        KeyBoardUtils.showSoftInput(roomMessageInput);

        roomMessageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() > 10){
                    LaToastUtil.showShort(context.getResources().getString(R.string.stream_mask_words_tip));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    roomMessageSent.setTextColor(context.getResources().getColor(R.color.rt_c_FF2434));
                    roomMessageSent.setEnabled(true);
                }else{
                    roomMessageSent.setTextColor(context.getResources().getColor(R.color.c_20_black));
                    roomMessageSent.setEnabled(false);
                }
            }
        });

    }

    public interface OnSetMaskWordListener{
        void onSetMaskWord(String content);
    }
}
