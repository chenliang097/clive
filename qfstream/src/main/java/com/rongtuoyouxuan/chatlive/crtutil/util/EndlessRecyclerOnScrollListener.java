package com.rongtuoyouxuan.chatlive.crtutil.util;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Custom Scroll listener for RecyclerView.
 * Based on implementation https://gist.github.com/ssinss/e06f12ef66c51252563e
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = "EndlessScrollListener";

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 0; // The minimum amount of items to have below your current scroll position before loading more.
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;

    private int currentPage = 1;

    private RecyclerViewPositionHelper mRecyclerViewHelper;
    private boolean isBottom;
    private boolean mIsSuccess = true;
    private boolean mIsZero = false;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        isBottom = isSlideToBottom(recyclerView);
        if (dy > 0) {
            mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mRecyclerViewHelper.getItemCount();
            firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached
                // Do something
                currentPage++;
                onLoadMore(currentPage);
                loading = true;
            }
        }
    }

    private boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        if (isBottom && !mIsSuccess && newState == RecyclerView.SCROLL_STATE_IDLE) {
            onLoadMore(currentPage);
            return;
        }

        if (isBottom && mIsZero && newState == RecyclerView.SCROLL_STATE_IDLE) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    /**
     * 网络请求成功的标示
     * @param isOk isOk
     */
    public void setLoadSucceedFlag(boolean isOk) {
        this.mIsSuccess = isOk;
    }

    public void setZeroFlag(boolean isZero){
        mIsZero = isZero;
    }

    public void resetCurrentPage() {
        mIsSuccess= true;
        mIsZero = false;
        previousTotal = 0;
        loading = true;
        visibleThreshold = 0;
        firstVisibleItem = 0;
        visibleItemCount = 0;
        totalItemCount = 0;
        currentPage = 1;
    }

    //Start loading
    public abstract void onLoadMore(int currentPage);
}