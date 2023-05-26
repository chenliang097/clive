package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.response;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.response.convert.ListStringConverter;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "group_info")
@TypeConverters(ListStringConverter.class)
public class GroupInfo {
    /**
     * id
     */
    @PrimaryKey
    @SerializedName("id")
    public long id;

    /**
     * name 名称
     */
    @SerializedName("name")
    public String name;

    /**
     * ownerUserId 群主id
     */
    @SerializedName("owner_user_id")
    public long ownerUserId;

    /**
     * type 群类型 1粉丝群
     */
    @SerializedName("type")
    public int type;

    /**
     * total     成员数
     */
    @SerializedName("total")
    public int total;

    /**
     * avatars  群成员头像
     */
    @SerializedName("avatars")
    public List<String> avatars = new ArrayList<>();

    /**
     * flags  群权限
     */
    @SerializedName("flags")
    public int flags;

    /**
     * userPermissions   用户权限(位权限)
     */
    @SerializedName("user_permissions")
    public int userPermissions;

    /**
     * status    状态
     */
    @SerializedName("status")
    public int status;

    /**
     * createdAt    创建时间
     */
    @SerializedName("created_at")
    public long createdAt;

    /**
     * updatedAt    更新时间
     */
    @SerializedName("updated_at")
    public long updatedAt;

    /**
     * logo    群头像
     */
    @SerializedName("logo")
    public String logo;

    public String targetId;

    @Ignore
    public BaseMsg.MsgBody msgBody;

    public long time;
}