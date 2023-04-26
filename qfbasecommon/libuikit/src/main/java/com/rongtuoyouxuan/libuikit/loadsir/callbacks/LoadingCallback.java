package com.rongtuoyouxuan.libuikit.loadsir.callbacks;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rongtuoyouxuan.chatlive.image.util.GlideUtils;
import com.rongtuoyouxuan.libuikit.R;
import com.kingja.loadsir.callback.Callback;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class LoadingCallback extends Callback {

    private LinearLayout mContainer;

    @Override
    protected int onCreateView() {
        return R.layout.profession_lib_layout_loading;
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        mContainer = view.findViewById(R.id.container);
        mContainer.setVisibility(View.VISIBLE);
        ImageView image = view.findViewById(R.id.image);
        GlideUtils.loadImageGif(image.getContext(), R.drawable.profession_lib_loading, image,null);
    }

    @Override
    public void onDetach() {
        if (mContainer != null) {
            mContainer.setVisibility(View.GONE);
            mContainer = null;
        }
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {

        return true;
    }
}
