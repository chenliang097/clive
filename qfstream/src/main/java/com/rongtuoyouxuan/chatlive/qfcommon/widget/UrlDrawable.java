package com.rongtuoyouxuan.chatlive.qfcommon.widget;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class UrlDrawable extends BitmapDrawable {
    public BitmapDrawable bitmapDrawable;

    @Override
    public void draw(Canvas canvas) {
        if (bitmapDrawable != null) {
            bitmapDrawable.draw(canvas);
        }
    }
}
