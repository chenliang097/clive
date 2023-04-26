package com.rongtuoyouxuan.libuikit.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

public class ClickTextView extends TextView {
    public ClickTextView(Context context) {
        this(context,null);
    }

    public ClickTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClickTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setClickable(true);

    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return LinkMovementMethod.getInstance();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }
}
