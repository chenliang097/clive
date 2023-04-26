package com.rongtuoyouxuan.chatlive.biz2.model.list;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class BaseListModel<T> extends BaseModel implements IBaseListModel<T> {
    public LoadEvent event = LoadEvent.EVENT_DEFAULT;

    List<T> list ;

    @Override
    public List<T> getListData() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public void setListData(List<T> list) {

    }
}
