package com.rongtuoyouxuan.chatlive.biz2.model.login.response;

import androidx.annotation.Keep;

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 批量获取用户信息
 * @Author : jianbo
 * @Date : 2022/8/23  11:12
 */
@Keep
public class UserListResponse extends BaseListModel<BatchUserInfo> {

    @SerializedName("data")
    public DataBean data;

    @Override
    public List<BatchUserInfo> getListData() {
        if (null != data && data.list != null) {
            return data.list;
        }
        return new ArrayList<>();
    }

    public static class DataBean {

        @SerializedName("items")
        public List<BatchUserInfo> list;

    }
}