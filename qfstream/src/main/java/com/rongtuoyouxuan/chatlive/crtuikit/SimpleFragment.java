package com.rongtuoyouxuan.chatlive.crtuikit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProvider;

public abstract class SimpleFragment extends Fragment {
    protected Context mContext;
    protected Activity mActivity;
    private boolean isVisibleToUser = false;
    private boolean isViewCreated = false;
    private boolean isLoadedData = false;
    private boolean isLoadEnable = false;
    protected View mRootView;

    //得到上下文 activity对象
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (Activity) context;
    }

    //这里进行绘制view
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        return mRootView;
    }


    //在这里进行请求数据和数据的更新
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initListener();
        isViewCreated = true;
        lazyLoading();
    }

    protected abstract int getLayoutResId();

    protected abstract void initData();

    public abstract void initListener();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        lazyLoading();
    }

    //懒加载验证
    private void lazyLoading() {
        if (isVisibleToUser && isViewCreated && !isLoadEnable) {
            isLoadEnable = true;
            lazyData();
        }
    }

    //懒加载请求数据逻辑 Mr秦
    protected void lazyData(){

    }

    protected void requestLazyData() {
    }

    protected <T extends AndroidViewModel> T getViewModel(Class<T> tClass) {
        return new ViewModelProvider(this).get(tClass);
    }

    @Override
    public void onDestroyView() {
        isViewCreated = false;
        super.onDestroyView();
    }

    //适用于Viewpage2及ViewPager中适用behavor，
    // onHiddenChanged适用于supperFragmentManager/childFragmentManager
    @Override
    public void onResume() {
        super.onResume();
        if (isVisible() && isViewCreated && !isLoadedData) {
            isLoadedData = true;
            requestLazyData();
        }
    }

    protected void showDialogLoading() {
        TransferLoadingUtil.showDialogLoading(getContext());
    }

    protected void dismissDialogLoading() {
        TransferLoadingUtil.dismissDialogLoading(getContext());
    }
}
