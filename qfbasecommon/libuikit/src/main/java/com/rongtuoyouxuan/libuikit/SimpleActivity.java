package com.rongtuoyouxuan.libuikit;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.rongtuoyouxuan.libuikit.widget.TitleBar;

public abstract class SimpleActivity extends LanguageActivity {
    protected Context mContext;
    protected View mRootView;
    protected TitleBar mTitleBar;
    protected boolean isHasStatusBar = true;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = View.inflate(this, getLayoutResId(), null);
        setContentView(mRootView);

        mContext = this;
        //路由注册
        ARouter.getInstance().inject(this);
        //设置toolbar
        initToolBar();
        //状态栏设置
        statusBarSetting();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initListener();
    }

    protected abstract int getLayoutResId();

    public abstract void initData();

    public void statusBarSetting() {
        if (!isHasStatusBar) {
            return;
        }
        //设置状态栏 默认为白色
        BarUtils.transparentStatusBar(this);
        BarUtils.setStatusBarLightMode(this, true);
        BarUtils.addMarginTopEqualStatusBarHeight(mRootView);
    }

    public abstract void initListener();

    protected <T extends AndroidViewModel> T getViewModel(Class<T> tClass) {
        return new ViewModelProvider(this).get(tClass);
    }

    protected void initToolBar() {
        View titleView = findViewById(R.id.rl_title_bar);
        if (titleView == null) {
            return;
        }
        mTitleBar = new TitleBar(this);
        mTitleBar.setLeftIco(R.drawable.icon_white_back)
                .setLeftIcoListening(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

    }

    protected void showDialogLoading() {
        TransferLoadingUtil.showDialogLoading(this);
    }

    protected void dismissDialogLoading() {
        TransferLoadingUtil.dismissDialogLoading(this);
    }
}
