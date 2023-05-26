package com.rongtuoyouxuan.chatlive.crturop.callback;

import android.graphics.Bitmap;

import com.rongtuoyouxuan.chatlive.crturop.model.ExifInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface BitmapLoadCallback {

    void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull String imageInputPath, @Nullable String imageOutputPath);

    void onFailure(@NonNull Exception bitmapWorkerException);

}