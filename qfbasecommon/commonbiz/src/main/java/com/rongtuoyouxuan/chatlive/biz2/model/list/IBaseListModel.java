package com.rongtuoyouxuan.chatlive.biz2.model.list;


import java.util.List;

public interface IBaseListModel<T> {

    public List<T> getListData();
    public void setListData(List<T> list);
}
