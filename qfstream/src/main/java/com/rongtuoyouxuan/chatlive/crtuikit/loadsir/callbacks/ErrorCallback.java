package com.rongtuoyouxuan.chatlive.crtuikit.loadsir.callbacks;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.stream.R;
import com.kingja.loadsir.callback.Callback;

public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.profession_lib_layout_error;
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void onViewCreate(Context context, View view) {
        final TextView sh = view.findViewById(R.id.retry_btn);
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float x = event.getX();
                float y = event.getY();
                return !(x > sh.getLeft()) || !(x < sh.getRight()) || !(y > sh.getTop()) || !(y < sh.getBottom());
            }
            return false;
        });
    }

}
