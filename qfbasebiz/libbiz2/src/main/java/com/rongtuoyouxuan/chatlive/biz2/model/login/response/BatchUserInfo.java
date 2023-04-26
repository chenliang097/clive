package com.rongtuoyouxuan.chatlive.biz2.model.login.response;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * @Description : 批量用户信息response
 * @Author : jianbo
 * @Date : 2022/10/24  15:00
 */
@Entity(tableName = "user_info")
public class BatchUserInfo {

    /**
     * uid
     */
    @PrimaryKey(autoGenerate = false)//主键是否自动增长，默认为false
    @SerializedName("uid")
    private long uid;

    /**
     * nickname
     */
    @SerializedName("nickname")
    private String nickname;

    /**
     * gender
     */
    @SerializedName("gender")
    private int gender;

    /**
     * avatar
     */
    @SerializedName("avatar")
    private String avatar;

    /**
     * 简介签名
     */
    @SerializedName("signature")
    private String signature;

    /**
     * 用户等级
     */
    @SerializedName("user_level")
    private int userLevel;

    /**
     * 主播等级
     */
    @SerializedName("anchor_level")
    private int anchorLevel;

    /**
     * 是否在线
     */
    @SerializedName("is_on_line")
    private boolean isOnLine;

    /**
     * 关注时间
     */
    @SerializedName("followed_at")
    private String followedAt;

    /**
     * 是否正在开播
     */
    @SerializedName("is_on_live")
    private boolean isOnLive;

    /**
     * 直播id
     */
    @SerializedName("live_id")
    private long liveId;

    /**
     * 认证状态 0 未认证即为普通用户 1 认证主播
     */
    @SerializedName("realcert_status")
    private int realcertStatus;

    /**
     * 登录国家
     */
    @SerializedName("login_country")
    private String loginCountry;

    /**
     * 当前用户角色 user guest
     */
    @SerializedName("role")
    private String role;

    public BatchUserInfo() {
    }

    public BatchUserInfo(long uid, String nickname, int gender, String avatar, String signature, int userLevel,
                         int anchorLevel, boolean isOnLine, boolean isOnLive, long liveId, int realcertStatus) {
        this.uid = uid;
        this.nickname = nickname;
        this.gender = gender;
        this.avatar = avatar;
        this.signature = signature;
        this.userLevel = userLevel;
        this.anchorLevel = anchorLevel;
        this.isOnLine = isOnLine;
        this.isOnLive = isOnLive;
        this.liveId = liveId;
        this.realcertStatus = realcertStatus;
    }

    public BatchUserInfo(long uid, String nickname, int gender, String avatar, String signature, int userLevel,
                         int anchorLevel, boolean isOnLine, String followedAt, boolean isOnLive, long liveId,
                         int realcertStatus, String loginCountry, String role) {
        this.uid = uid;
        this.nickname = nickname;
        this.gender = gender;
        this.avatar = avatar;
        this.signature = signature;
        this.userLevel = userLevel;
        this.anchorLevel = anchorLevel;
        this.isOnLine = isOnLine;
        this.followedAt = followedAt;
        this.isOnLive = isOnLive;
        this.liveId = liveId;
        this.realcertStatus = realcertStatus;
        this.loginCountry = loginCountry;
        this.role = role;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getAnchorLevel() {
        return anchorLevel;
    }

    public void setAnchorLevel(int anchorLevel) {
        this.anchorLevel = anchorLevel;
    }

    public boolean isOnLine() {
        return isOnLine;
    }

    public void setOnLine(boolean onLine) {
        isOnLine = onLine;
    }

    public String getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(String followedAt) {
        this.followedAt = followedAt;
    }

    public boolean isOnLive() {
        return isOnLive;
    }

    public void setOnLive(boolean onLive) {
        isOnLive = onLive;
    }

    public long getLiveId() {
        return liveId;
    }

    public void setLiveId(long liveId) {
        this.liveId = liveId;
    }

    public int getRealcertStatus() {
        return realcertStatus;
    }

    public void setRealcertStatus(int realcertStatus) {
        this.realcertStatus = realcertStatus;
    }

    public String getLoginCountry() {
        return loginCountry;
    }

    public void setLoginCountry(String loginCountry) {
        this.loginCountry = loginCountry;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "BatchUserInfo{" +
                "uid=" + uid +
                ", nickname='" + nickname + '\'' +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", signature='" + signature + '\'' +
                ", userLevel=" + userLevel +
                ", anchorLevel=" + anchorLevel +
                ", isOnLine=" + isOnLine +
                ", followedAt='" + followedAt + '\'' +
                ", isOnLive=" + isOnLive +
                ", liveId=" + liveId +
                ", realcertStatus=" + realcertStatus +
                ", loginCountry='" + loginCountry + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
