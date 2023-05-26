package com.rongtuoyouxuan.chatlive.crtutil.util;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public interface BaseItem extends MultiItemEntity {
    boolean isSameItem(BaseItem item);
    boolean isSameContent(BaseItem item);
}
