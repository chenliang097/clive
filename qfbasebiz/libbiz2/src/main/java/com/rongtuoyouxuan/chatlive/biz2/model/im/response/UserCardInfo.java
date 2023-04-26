package com.rongtuoyouxuan.chatlive.biz2.model.im.response;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class UserCardInfo {

    /**
     * anchor_level : 0
     * avatar : string
     * birthday : string
     * country_code : string
     * fans_total : 0
     * follow_total : 0
     * gender : 0
     * is_fans_group : true
     * is_follows : true
     * is_on_line : true
     * is_on_live : true
     * live_id : 0
     * mobile : string
     * nickname : string
     * realcert_status : 0
     * region_code : string
     * role : string
     * show_id : string
     * signature : string
     * user_id : 0
     * user_level : 0
     * write_off_status : 0
     */

    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    @SerializedName("user_id")
    private long userId;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("user_level")
    private int userLevel;

    @SerializedName("anchor_level")
    private int anchorLevel;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("fans_total")
    private int fansTotal;

    @SerializedName("follow_total")
    private int followTotal;

    @SerializedName("gender")
    private int gender;

    @SerializedName("is_fans_group")
    private boolean isFansGroup;

    @SerializedName("is_follows")
    private boolean isFollows;

    @SerializedName("is_on_line")
    private boolean isOnLine;

    @SerializedName("is_on_live")
    private boolean isOnLive;

    @SerializedName("live_id")
    private int liveId;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("realcert_status")
    private int realcertStatus;

    @SerializedName("region_code")
    private String regionCode;

    @SerializedName("role")
    private String role;

    @SerializedName("send_coin")
    private int sendCoin;

    @SerializedName("show_id")
    private String showId;

    @SerializedName("signature")
    private String signature;

    @SerializedName("write_off_status")
    private int writeOffStatus;

    public int getAnchorLevel() {
        return anchorLevel;
    }

    public void setAnchorLevel(int anchorLevel) {
        this.anchorLevel = anchorLevel;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getFansTotal() {
        return fansTotal;
    }

    public void setFansTotal(int fansTotal) {
        this.fansTotal = fansTotal;
    }

    public int getFollowTotal() {
        return followTotal;
    }

    public void setFollowTotal(int followTotal) {
        this.followTotal = followTotal;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public boolean isFansGroup() {
        return isFansGroup;
    }

    public void setFansGroup(boolean fansGroup) {
        isFansGroup = fansGroup;
    }

    public boolean isFollows() {
        return isFollows;
    }

    public void setFollows(boolean follows) {
        isFollows = follows;
    }

    public boolean isOnLine() {
        return isOnLine;
    }

    public void setOnLine(boolean onLine) {
        isOnLine = onLine;
    }

    public boolean isOnLive() {
        return isOnLive;
    }

    public void setOnLive(boolean onLive) {
        isOnLive = onLive;
    }

    public int getLiveId() {
        return liveId;
    }

    public void setLiveId(int liveId) {
        this.liveId = liveId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRealcertStatus() {
        return realcertStatus;
    }

    public void setRealcertStatus(int realcertStatus) {
        this.realcertStatus = realcertStatus;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getSendCoin() {
        return sendCoin;
    }

    public void setSendCoin(int sendCoin) {
        this.sendCoin = sendCoin;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getWriteOffStatus() {
        return writeOffStatus;
    }

    public void setWriteOffStatus(int writeOffStatus) {
        this.writeOffStatus = writeOffStatus;
    }
}
