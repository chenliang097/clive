package com.rongtuoyouxuan.libuikit;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.Toolbar;

import com.rongtuoyouxuan.chatlive.util.KeyBoardUtils;
import com.gyf.barlibrary.ImmersionBar;

public class BaseStatusBarFragmentActivity extends LanguageActivity {

    public ImmersionBar mImmersionBar;
    private Toolbar mToolbar;
    private ImageView mBack;
    private TextView mTvTitle;
    private ImageView mRightBtn;
    private View mCommonTitleBarWithStatusBar;

    protected void startWakeLock() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected void stopWakeLock() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImmersionBar();
        setStatusBarDartFont();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initToolbar();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initToolbar();
    }

    private void initToolbar() {
        mCommonTitleBarWithStatusBar = findViewById(R.id.commonTitleBarWithStatusBar);
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar == null || mCommonTitleBarWithStatusBar == null) return;
        mBack = findViewById(R.id.iv_back);
        mTvTitle = findViewById(R.id.tv_title);
        mRightBtn = findViewById(R.id.iv_right_button);
//        setSupportActionBar(mToolbar);
        mCommonTitleBarWithStatusBar.setBackgroundResource(configCommonTitleBarWithStatusBarBackground());
        //TODO 直接getTitle 国际化有问题
        mTvTitle.setText(configTitle().toString().isEmpty() ? getTitle() : configTitle());
        mTvTitle.setTextAppearance(this, getTitleAppearance());
        mBack.setImageResource(configBackIcon());
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideSoftInputBox(BaseStatusBarFragmentActivity.this);
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    /**
     * 不同场景下 title 不一样
     * <p>
     * return resId
     */
    @StyleRes
    protected int getTitleAppearance() {
        return R.style.Font203;
    }

    @DrawableRes
    protected int configCommonTitleBarWithStatusBarBackground() {
        return R.drawable.bg_white_title;
    }

    @DrawableRes
    protected int configBackIcon() {
        return R.drawable.icon_common_topnavigation_lefttarrowback;
    }

    protected CharSequence configTitle() {
        return "";
    }


    /**
     * 可能为null
     *
     * @return
     */
    protected ImageView getRightBtn() {
        return mRightBtn;
    }


    /**
     * 对状态栏的处理操作
     */
    public void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(false)
                //.fullScreen(true)
                .init();
    }

    public void setStatusBarDartFont() {
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(true)
                .init();
    }

    public void setStatusBarLightFont() {
        mImmersionBar.transparentStatusBar()
                .statusBarDarkFont(false)
                .init();
    }

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null) mImmersionBar.destroy();
        mImmersionBar = null;
        dismissDialogLoading();
        super.onDestroy();
    }

    protected void showDialogLoading() {
        TransferLoadingUtil.showDialogLoading(this);
    }

    protected void dismissDialogLoading() {
        TransferLoadingUtil.dismissDialogLoading(this);
    }
}
