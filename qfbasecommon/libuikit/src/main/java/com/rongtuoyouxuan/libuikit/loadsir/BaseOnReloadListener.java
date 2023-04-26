package com.rongtuoyouxuan.libuikit.loadsir;

import android.view.View;

import com.rongtuoyouxuan.libuikit.R;
import com.kingja.loadsir.callback.Callback;

/*
 *Create by {Mr秦} on 2020/12/26
 */
public abstract class BaseOnReloadListener implements Callback.OnReloadListener {
    @Override
    public void onReload(View v) {
        if (v.findViewById(R.id.empty_btn) != null) {
            emptyClick(v);
        } else {
            errorClick(v);
        }
    }

    public abstract void emptyClick(View v);

    public abstract void errorClick(View v);

}