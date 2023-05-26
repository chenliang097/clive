package com.rongtuoyouxuan.chatlive.crtuikit;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.stream.R;


public class CommonDialog extends Dialog {

    public static int TYPE_IDOK = 0;
    public static int TYPE_OKCANCEL = 1;

    public int nResoult = 0;
    private String mContent;
    private String mOKtext;
    private String mCancelText;
    private TextView mTextView;
    private TextView mOKTextView;
    private TextView mCancelTextView;
    private View mCancelWrapper;
    private int mType = 1;
    public CommonDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pl_libres_common_dlg);

        setCanceledOnTouchOutside(false);
        init();
    }

    public void setContent(String content, int type) {
        mContent = content;
        if (mTextView != null) {
            mTextView.setText(mContent);
        }

        mType = type;
        if (type == TYPE_IDOK) {
            if (mCancelWrapper != null) {
                mCancelWrapper.setVisibility(View.GONE);
            }
        }
    }

    public void setButtonOK(String text) {
        mOKtext = text;
        if (mOKTextView != null) {
            mOKTextView.setText(mOKtext);
        }
    }

    public void setButtonCancel(String text) {
        mCancelText = text;
        if (mCancelTextView != null) {
            mCancelTextView.setText(mCancelText);
        }
    }

    private void init() {
        mTextView = findViewById(R.id.content);
        mTextView.setText(mContent);

        mOKTextView = findViewById(R.id.button_ok);
        mOKTextView.setText(mOKtext);
        mOKTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nResoult = 1;
                dismiss();
            }
        });

        mCancelTextView = findViewById(R.id.button_cancel);
        mCancelTextView.setText(mCancelText);
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nResoult = 0;
                dismiss();
            }
        });

        mCancelWrapper = findViewById(R.id.ll_cancel_wrapper);
        if (mType == TYPE_IDOK) {
            mCancelWrapper.setVisibility(View.GONE);
        }
    }
}
