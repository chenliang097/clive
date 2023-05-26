package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.response;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * ================================================
 * Created on     2022/8/14 22:46
 * function
 *
 * @author Jason
 * Email          jianbo311@163.com
 * ================================================
 */
public class GroupUserModel extends BaseModel {

    /**
     * data
     */
    @SerializedName("data")
    public DataBean data;

//    /**
//     * show_id : string
//     * nickname : string
//     * gender : string
//     * avatar : string
//     * user_id : long
//     * user_level : 0
//     * is_follow : 0
//     * permissions : string
//     * live_id : string
//     * is_online : 0
//     * role : string
//     * anchor_level : 0
//     * owner_user_id : long
//     * type : 0
//     * created_at : string
//     * updated_at : string
//     */

    public static class DataBean {
        /**
         * showId
         */
        @SerializedName("show_id")
        public int showId;
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
         * userId
         */
        @SerializedName("user_id")
        public long userId;
        /**
         * userLevel
         */
        @SerializedName("user_level")
        public int userLevel;
        /**
         * isFollow
         */
        @SerializedName("is_follow")
        public int isFollow;
        /**
         * permissions  群成员权限
         */
        @SerializedName("permissions")
        public int permissions;
        /**
         * liveId 直播Id, 0表示没有直播
         */
        @SerializedName("live_id")
        public int liveId;
        /**
         * isOnline 是否在线
         */
        @SerializedName("is_online")
        public int isOnline;
        /**
         * role 角色
         */
        @SerializedName("role")
        public String role;
        /**
         * anchorLevel  主播等级
         */
        @SerializedName("anchor_level")
        public int anchorLevel;
        /**
         * ownerUserId  群主id
         */
        @SerializedName("owner_user_id")
        public long ownerUserId;
        /**
         * type 群类型 1粉丝群
         */
        @SerializedName("type")
        public int type;
        /**
         * createdAt
         */
        @SerializedName("created_at")
        public int createdAt;
        /**
         * updatedAt    更新时间
         */
        @SerializedName("updated_at")
        public int updatedAt;
        /**
         * groupId
         */
        @SerializedName("group_id")
        public int groupId;

        /**
         * 认证状态 0 未认证即为普通用户 1 认证主播
         */
        @SerializedName("realcert_status")
        public int realcertStatus;
    }
}
