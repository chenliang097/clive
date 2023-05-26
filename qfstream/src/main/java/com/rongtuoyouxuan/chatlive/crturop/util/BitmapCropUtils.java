package com.rongtuoyouxuan.chatlive.crturop.util;

import android.graphics.Bitmap;

import com.rongtuoyouxuan.chatlive.crturop.callback.BitmapCropCallback;
import com.rongtuoyouxuan.chatlive.crturop.model.CropParameters;
import com.rongtuoyouxuan.chatlive.crturop.model.ImageState;
import com.rongtuoyouxuan.chatlive.crturop.task.BitmapCropTask;

import androidx.annotation.Nullable;


public class BitmapCropUtils {
    public static void cropBitmapInBackground(Bitmap bitmap, ImageState imageState, CropParameters cropParameters, @Nullable BitmapCropCallback cropCallback) {
        new BitmapCropTask(bitmap, imageState, cropParameters, cropCallback)
                .executeOnExecutor(BitmapLoadUtils.SINGLE_THREAD_EXECUTOR);

    }
}
