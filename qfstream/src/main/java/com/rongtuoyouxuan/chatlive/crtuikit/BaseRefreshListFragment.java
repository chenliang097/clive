package com.rongtuoyouxuan.chatlive.crtuikit;

import androidx.annotation.NonNull;

import android.view.View;

import com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.BaseListModel;
import com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.LoadEvent;
import com.rongtuoyouxuan.chatlive.crtuikit.viewmodel.BaseListFragmentViewModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


public abstract class BaseRefreshListFragment<ViewModel extends BaseListFragmentViewModel, Model extends BaseListModel>
        extends BaseListFragment<ViewModel, Model> implements OnRefreshListener {

    protected SmartRefreshLayout smartRefreshLayout;

    @Override
    public void initViews(View rootView) {
        super.initViews(rootView);
        smartRefreshLayout = createSmartRefreshLayout();
        if (smartRefreshLayout != null) {
            smartRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void setListData(Model data) {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.setEnableRefresh(true);
        }
        super.setListData(data);
    }

    @Override
    public void showError(LoadEvent loadEvent) {
        super.showError(loadEvent);
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh();
            smartRefreshLayout.setEnableRefresh(isRefreshEnabled);
        }
    }

    protected void scrollToTopAndRefresh() {
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(0);
        }

        if (smartRefreshLayout != null) {
            if (smartRefreshLayout.autoRefresh()) {
                onRefresh(smartRefreshLayout);
            }
        }
    }

    /**
     * Create smartRefreshLayout
     *
     * @return SmartRefreshLayout
     */
    protected abstract SmartRefreshLayout createSmartRefreshLayout();

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        loadData(LoadEvent.EVENT_PULL_TO_REFRESH);
    }
}
