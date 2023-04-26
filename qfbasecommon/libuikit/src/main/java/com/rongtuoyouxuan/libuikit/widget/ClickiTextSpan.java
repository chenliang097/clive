package com.rongtuoyouxuan.libuikit.widget;

import androidx.annotation.NonNull;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class ClickiTextSpan extends ClickableSpan {
    SpanClick mSpanClick;
    int mColor;

    public ClickiTextSpan(int color) {
        this.mColor = color;
    }

    public ClickiTextSpan(int color, SpanClick mSpanClick) {
        this.mSpanClick = mSpanClick;
        this.mColor = color;
    }

    @Override
    public void onClick(@NonNull View widget) {
        if (mSpanClick != null) {
            mSpanClick.onClick(this);
        }
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setAntiAlias(true);
        ds.setColor(mColor);
    }

    public void setSpanClick(SpanClick spanClick) {
        mSpanClick = spanClick;
    }

    public interface SpanClick {
        void onClick(ClickiTextSpan clickiTextSpan);
    }
}
