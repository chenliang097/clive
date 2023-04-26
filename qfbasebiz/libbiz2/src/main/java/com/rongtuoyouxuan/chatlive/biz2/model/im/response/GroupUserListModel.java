package com.rongtuoyouxuan.chatlive.biz2.model.im.response;

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Created on     2022/8/14 21:16
 * function
 *
 * @author Jason
 * Email          jianbo311@163.com
 * ================================================
 */
public class GroupUserListModel extends BaseListModel<GroupUserModel.DataBean> {

    /**
     * list : [{"show_id":"string","nickname":"string","gender":"string","avatar":"string","user_id":"string","user_level":0,"is_follow":0,"permissions":"string","live_id":"string","is_online":0,"role":"string","anchor_level":0,"owner_user_id":"string","type":0,"created_at":"string","updated_at":"string"}]
     * page : 1
     * size : 10
     * page_total : 10
     * total : 10
     */

    @SerializedName("data")
    public DataBean data;

    @Override
    public List<GroupUserModel.DataBean> getListData() {
        if (null != data && data.list != null) {
            return data.list;
        }
        return new ArrayList<>();
    }

    public static class DataBean {

        public String page;
        public String size;

        /**
         * pageTotal
         */
        @SerializedName("page_total")
        public int pageTotal;

        public String total;
        @SerializedName("list")
        public List<GroupUserModel.DataBean> list;
    }
}