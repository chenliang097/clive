package com.rongtuoyouxuan.libuikit;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rongtuoyouxuan.chatlive.util.DisplayUtil;
import com.rongtuoyouxuan.chatlive.util.KeyBoardUtils;

public abstract class BaseDialog extends AppCompatDialog {
    private View mViewStatusBar;

    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;
    private View mViewShadow;


    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFullWindow();
        setContentView(getLayoutId());
        initDialogWindow();
        initView();
        initData();
        initLoad();
    }

    /**
     * 设置布局
     *
     * @return layoutId
     */
    @LayoutRes
    protected abstract int getLayoutId();


    /**
     * 开始加载数据
     */
    protected abstract void initLoad();


    /**
     * 数据初始化
     */
    protected abstract void initData();


    /**
     * 控件的初始化
     */
    protected abstract void initView();


    private void initFullWindow() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (window == null) return;
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void initDialogWindow() {
        Window window = getWindow();
        if (window == null) return;
        WindowManager.LayoutParams lp = window.getAttributes();
        if (lp == null) return;
        lp.alpha = 1.0f;
        lp.width = DisplayUtil.screenWidth(getContext());
        lp.height = DisplayUtil.getScreenHeight(getContext()) + DisplayUtil.getStatusBarHeight(getContext());
        window.setAttributes(lp);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        mToolbar = findViewById(R.id.app_common_toolbar_layout);
        if (mToolbar == null) return;
        mToolbarTitle = findViewById(R.id.app_common_tv_toolbar_title);
        mToolbarSubTitle = findViewById(R.id.app_common_tv_toolbar_subtitle);
        mViewShadow = findViewById(R.id.toolbar_shadow);
        if (mToolbarTitle == null) return;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayShowTitleEnabled(false);//设置默认的标题不显示

        initImmersionBar();
    }

    private void initImmersionBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        View statusView = findViewById(R.id.view_common_dialog_status_bar_view);
        if (statusView == null) return;
        statusView.setVisibility(View.VISIBLE);
        Window win = getWindow();
        if (win == null) return;
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (winParams == null) return;
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        win.setAttributes(winParams);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mViewStatusBar = findViewById(R.id.view_common_dialog_status_bar_view);
        if (mViewStatusBar == null) {
            throw new NullPointerException(getContext().getString(R.string.pl_libutil_toolbar_null_tip_text));
        }

        initImmersionBar();

        if (getToolbar() == null) return;
        if (isShowToolbar()) {
            if (mToolbar.getVisibility() != View.VISIBLE) mToolbar.setVisibility(View.VISIBLE);
        } else {
            if (mToolbar.getVisibility() != View.GONE) mToolbar.setVisibility(View.GONE);
        }

        if (isShowToolbarBottomLine()) {
            if (mViewShadow != null && mViewShadow.getVisibility() != View.VISIBLE)
                mViewShadow.setVisibility(View.VISIBLE);
        } else {
            if (mViewShadow != null && mViewShadow.getVisibility() != View.GONE)
                mViewShadow.setVisibility(View.GONE);
        }

        if (isShowBacking()) {
            showBack();
        } else {
            hideBack();
        }
    }

    private void hideBack() {
        Toolbar toolbar = getToolbar();
        if (toolbar == null) return;
        toolbar.setNavigationIcon(null);
        toolbar.setNavigationOnClickListener(null);
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        Toolbar toolbar = getToolbar();
        if (toolbar == null) return;
        toolbar.setNavigationIcon(R.drawable.pl_libutil_common_back_icon);
        if (isChangeToolbarBackEvent()) return;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideSoftInputBox(getOwnerActivity());
                onBackPressed();
            }
        });
    }

    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return mToolbar;
    }


    /**
     * 获取头部标题的TextView
     *
     * @return mToolbarTitle
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }


    /**
     * 获取头部标题的TextView
     *
     * @return mToolbarSubTitle
     */
    public TextView getToolbarSubTitle() {
        return mToolbarSubTitle;
    }

    /**
     * 设置头部标题
     *
     * @param title title
     */
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle == null) return;
        mToolbarTitle.setText(title);
    }


    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return true:显示，false:隐藏
     */
    public boolean isShowBacking() {
        return true;
    }

    /**
     * 是否显示Toolbar，默认显示,可在子类重写该方法.
     *
     * @return true:显示，false:隐藏
     */
    public boolean isShowToolbar() {
        return true;
    }


    /**
     * 是否要在子类中重写Toolbar的返回事件，默认关闭键盘，关闭Activity,可在子类重写该方法.
     *
     * @return true:覆盖，false:不覆盖
     */
    public boolean isChangeToolbarBackEvent() {
        return false;
    }

    /**
     * 是否显示Toolbar底部的阴影，默认显示,可在子类重写该方法.
     *
     * @return true:显示，false:隐藏
     */
    public boolean isShowToolbarBottomLine() {
        return true;
    }

}
