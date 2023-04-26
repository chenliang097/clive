package com.rongtuoyouxuan.qfcommon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rongtuoyouxuan.qfcommon.R;

public class RecyclerViewDialog {

    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private Display display;
    private boolean showTitle = false;
    private View view;
    private FrameLayout frameLayout;

    public RecyclerViewDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public RecyclerViewDialog builder() {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_recycler_view, null);
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);

        frameLayout = (FrameLayout) view.findViewById(R.id.customPanel);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        Window dialogWindow = dialog.getWindow();
        //设置dialog在底部显示
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
//            //设置dialog宽度占满屏幕，高度包含内容
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //因为我的dialog背景图片是圆弧型，不设置背景透明的话圆弧处显示黑色
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);

//        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });

        return this;
    }

    public RecyclerViewDialog setCustomView(View v, ViewGroup.LayoutParams params) {
        if (frameLayout.getChildCount() > 0)
            frameLayout.removeAllViews();

        frameLayout.addView(v, params);
        return this;
    }

    public RecyclerViewDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            txt_title.setText(title);
            txt_title.setVisibility(View.VISIBLE);
            showTitle = true;
        }
        return this;
    }

    public RecyclerViewDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public RecyclerViewDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    private void setLayout() {
        if (!showTitle) {
            txt_title.setVisibility(View.GONE);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public void cancel() {
        dialog.dismiss();
    }
}