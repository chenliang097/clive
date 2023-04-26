package com.rongtuoyouxuan.chatlive.util;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public interface BaseItem extends MultiItemEntity {
    boolean isSameItem(BaseItem item);
    boolean isSameContent(BaseItem item);
}
