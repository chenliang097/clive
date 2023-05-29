package com.yalantis.ucrop.util;

import android.graphics.Bitmap;

import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.CropParameters;
import com.yalantis.ucrop.model.ImageState;
import com.yalantis.ucrop.task.BitmapCropTask;

import androidx.annotation.Nullable;


public class BitmapCropUtils {
    public static void cropBitmapInBackground(Bitmap bitmap, ImageState imageState, CropParameters cropParameters, @Nullable BitmapCropCallback cropCallback) {
        new BitmapCropTask(bitmap, imageState, cropParameters, cropCallback)
                .executeOnExecutor(BitmapLoadUtils.SINGLE_THREAD_EXECUTOR);

    }
}
