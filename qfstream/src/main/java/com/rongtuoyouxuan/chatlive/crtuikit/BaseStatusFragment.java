package com.rongtuoyouxuan.chatlive.crtuikit;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rongtuoyouxuan.chatlive.crtuikit.layout.IStatusView;

/**
 * TODO 各种打点统计，语言
 */
public abstract class BaseStatusFragment extends Fragment {

    protected View mRootView;
    protected IStatusView statusView;
    protected boolean isFragmentCreated = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mRootView) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        } else {
            mRootView = inflater.inflate(getLayoutRes(), null);
            initStatusView();
            initViews(mRootView);
            if (getUserVisibleHint()) {
                onFragmentVisible();
            }
            initdata();
        }
        isFragmentCreated = true;
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract void initdata();

    public void initStatusView() {
        statusView = createStatusView();
        if (statusView != null) {
            statusView.setOnErrorClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    onErrorViewClicked();
                }
            });
        }
    }

    /**
     * Called if the error view has been clicked.
     */
    protected abstract void onErrorViewClicked();

    protected void showEmpty() {
        if (statusView != null) {
            statusView.showEmpty();
        }
    }

    protected void showLoading() {
        if (statusView != null) {
            statusView.showLoading();
        }
    }

    protected void showError() {
        if (statusView != null) {
            statusView.showError();
        }
    }

    void showError(String msg) {
        if (statusView != null) {
            statusView.showError(msg);
        }
    }

    protected void showEmpty(String msg) {
        if (statusView != null) {
            statusView.showEmpty(msg);
        }
    }

    protected void showContent() {
        if (statusView != null) {
            statusView.showContent();
        }
    }

    protected abstract IStatusView createStatusView();

    protected abstract @LayoutRes
    int getLayoutRes();

    public abstract void initViews(View rootView);


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //可见
            userVisible();
        } else {
            //不可见
            userHide();
        }
        if (!isFragmentCreated) {
            return;
        }

        if (getUserVisibleHint() && isVisibleToUser) {
            onFragmentVisible();
        } else {
            onFragmentInVisible();
        }
    }

    protected void userVisible() {
    }

    protected void userHide() {
    }

    protected void onFragmentVisible() {

    }

    //调用逻辑:当页面onPause 或者 viewpager 切换导致的 setUserVisibleHint(false) 时调用
    protected void onFragmentInVisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFragmentCreated = false;
    }

}
