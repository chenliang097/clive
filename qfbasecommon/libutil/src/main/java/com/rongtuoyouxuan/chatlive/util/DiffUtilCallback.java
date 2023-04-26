package com.rongtuoyouxuan.chatlive.util;

import androidx.recyclerview.widget.DiffUtil;

import java.util.Collections;
import java.util.List;

/**
 * 创建人:yangzhiqian
 * 创建时间:2018/10/10 17:38
 */
public class DiffUtilCallback<T> extends DiffUtil.Callback {
    private List<T> mOldData = Collections.EMPTY_LIST;
    private List<T> mNewData = Collections.EMPTY_LIST;

    public DiffUtilCallback() {
    }

    public DiffUtilCallback(List<T> mOldData, List<T> mNewData) {
        setData(mOldData, mNewData);
    }

    public void setData(List<T> mOldData, List<T> mNewData) {
        this.mOldData = mOldData;
        this.mNewData = mNewData;
    }

    @Override
    public int getOldListSize() {
        return mOldData == null ? 0 : mOldData.size();
    }

    @Override
    public int getNewListSize() {
        return mNewData == null ? 0 : mNewData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldData.get(oldItemPosition) == mNewData.get(newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldData.get(oldItemPosition) == mNewData.get(newItemPosition);
    }
}
