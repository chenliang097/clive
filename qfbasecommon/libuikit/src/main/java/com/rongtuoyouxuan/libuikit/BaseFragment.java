package com.rongtuoyouxuan.libuikit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rongtuoyouxuan.chatlive.util.ToastUtils;

/**
 * TODO 各种打点统计，语言
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;

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
            initViews(mRootView);
            initData();
            initLiveDataObserve();
        }
        return mRootView;
    }

    /**
     * set your layout res
     *
     * @return
     */
    protected abstract @LayoutRes
    int getLayoutRes();

    /**
     * init views
     *
     * @param rootView
     */
    protected abstract void initViews(View rootView);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * init liveDataObserve
     */
    protected void initLiveDataObserve() {

    }

    /**
     * load data
     */
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void showDialogLoading() {
        if (getActivity() != null && !getActivity().isDestroyed() && !getActivity().isFinishing()) {
            TransferLoadingUtil.showDialogLoading(getActivity());
        }
    }

    protected void showDialogLoading(String msg) {
        if (getActivity() != null && !getActivity().isDestroyed() && !getActivity().isFinishing()) {
            TransferLoadingUtil.showDialogLoading(getActivity(), msg);
        }
    }

    protected void dismissDialogLoading() {
        if (getActivity() != null && !getActivity().isDestroyed() && !getActivity().isFinishing()) {
            TransferLoadingUtil.dismissDialogLoading(getActivity());
        }
    }

    protected void toast(String msg){
        if (getActivity() != null && !getActivity().isDestroyed() && !getActivity().isFinishing()) {
            ToastUtils.show(getActivity(),msg);
        }
    }
}
