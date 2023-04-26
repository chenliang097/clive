package com.rongtuoyouxuan.libuikit;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel;
import com.rongtuoyouxuan.libuikit.viewmodel.BaseListFragmentViewModel;

import static com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent.EVENT_PULL_TO_REFRESH;

public abstract class BaseIntervalRefreshListFragment<ViewModel extends BaseListFragmentViewModel, Model extends BaseListModel> extends BaseRefreshListFragment<ViewModel, Model> {

    /**
     * 默认刷新时间间隔 60S
     */
    protected long DEFAULT_INTERVAL_TIME = 60 * 1000l;
    protected boolean isFirst = true;
    protected long mPreRefreshTime = 0l;
    protected long mIntervalTime = DEFAULT_INTERVAL_TIME;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initDataInonViewCreated() {
        //取消这里的网络请求
    }

    @Override
    protected void onFragmentVisible() {
        super.onFragmentVisible();
        if (!isFirst && (overTime() || adapter.getData().isEmpty())) {
            loadData(EVENT_PULL_TO_REFRESH);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && (overTime() || adapter.getData().isEmpty())) {
            loadData(EVENT_PULL_TO_REFRESH);
        }
        isFirst = false;
    }

    @Override
    public void setListData(Model data) {
        super.setListData(data);
        mPreRefreshTime = System.currentTimeMillis();
    }

    protected boolean overTime() {
        return (System.currentTimeMillis() - mPreRefreshTime) > mIntervalTime;
    }

}
