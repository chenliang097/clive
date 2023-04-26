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
public class GroupInfoListModel extends BaseListModel<GroupInfo> {

    @SerializedName("data")
    public DataBean data;

    /**
     * list : [{"id":"string","owner_user_id":"string","type":0,"name":"string","permissions":"string","status":0,"total":"string","avatars":["string"],"created_at":"string","updated_at":"string"}]
     * page : 1
     * size : 10
     * page_total : 10
     * total : 10
     */

    @Override
    public List<GroupInfo> getListData() {
        if (null != data && data.list != null) {
            return data.list;
        }
        return new ArrayList<>();
    }

    public static class DataBean {

        /**
         * page
         */
        @SerializedName("page")
        public int page;

        /**
         * size
         */
        @SerializedName("size")
        public int size;

        /**
         * pageTotal
         */
        @SerializedName("page_total")
        public int pageTotal;

        /**
         * total
         */
        @SerializedName("total")
        public int total;

        @SerializedName("list")
        public List<GroupInfo> list;
    }
}