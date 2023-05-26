package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.LruCache;

public class BitmapLruCache extends LruCache<String, Bitmap> {
    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        if (VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }



    public Bitmap getBitmap(String url) {
        return get(url);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}

