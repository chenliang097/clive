package com.rongtuoyouxuan.chatlive.biz2.model.login.response;

import androidx.annotation.Keep;

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 好友、关注、粉丝列表
 * @Author : jianbo
 * @Date : 2022/8/23  11:12
 */
@Keep
public class ContactListResponse extends BaseListModel<ContactListResponse.DataBean.UserBean> {

    @SerializedName("data")
    public DataBean data;

    @Override
    public List<DataBean.UserBean> getListData() {
        if (null != data && data.list != null) {
            return data.list;
        }
        return new ArrayList<>();
    }

    public static class DataBean {

        public List<UserBean> list;

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
         * total
         */
        @SerializedName("total")
        public int totalSize;

        public static class UserBean {

            @SerializedName("uid")
            public long uid;

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
             * signature
             */
            @SerializedName("signature")
            public String signature;

            /**
             * userLevel
             */
            @SerializedName("user_level")
            public long userLevel;

            /**
             * anchorLevel
             */
            @SerializedName("anchor_level")
            public long anchorLevel;

            /**
             * isOnLine
             */
            @SerializedName("is_on_line")
            public boolean isOnLine;

            /**
             * followedAt
             */
            @SerializedName("followed_at")
            public String followedAt;

            /**
             * followsHas
             */
            @SerializedName("follows_has")
            public boolean followsHas;

            /**
             * isOnLive
             */
            @SerializedName("is_on_live")
            public boolean isOnLive;

            /**
             * liveId
             */
            @SerializedName("live_id")
            public long liveId;

            /**
             * 认证状态 0 未认证即为普通用户 1 认证主播
             */
            @SerializedName("realcert_status")
            public int realcertStatus;

            @Override
            public String toString() {
                return "UserBean{" +
                        "uid=" + uid +
                        ", nickname='" + nickname + '\'' +
                        ", gender=" + gender +
                        ", avatar='" + avatar + '\'' +
                        ", signature='" + signature + '\'' +
                        ", userLevel=" + userLevel +
                        ", anchorLevel=" + anchorLevel +
                        ", isOnLine=" + isOnLine +
                        ", followedAt='" + followedAt + '\'' +
                        ", followsHas=" + followsHas +
                        ", isOnLive=" + isOnLive +
                        ", liveId=" + liveId +
                        '}';
            }
        }
    }
}