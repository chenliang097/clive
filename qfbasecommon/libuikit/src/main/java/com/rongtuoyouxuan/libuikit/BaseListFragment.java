package com.rongtuoyouxuan.libuikit;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel;
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent;
import com.rongtuoyouxuan.chatlive.util.Pair;
import com.rongtuoyouxuan.libuikit.viewmodel.BaseListFragmentViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;

import static com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent.EVENT_LOAD_INIT_OR_RETRY;
import static com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent.EVENT_LOAD_MORE;


public abstract class BaseListFragment<ViewModel extends BaseListFragmentViewModel, Model extends BaseListModel>
        extends BaseStatusFragment implements OnLoadMoreListener {

    protected BaseQuickAdapter adapter;
    protected RecyclerView mRecyclerView;
    protected boolean isRefreshEnabled = false;
    protected ViewModel viewModel;
    private boolean isFirstLoadMore = false;

    public static <ViewModel extends BaseListFragmentViewModel> ViewModel obtainViewModel(FragmentActivity activity, Class<ViewModel> clz) {
        return ViewModelProviders.of(activity).get(clz);
    }

    public static <ViewModel extends BaseListFragmentViewModel> ViewModel obtainViewModel(Fragment fragment, Class<ViewModel> clz) {
        return ViewModelProviders.of(fragment).get(clz);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
        initObserver();
    }

    protected void initObserver() {
        viewModel.getData().observe(this, new Observer<Model>() {
            @Override
            public void onChanged(@Nullable Model model) {
                setListData(model);
            }
        });
        viewModel.addItemEvent.observeOnce(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void o) {
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.addItemsEvent.observeOnce(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer size) {
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.deleteItemEvent.observeOnce(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer p) {
                adapter.remove(p);
            }
        });

        viewModel.insertItemEvent.observeOnce(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer p) {
                adapter.notifyItemChanged(p);
            }
        });

        viewModel.insertItemsEvent.observeOnce(this, new Observer<Pair<Integer, Integer>>() {
            @Override
            public void onChanged(@Nullable Pair<Integer, Integer> pair) {
                adapter.notifyItemRangeInserted(pair.first, pair.second);
            }
        });

        viewModel.updateItemEvent.observeOnce(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer p) {
                adapter.notifyItemChanged(p);
            }
        });
    }

    @Override
    public void initViews(View rootView) {
        mRecyclerView = createRecyclerView();
        mRecyclerView.setLayoutManager(createLayoutManager());
        mRecyclerView.setAdapter(adapter);
        adapter.getLoadMoreModule().setOnLoadMoreListener(this);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataInonViewCreated();
    }

    public void initDataInonViewCreated() {
        if (adapter.getData().isEmpty()) {
            viewModel.loadData(EVENT_LOAD_INIT_OR_RETRY);
        }
    }

    @Override
    public void initStatusView() {
        super.initStatusView();
        adapter = createAdapter();
        if (statusView != null) {
            adapter.setEmptyView((View) statusView);
        }
    }

    @Override
    public void onLoadMore() {
        loadData(LoadEvent.EVENT_LOAD_MORE);

    }

    public void setListData(Model data) {
        switch (data.event) {
            case EVENT_LOAD_INIT_OR_RETRY:
                if (data.getListData() != null && !data.getListData().isEmpty() && data.errCode == 0) {
                    adapter.setNewData(data.getListData());
                } else if (data.errCode == 0 && (data.getListData() == null || data.getListData().isEmpty())) {
                    showEmpty();
                } else {
                    showError(EVENT_LOAD_INIT_OR_RETRY);
                }
                if (data.getListData().size() > 0) {
                    adapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    adapter.getLoadMoreModule().loadMoreEnd(true);
                }
                break;
            case EVENT_PULL_TO_REFRESH:
                if (data.getListData() != null && !data.getListData().isEmpty()) {
                    adapter.setNewData(data.getListData());
                } else {
                    showEmpty();
                }
                if (data.getListData().size() > 0) {
                    adapter.getLoadMoreModule().loadMoreComplete();
                } else {
                    adapter.getLoadMoreModule().loadMoreEnd(true);
                }
                break;
            case EVENT_LOAD_MORE:
                if (data.errCode == 0) {
                    if (data.getListData() != null) {
                        adapter.addData(data.getListData());
                    } else {
                        showError(EVENT_LOAD_MORE);
                    }
                    if (data.getListData().size() > 0) {
                        adapter.getLoadMoreModule().loadMoreComplete();
                    } else {
                        adapter.getLoadMoreModule().loadMoreEnd(true);
                    }
                } else {
                    showError(EVENT_LOAD_MORE);
                }
                break;
        }
    }

    public void showLoading(LoadEvent loadEvent) {
        if (loadEvent == EVENT_LOAD_INIT_OR_RETRY) {
            showLoading();
        }
    }

    public void showError(LoadEvent loadEvent) {
        if (loadEvent == LoadEvent.EVENT_LOAD_MORE) {
            onLoadMoreError();
            isRefreshEnabled = true;
        } else if (loadEvent == LoadEvent.EVENT_PULL_TO_REFRESH) {
            if (adapter.getData().isEmpty() && adapter.getHeaderLayoutCount() == 0) {
                showError();
                isRefreshEnabled = false;
            } else {
                isRefreshEnabled = true;
            }
        } else if (loadEvent == EVENT_LOAD_INIT_OR_RETRY) {
            showError();
            isRefreshEnabled = false;
        }
    }

    @Override
    protected void onErrorViewClicked() {
        showLoading();
        loadData(EVENT_LOAD_INIT_OR_RETRY);
    }

    protected void loadData(LoadEvent loadEvent) {
        viewModel.loadData(loadEvent);
    }

    protected void onLoadMoreError() {
        adapter.getLoadMoreModule().loadMoreFail();
    }

    public void showEmpty() {
        super.showEmpty();
        adapter.setNewInstance(null);
    }

    /**
     * Create adapter
     *
     * @return BaseLoadMoreAdapter
     */
    protected abstract BaseQuickAdapter createAdapter();

    /**
     * Create recyclerView
     *
     * @return RecyclerView
     */
    protected abstract RecyclerView createRecyclerView();

    /**
     * Create LayoutManager
     *
     * @return RecyclerView.LayoutManager
     */
    protected abstract RecyclerView.LayoutManager createLayoutManager();

    protected abstract ViewModel createViewModel();
}
