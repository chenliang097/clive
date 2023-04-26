package com.rongtuoyouxuan.chatlive.biz2.model.im.response;

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * PackageName    com.boboo.chatlive.biz2.model.im.response
 * FileName       BlackListModel
 * Created on     2022/8/14 21:16
 * function
 *
 * @author Jason
 * Email          jianbo311@163.com
 * ================================================
 */
public class BlackListModel extends BaseListModel<BlackListModel.DataBean.BlackUserBean> {

    /**
     * list : [{"user_id":"string","show_id":"string","nickname":"string","gender":"string","avatar":"string","block_id":"string","user_level":0,"role":"string","anchor_level":0}]
     * page : 1
     * size : 10
     * page_total : 10
     * total : 10
     */

    @SerializedName("data")
    public DataBean data;

    @Override
    public List<DataBean.BlackUserBean> getListData() {
        if (null != data && data.list != null) {
            return data.list;
        }
        return new ArrayList<>();
    }

    public static class DataBean {
        /**
         * list
         */
        @SerializedName("list")
        public List<BlackUserBean> list;

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

        public static class BlackUserBean {

            /**
             * user_id : string
             * show_id : string
             * nickname : string
             * gender : string
             * avatar : string
             * block_id : string
             * user_level : 0
             * role : string
             * anchor_level : 0
             */

            /**
             * userId
             */
            @SerializedName("user_id")
            public long userId;
            /**
             * showId
             */
            @SerializedName("show_id")
            public String showId;
            /**
             * nickname
             */
            @SerializedName("nickname")
            public String nickname;
            /**
             * gender
             */
            @SerializedName("gender")
            public int gender;
            /**
             * avatar
             */
            @SerializedName("avatar")
            public String avatar;
            /**
             * blockId
             */
            @SerializedName("block_id")
            public long blockId;
            /**
             * userLevel
             */
            @SerializedName("user_level")
            public long userLevel;
            /**
             * anchorLevel
             */
            @SerializedName("anchor_level")
            public int anchorLevel;
            /**
             * createdAt
             */
            @SerializedName("created_at")
            public long createdAt;

            /**
             * 认证状态 0 未认证即为普通用户 1 认证主播
             */
            @SerializedName("realcert_status")
            public int realcertStatus;
        }
    }
}