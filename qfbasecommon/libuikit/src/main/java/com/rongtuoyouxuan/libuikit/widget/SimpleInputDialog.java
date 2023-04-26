package com.rongtuoyouxuan.libuikit.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.rongtuoyouxuan.libuikit.R;

/**
 * @Description : 可输入内容Dialog
 * @Author : jianbo
 * @Date : 2022/7/30  19:28
 */
public class SimpleInputDialog extends AppCompatDialog implements View.OnClickListener {

    private TextView titleTv;
    private EditText inputEt;
    private TextView countTv;
    private TextView confirmTv;
    private TextView cancelTv;

    //标题
    private String titleText;
    //输入框提示语
    private String hintText;
    private String confirmText;
    private String cancelText;

    private boolean isShowCountLimit = true;
    private int countLimit = 300;

    private InputDialogListener inputDialogListener;

    public SimpleInputDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogWindow();
        setContentView(R.layout.common_dialog_simple_input);
        initView();
    }

    private void initDialogWindow() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (window == null) return;
        // 透明化背景
        // 背景色
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        titleTv = findViewById(R.id.common_dialog_tv_title);
        inputEt = findViewById(R.id.common_dialog_et_input);
        countTv = findViewById(R.id.common_dialog_tv_count);
        confirmTv = findViewById(R.id.common_dialog_tv_confirm);
        cancelTv = findViewById(R.id.common_dialog_tv_cancel);
        confirmTv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);

        inputEt.requestFocus();
        inputEt.setFocusableInTouchMode(true);

        if (!TextUtils.isEmpty(titleText)) {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(titleText);
        } else {
            titleTv.setVisibility(View.GONE);
        }

        if (isShowCountLimit) {
            confirmTv.setVisibility(View.VISIBLE);
        } else {
            confirmTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(hintText)) {
            inputEt.setHint(hintText);
        }

        if (!TextUtils.isEmpty(confirmText)) {
            confirmTv.setText(confirmText);
        }

        if (!TextUtils.isEmpty(cancelText)) {
            cancelTv.setText(cancelText);
        }

        //etNoteContent是EditText
        inputEt.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //实时记录输入的字数
                wordNum = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = countLimit - s.length();
                //TextView显示已输入字数
                countTv.setText("" + number);
                selectionStart = inputEt.getSelectionStart();
                selectionEnd = inputEt.getSelectionEnd();
                if (wordNum.length() > countLimit) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    inputEt.setText(s);
                    //设置光标在最后
                    inputEt.setSelection(tempSelection);
                }
            }
        });

    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public void setInputHint(String hint) {
        hintText = hint;
    }

    /**
     * 是否显示已输入字数  默认显示
     *
     * @param showCountLimit
     */
    public void setShowCountLimit(boolean showCountLimit) {
        isShowCountLimit = showCountLimit;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    public void setInputDialogListener(InputDialogListener listener) {
        this.inputDialogListener = listener;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.common_dialog_tv_confirm) {
            boolean isClose = true;
            if (inputDialogListener != null) {
                isClose = inputDialogListener.onConfirmClicked(inputEt);
            }
            if (isClose) {
                dismiss();
            }
        } else if (id == R.id.common_dialog_tv_cancel) {
            dismiss();
        }
    }

    /**
     * 输入框回调监听
     */
    public interface InputDialogListener {
        /**
         * 当点击确认时回调输入内容
         *
         * @return 返回 false 时，不关闭对话框，返回 true时关闭对话框
         */
        boolean onConfirmClicked(EditText input);
    }
}
