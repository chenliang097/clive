package com.rongtuoyouxuan.libuikit;

import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;


/**
 * @author: limuyang
 * @date: 2019-12-04
 * @Description: 需要【向下加载更多】功能的，[Adapter]继承此类
 */
public abstract class BaseLoadMoreMultiAdapter<T>
        extends BaseProviderMultiAdapter<T> implements LoadMoreModule {

}
