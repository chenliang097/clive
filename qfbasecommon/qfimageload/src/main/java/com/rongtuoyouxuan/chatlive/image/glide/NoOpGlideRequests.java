package com.rongtuoyouxuan.chatlive.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.LifecycleListener;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoOpGlideRequests extends GlideRequests {
    public NoOpGlideRequests(@NonNull Glide glide, @NonNull Lifecycle lifecycle, @NonNull RequestManagerTreeNode treeNode, @NonNull Context context) {
        super(glide, lifecycle, treeNode, context);
    }

    public NoOpGlideRequests(@NonNull Context context) {
        this(Glide.get(context), new Lifecycle() {
            @Override
            public void addListener(@NonNull LifecycleListener listener) {

            }

            @Override
            public void removeListener(@NonNull LifecycleListener listener) {

            }

        }, new RequestManagerTreeNode() {

            @NonNull
            @Override
            public Set<RequestManager> getDescendants() {
                return Collections.emptySet();
            }
        }, context);
    }

    @NonNull
    @Override
    public <ResourceType> GlideRequest<ResourceType> as(@NonNull Class<ResourceType> resourceClass) {
        return super.as(resourceClass);
    }

    @NonNull
    @Override
    public synchronized GlideRequests applyDefaultRequestOptions(@NonNull RequestOptions options) {
        return super.applyDefaultRequestOptions(options);
    }

    @NonNull
    @Override
    public synchronized GlideRequests setDefaultRequestOptions(@NonNull RequestOptions options) {
        return super.setDefaultRequestOptions(options);
    }

    @NonNull
    @Override
    public GlideRequests addDefaultRequestListener(RequestListener<Object> listener) {
        return super.addDefaultRequestListener(listener);
    }

    @NonNull
    @Override
    public GlideRequest<Bitmap> asBitmap() {
        return new NoOpGlideRequest<>(context, Bitmap.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<GifDrawable> asGif() {
        return new NoOpGlideRequest<>(context, GifDrawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<Drawable> asDrawable() {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<Drawable> load(@Nullable Bitmap bitmap) {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<Drawable> load(@Nullable Drawable drawable) {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<Drawable> load(@Nullable String string) {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<Drawable> load(@Nullable Uri uri) {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<Drawable> load(@Nullable File file) {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<Drawable> load(@Nullable Integer id) {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @Override
    public GlideRequest<Drawable> load(@Nullable URL url) {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<Drawable> load(@Nullable byte[] bytes) {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<Drawable> load(@Nullable Object o) {
        return new NoOpGlideRequest<>(context, Drawable.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<File> downloadOnly() {
        return new NoOpGlideRequest<>(context, File.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<File> download(@Nullable Object o) {
        return new NoOpGlideRequest<>(context, File.class, this);
    }

    @NonNull
    @Override
    public GlideRequest<File> asFile() {
        return new NoOpGlideRequest<>(context, File.class, this);
    }

    @Override
    protected void setRequestOptions(@NonNull RequestOptions toSet) {
        super.setRequestOptions(toSet);
    }
}
