package com.rongtuoyouxuan.chatlive.image.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;

import androidx.annotation.NonNull;

class NoOpGlideRequest<TranscodeType> extends GlideRequest<TranscodeType> {
    public NoOpGlideRequest(@NonNull Glide glide, @NonNull RequestManager requestManager, @NonNull Class<TranscodeType> transcodeClass, @NonNull Context context) {
        super(glide, requestManager, transcodeClass, context);
    }

    public NoOpGlideRequest(@NonNull Context context, @NonNull Class<TranscodeType> transcodeClass, @NonNull RequestManager requestManager) {
        this(Glide.get(context), requestManager, transcodeClass, context);
    }

    @NonNull
    @Override
    public Target into(@NonNull Target target) {
        return target;
    }

    @NonNull
    @Override
    public ViewTarget into(@NonNull ImageView view) {
        return new BitmapImageViewTarget(view);
    }
}
