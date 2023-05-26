package com.rongtuoyouxuan.chatlive.crtimage.glide;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImgRequestOptions extends RequestOptions {

    public ImgRequestOptions() {
        super();
    }

    @NonNull
    @Override
    public ImgRequestOptions sizeMultiplier(float sizeMultiplier) {
        return (ImgRequestOptions) super.sizeMultiplier(sizeMultiplier);
    }

    @NonNull
    @Override
    public ImgRequestOptions useUnlimitedSourceGeneratorsPool(boolean flag) {
        return (ImgRequestOptions) super.useUnlimitedSourceGeneratorsPool(flag);
    }

    @NonNull
    @Override
    public ImgRequestOptions useAnimationPool(boolean flag) {
        return (ImgRequestOptions) super.useAnimationPool(flag);
    }

    @NonNull
    @Override
    public ImgRequestOptions onlyRetrieveFromCache(boolean flag) {
        return (ImgRequestOptions) super.onlyRetrieveFromCache(flag);
    }

    @NonNull
    @Override
    public ImgRequestOptions diskCacheStrategy(@NonNull DiskCacheStrategy strategy) {
        return (ImgRequestOptions) super.diskCacheStrategy(strategy);
    }

    @NonNull
    @Override
    public ImgRequestOptions priority(@NonNull Priority priority) {
        return (ImgRequestOptions) super.priority(priority);
    }

    @NonNull
    @Override
    public ImgRequestOptions placeholder(@Nullable Drawable drawable) {
        return (ImgRequestOptions) super.placeholder(drawable);
    }

    @NonNull
    @Override
    public ImgRequestOptions placeholder(int resourceId) {
        return (ImgRequestOptions) super.placeholder(resourceId);
    }

    @NonNull
    @Override
    public ImgRequestOptions fallback(@Nullable Drawable drawable) {
        return (ImgRequestOptions) super.fallback(drawable);
    }

    @NonNull
    @Override
    public ImgRequestOptions fallback(int resourceId) {
        return (ImgRequestOptions) super.fallback(resourceId);
    }

    @NonNull
    @Override
    public ImgRequestOptions error(@Nullable Drawable drawable) {
        return (ImgRequestOptions) super.error(drawable);
    }

    @NonNull
    @Override
    public ImgRequestOptions error(int resourceId) {
        return (ImgRequestOptions) super.error(resourceId);
    }

    @NonNull
    @Override
    public ImgRequestOptions theme(@Nullable Resources.Theme theme) {
        return (ImgRequestOptions) super.theme(theme);
    }

    @NonNull
    @Override
    public ImgRequestOptions skipMemoryCache(boolean skip) {
        return (ImgRequestOptions) super.skipMemoryCache(skip);
    }

    @NonNull
    @Override
    public ImgRequestOptions override(int width, int height) {
        return (ImgRequestOptions) super.override(width, height);
    }

    @NonNull
    @Override
    public ImgRequestOptions override(int size) {
        return (ImgRequestOptions) super.override(size);
    }

    @NonNull
    @Override
    public ImgRequestOptions signature(@NonNull Key signature) {
        return (ImgRequestOptions) super.signature(signature);
    }

    @Override
    public ImgRequestOptions clone() {
        return (ImgRequestOptions) super.clone();
    }

    @NonNull
    @Override
    public <T> ImgRequestOptions set(@NonNull Option<T> option, @NonNull T value) {
        return (ImgRequestOptions) super.set(option, value);
    }

    @NonNull
    @Override
    public ImgRequestOptions decode(@NonNull Class<?> resourceClass) {
        return (ImgRequestOptions) super.decode(resourceClass);
    }

    @NonNull
    @Override
    public ImgRequestOptions encodeFormat(@NonNull Bitmap.CompressFormat format) {
        return (ImgRequestOptions) super.encodeFormat(format);
    }

    @NonNull
    @Override
    public ImgRequestOptions encodeQuality(int quality) {
        return (ImgRequestOptions) super.encodeQuality(quality);
    }

    @NonNull
    @Override
    public ImgRequestOptions frame(long frameTimeMicros) {
        return (ImgRequestOptions) super.frame(frameTimeMicros);
    }

    @NonNull
    @Override
    public ImgRequestOptions format(@NonNull DecodeFormat format) {
        return (ImgRequestOptions) super.format(format);
    }

    @NonNull
    @Override
    public ImgRequestOptions disallowHardwareConfig() {
        return (ImgRequestOptions) super.disallowHardwareConfig();
    }

    @NonNull
    @Override
    public ImgRequestOptions downsample(@NonNull DownsampleStrategy strategy) {
        return (ImgRequestOptions) super.downsample(strategy);
    }

    @NonNull
    @Override
    public ImgRequestOptions timeout(int timeoutMs) {
        return (ImgRequestOptions) super.timeout(timeoutMs);
    }

    @NonNull
    @Override
    public ImgRequestOptions optionalCenterCrop() {
        return (ImgRequestOptions) super.optionalCenterCrop();
    }

    @NonNull
    @Override
    public ImgRequestOptions centerCrop() {
        return (ImgRequestOptions) super.centerCrop();
    }

    @NonNull
    @Override
    public ImgRequestOptions optionalFitCenter() {
        return (ImgRequestOptions) super.optionalFitCenter();
    }

    @NonNull
    @Override
    public ImgRequestOptions fitCenter() {
        return (ImgRequestOptions) super.fitCenter();
    }

    @NonNull
    @Override
    public ImgRequestOptions optionalCenterInside() {
        return (ImgRequestOptions) super.optionalCenterInside();
    }

    @NonNull
    @Override
    public ImgRequestOptions centerInside() {
        return (ImgRequestOptions) super.centerInside();
    }

    @NonNull
    @Override
    public ImgRequestOptions optionalCircleCrop() {
        return (ImgRequestOptions) super.optionalCircleCrop();
    }

    @NonNull
    @Override
    public ImgRequestOptions circleCrop() {
        return (ImgRequestOptions) super.circleCrop();
    }

    @NonNull
    @Override
    public ImgRequestOptions transform(@NonNull Transformation<Bitmap> transformation) {
        return (ImgRequestOptions) super.transform(transformation);
    }

    @NonNull
    @Override
    public ImgRequestOptions transforms(@NonNull Transformation<Bitmap>... transformations) {
        return (ImgRequestOptions) super.transforms(transformations);
    }

    @NonNull
    @Override
    public ImgRequestOptions optionalTransform(@NonNull Transformation<Bitmap> transformation) {
        return (ImgRequestOptions) super.optionalTransform(transformation);
    }

    @NonNull
    @Override
    public <T> ImgRequestOptions optionalTransform(@NonNull Class<T> resourceClass, @NonNull Transformation<T> transformation) {
        return (ImgRequestOptions) super.optionalTransform(resourceClass, transformation);
    }

    @NonNull
    @Override
    public <T> ImgRequestOptions transform(@NonNull Class<T> resourceClass, @NonNull Transformation<T> transformation) {
        return (ImgRequestOptions) super.transform(resourceClass, transformation);
    }

    @NonNull
    @Override
    public ImgRequestOptions dontTransform() {
        return (ImgRequestOptions) super.dontTransform();
    }

    @NonNull
    @Override
    public ImgRequestOptions dontAnimate() {
        return (ImgRequestOptions) super.dontAnimate();
    }
}
