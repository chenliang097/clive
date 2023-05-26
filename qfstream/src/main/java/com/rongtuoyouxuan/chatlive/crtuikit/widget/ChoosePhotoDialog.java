package com.rongtuoyouxuan.chatlive.crtuikit.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.rongtuoyouxuan.chatlive.stream.R;

/**
 * @Description : 底部选择照片Dialog
 * @Author : jianbo
 * @Date : 2022/7/30  15:45
 */
public class ChoosePhotoDialog extends BottomPopupView {

    LinearLayout albumLayout;

    private Context context;
    CallBack callBack;

    public ChoosePhotoDialog(@NonNull Context context) {
        super(context);
    }

    public ChoosePhotoDialog(@NonNull Context context, CallBack callBack) {
        super(context);
        this.context = context;
        this.callBack = callBack;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        albumLayout = findViewById(R.id.layout_album);
        albumLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.choosePhoto();
                dismiss();
            }
        });
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_bottom_choose_photo;
    }

    public void showDialog() {
        new XPopup.Builder(context)
                .dismissOnTouchOutside(true)
                .dismissOnBackPressed(true)
                .hasNavigationBar(false)
                .hasStatusBar(false)
                .isViewMode(true)
                .hasShadowBg(true)
                .asCustom(new ChoosePhotoDialog(context, callBack))
                .show();
    }

    public interface CallBack {

        void choosePhoto();

        void cancel();
    }
}
