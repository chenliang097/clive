package com.rongtuoyouxuan.chatlive.crtuikit.layout;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rongtuoyouxuan.chatlive.crtutil.util.DisplayUtil;

/**
 * title bar 包含状态栏高度
 */
public class CommonTitleBarWithStatusBar extends LinearLayout {

    public CommonTitleBarWithStatusBar(Context context) {
        this(context, null);
    }

    public CommonTitleBarWithStatusBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTitleBarWithStatusBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommonTitleBarWithStatusBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        View statusBarView = new View(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.getStatusBarHeight(getContext()));
        addView(statusBarView, 0, layoutParams);
    }
}
