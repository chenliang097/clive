package com.rongtuoyouxuan.chatlive.crtuikit;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;


/**
 * @author: limuyang
 * @date: 2019-12-04
 * @Description: 需要【向下加载更多】功能的，[Adapter]继承此类
 */
public abstract class BaseLoadMoreAdapter<T, H extends BaseViewHolder>
        extends BaseQuickAdapter<T, BaseViewHolder> implements LoadMoreModule {

    public BaseLoadMoreAdapter(int layoutResId) {
        super(layoutResId);
    }

}
