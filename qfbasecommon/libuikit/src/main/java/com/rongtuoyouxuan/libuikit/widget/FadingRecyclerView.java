package com.rongtuoyouxuan.libuikit.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Ning on 2019/4/13
 * Describe :
 */


public class FadingRecyclerView extends RecyclerView {

    private static final String TAG = "FadingRecyclerView";
    private Paint paint;
    private int height;
    private int width;
    private int spanPixel = 100;

    public FadingRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public FadingRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FadingRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//      paint.setShader(new LinearGradient(0, 0, 0, 890/2, 0x00000000, 0xff000000, Shader.TileMode.CLAMP)); //仅上部渐变
    }

    public void setSpanPixel(int spanPixel) {
        this.spanPixel = spanPixel;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        float spanFactor = spanPixel / (height / 2f);
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, 100.0f,
                new int[]{0x00000000, 0xff000000, 0xff000000}, new float[]{0, 100.0f, 1f}, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
    }

    @Override
    public void draw(Canvas c) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH){
            super.draw(c);
            return;
        }
        c.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        super.draw(c);
        c.drawRect(0, 0, width, height, paint);
        c.restore();
    }
}