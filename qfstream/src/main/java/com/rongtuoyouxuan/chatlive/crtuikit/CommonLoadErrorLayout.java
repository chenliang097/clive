package com.rongtuoyouxuan.chatlive.crtuikit;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rongtuoyouxuan.chatlive.crtimage.ImgLoader;
import com.rongtuoyouxuan.chatlive.stream.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class CommonLoadErrorLayout extends LinearLayout {


    public static final int WHITE = 0x000001;
    public static final int BLACK = 0x000010;
    private ImageView mIvLoadErrorIcon;


    public CommonLoadErrorLayout(Context context) {
        super(context);
        initViews();
    }

    public CommonLoadErrorLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public CommonLoadErrorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CommonLoadErrorLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews();
    }

    private void initViews() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View.inflate(getContext(), R.layout.pl_libutil_common_load_error_layout, this);
        mIvLoadErrorIcon = findViewById(R.id.iv_common_load_error_layout);
        setErrorIconMode(WHITE);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        mIvLoadErrorIcon.setVisibility(visibility);
    }

    public void setErrorIconMode(@ErrorIconFlavour int mode) {
        switch (mode) {
            case BLACK: {
                ImgLoader.loadLocalRes(mIvLoadErrorIcon, R.drawable.pl_libutil_common_load_error_black);
//                FrescoLoadUtil.getInstance().loadImageLocalResFromDimen(mIvLoadErrorIcon, R.dimen.pl_libutil_common_dimen_134dp, R.dimen.pl_libutil_common_dimen_134dp, R.drawable.pl_libutil_common_load_error_black);
                break;
            }

            default: {
                ImgLoader.loadLocalRes(mIvLoadErrorIcon, R.drawable.pl_libutil_common_load_error_white);
//                FrescoLoadUtil.getInstance().loadImageLocalResFromDimen(mIvLoadErrorIcon, R.dimen.pl_libutil_common_dimen_134dp, R.dimen.pl_libutil_common_dimen_134dp, R.drawable.pl_libutil_common_load_error_white);
                break;
            }
        }
    }

    @IntDef({WHITE, BLACK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorIconFlavour {
    }

}
