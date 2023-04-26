package com.rongtuoyouxuan.libuikit.layout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rongtuoyouxuan.chatlive.image.util.GlideUtils;
import com.rongtuoyouxuan.libuikit.R;


/**
 * Created by Wells on 2018/3/18.
 */

public class CommonStatusView extends FrameLayout implements IStatusView {
    protected View mLayoutLoading, mLayoutError, mLayoutEmpty;
    protected OnClickListener mErrorListener;

    public CommonStatusView(Context context) {
        super(context);
        init();
    }

    public CommonStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonStatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.pl_libutil_layout_load_status_view, this);
        mLayoutLoading = findViewById(R.id.layout_loading);
    }

    @Override
    public void showLoading() {
        if (null != mLayoutLoading) {
            mLayoutLoading.setVisibility(VISIBLE);
            GlideUtils.loadImageGif(getContext(), R.drawable.profession_lib_loading, mLayoutLoading.findViewById(R.id.image),null);
        }
        if (null != mLayoutEmpty) {
            mLayoutEmpty.setVisibility(GONE);
        }
        if (null != mLayoutError) {
            mLayoutError.setVisibility(GONE);
        }
    }

    @Override
    public void showError() {
        setVisibility(VISIBLE);
        if (null == mLayoutError) {
            mLayoutError = ((ViewStub) findViewById(R.id.layout_error)).inflate();
        }
        mLayoutError.setOnClickListener(mErrorListener);
        if (null != mLayoutError) {
            mLayoutError.setVisibility(VISIBLE);
        }
        if (null != mLayoutEmpty) {
            mLayoutEmpty.setVisibility(GONE);
        }
        if (null != mLayoutLoading) {
            mLayoutLoading.setVisibility(GONE);
        }
    }

    @Override
    public void showError(String msg) {
        showError();
        if (null != mLayoutError && !TextUtils.isEmpty(msg)) {
            TextView mErrorView = (TextView) mLayoutError.findViewById(R.id.tv_error);
            mErrorView.setText(msg);
        }
    }
    
    @Override
    public void showEmpty() {
        setVisibility(VISIBLE);
        if (null == mLayoutEmpty) {
            mLayoutEmpty = ((ViewStub) findViewById(R.id.layout_empty)).inflate();
        }

        if (null != mLayoutEmpty) {
            mLayoutEmpty.setVisibility(VISIBLE);
        }
        if (null != mLayoutError) {
            mLayoutError.setVisibility(GONE);
        }
        if (null != mLayoutLoading) {
            mLayoutLoading.setVisibility(GONE);
        }
    }

    @Override
    public void showEmpty(String msg) {
        showEmpty();
        if (null != mLayoutEmpty && !TextUtils.isEmpty(msg)) {
            TextView mEmptyView = (TextView) mLayoutEmpty.findViewById(R.id.tv_empty);
            mEmptyView.setText(msg);
        }
    }

    @Override
    public void setOnErrorClickListener(OnClickListener listener) {
        mErrorListener = listener;
    }

    @Override
    public void showContent() {
        setVisibility(GONE);
        if (null != mLayoutEmpty) {
            mLayoutEmpty.setVisibility(GONE);
        }
        if (null != mLayoutError) {
            mLayoutError.setVisibility(GONE);
        }
        if (null != mLayoutLoading) {
            mLayoutLoading.setVisibility(GONE);
        }
    }

    @Override
    public View getEmptyView() {
        return mLayoutEmpty;
    }

    public interface ToLiveListener {
        void toLiveClick();
    }
}
