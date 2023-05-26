package com.rongtuoyouxuan.chatlive.crtuikit;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rongtuoyouxuan.chatlive.stream.R;


/**
 * @author rongbo on 2016/5/20.
 */
public class CommonLoadingLayout extends LinearLayout {

    private ProgressBar mProgressView;
    private AppCompatTextView mTvLoading;

    public CommonLoadingLayout(Context context) {
        super(context);
        initViews();
    }

    public CommonLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public CommonLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommonLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews();
    }

    private void initViews() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View.inflate(getContext(), R.layout.pl_libutil_common_loading_layout, this);
        mProgressView = findViewById(R.id.progressbar_common_loading);
        mTvLoading = findViewById(R.id.tv_common_loading_text);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        mProgressView.setVisibility(visibility);
        mTvLoading.setVisibility(visibility);
    }
}
