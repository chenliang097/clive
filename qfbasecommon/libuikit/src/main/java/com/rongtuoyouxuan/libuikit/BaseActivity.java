package com.rongtuoyouxuan.libuikit;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rongtuoyouxuan.chatlive.util.KeyBoardUtils;
import com.gyf.barlibrary.ImmersionBar;

public class BaseActivity extends LanguageActivity {

    public ImmersionBar mImmersionBar;
    protected Handler handler = new Handler(Looper.getMainLooper());
    private AppCompatTextView mToolbarTitle;
    private AppCompatTextView mToolbarSubTitle;
    private Toolbar mToolbar;
    private View mViewShadow;
    private AppCompatTextView mTvToolbarBack;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View rootView = View.inflate(this, layoutResID, null);
        super.setContentView(rootView);
        //路由注册
        ARouter.getInstance().inject(this);
        rootView.setFitsSystemWindows(fitsSystemWindows());
        rootView.setBackgroundColor(statusbarColor());
        mToolbar = findViewById(R.id.app_common_toolbar_layout);
        if (mToolbar == null) return;
        mToolbarTitle = findViewById(R.id.app_common_tv_toolbar_title);
        mToolbarSubTitle = findViewById(R.id.app_common_tv_toolbar_subtitle);
        mTvToolbarBack = findViewById(R.id.tv_app_common_tv_toolbar_back);
        setSupportActionBar(mToolbar);//将Toolbar显示到界面
        mViewShadow = findViewById(R.id.toolbar_shadow);
        if (mToolbarTitle == null) return;
        mToolbarTitle.setText(getTitle());//getTitle()的值是activity的android:lable属性值
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayShowTitleEnabled(false);//设置默认的标题不显示

    }

    protected boolean fitsSystemWindows() {
        return true;
    }

    protected int statusbarColor() {
        return getResources().getColor(R.color.qf_libutils_color_white);
    }

    protected boolean useDarkFont() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mImmersionBar == null) {
            initImmersionBar();
        }

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

        if (isShowBackTextView()) {
            mTvToolbarBack.setVisibility(View.VISIBLE);
            mTvToolbarBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    KeyBoardUtils.hideSoftInputBox(BaseActivity.this);
                    onBackPressed();
                }
            });
        } else {
            mTvToolbarBack.setVisibility(View.GONE);
        }
    }

    public void hideBack() {
        Toolbar toolbar = getToolbar();
        if (toolbar == null) return;
        toolbar.setNavigationIcon(null);
        toolbar.setNavigationOnClickListener(null);
    }


    public void hideToolBar() {
        if (mToolbar.getVisibility() != View.GONE) mToolbar.setVisibility(View.GONE);
    }


    public void showToolBar() {
        if (mToolbar.getVisibility() != View.VISIBLE) mToolbar.setVisibility(View.VISIBLE);
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    public void showBack() {
        //setNavigationIcon必须在setSupportActionBar(toolbar);方法后面加入
        Toolbar toolbar = getToolbar();
        if (toolbar == null) return;
        toolbar.setNavigationIcon(R.drawable.icon_common_topnavigation_lefttarrowback);
        if (isChangeToolbarBackEvent()) return;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideSoftInputBox(BaseActivity.this);
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

    public void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar
                .transparentStatusBar()
                .statusBarDarkFont(useDarkFont(), 0.2f)
                .init();
    }


    /**
     * 获取头部标题的TextView
     *
     * @return mToolbarTitle
     */
    public AppCompatTextView getToolbarTitle() {
        return mToolbarTitle;
    }


    /**
     * 获取头部标题的TextView
     *
     * @return mToolbarSubTitle
     */
    public AppCompatTextView getToolbarSubTitle() {
        return mToolbarSubTitle;
    }


    public TextView getBackTextView() {
        return mTvToolbarBack;
    }

    /**
     * 设置头部标题
     *
     * @param title title
     */
    public void setToolBarTitle(CharSequence title) {
        if (mToolbarTitle == null || TextUtils.isEmpty(title)) return;
        mToolbarTitle.setText(title);
    }


    /**
     * 返回按钮是TextView,设置文字
     *
     * @param title backText
     */
    public void setToolbarLeftBackTextView(CharSequence title) {
        if (mTvToolbarBack == null || TextUtils.isEmpty(title)) return;
        mTvToolbarBack.setText(title);
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
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return true:显示，false:隐藏
     */
    public boolean isShowBackTextView() {
        return false;
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

    @Override
    protected void onDestroy() {
        if (mImmersionBar != null) mImmersionBar.destroy();
        mImmersionBar = null;
        dismissDialogLoading();
        super.onDestroy();
    }

    @Override
    public void finish() {
        KeyBoardUtils.hideSoftInputBox(this);
        super.finish();
    }

    protected void showDialogLoading() {
        TransferLoadingUtil.showDialogLoading(this);
    }

    protected void showDialogLoading(String msg) {
        TransferLoadingUtil.showDialogLoading(this,msg);
    }

    protected void dismissDialogLoading() {
        TransferLoadingUtil.dismissDialogLoading(this);
    }


}
