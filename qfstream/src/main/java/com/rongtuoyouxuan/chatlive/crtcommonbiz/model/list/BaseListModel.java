package com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;

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
