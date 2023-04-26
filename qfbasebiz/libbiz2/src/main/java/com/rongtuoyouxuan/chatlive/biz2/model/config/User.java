package com.rongtuoyouxuan.chatlive.biz2.model.config;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.rongtuoyouxuan.chatlive.util.NumUtils;

@Entity(tableName = "user_table")
public class User {
    public final static int FRIEND_STATUS_NOT_INIT = 0;//非好友（未初始化）
    public final static int FRIEND_STATUS_NOT_FRIEND = 1;//非好友
    public final static int FRIEND_STATUS_IS_FRIEND = 2;//好友
    public final static int NO_ADD_FRIEND = 0;
    public final static int SEND_ADD_FRIEND = 1;
    public final static int RECV_ADD_FRIEND = 2;
    //用户id
    @PrimaryKey @NonNull
    private Long uid;
    //用户名
    private String name = "";
    //用户性别
    private String gender = "";
    //用户头像地址
    private String icon = "";
    //用户是否在线
    @Ignore
    private String online = "0";
    //最后收到的消息
    private String lastContent = "";
    //最后一条消息的时间
    private long updateTime = 0;
    //未读消息数
    private int unReadMsgCount = 0;
    private int friendStatus = FRIEND_STATUS_NOT_INIT;
    private int addFriendEvent = NO_ADD_FRIEND;

    //扩展参数
    @ColumnInfo(defaultValue = "") @NonNull
    private String text1 = "";//用作置顶标识
    @ColumnInfo(defaultValue = "") @NonNull
    private String text2 = "";
    @ColumnInfo(defaultValue = "") @NonNull
    private String text3 = "";
    @ColumnInfo(defaultValue = "0") @NonNull
    private String text4 = "";
    @ColumnInfo(defaultValue = "0") @NonNull
    private String text5 = "";
    @ColumnInfo(defaultValue = "0") @NonNull
    private int int1 = 0;
    @ColumnInfo(defaultValue = "0") @NonNull
    private int int2 = 0;
    @ColumnInfo(defaultValue = "0") @NonNull
    private int int3 = 0;
    @ColumnInfo(defaultValue = "0") @NonNull
    private long long4 = 0;
    @ColumnInfo(defaultValue = "0") @NonNull
    private long long5 = 0;

    public User() {
    }

    public User(Long uid, String name, String gender, String icon, String online, String lastContent, long updateTime) {
        super();
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.icon = icon;
        this.online = online;
        this.lastContent=lastContent;
        this.updateTime=updateTime;
    }
    public User(Long uid, String name, String gender, String icon, String online, String lastContent, long updateTime, int msgCount) {
        super();
        this.uid = uid;
        this.name = name;
        this.gender = gender;
        this.icon = icon;
        this.online = online;
        this.lastContent=lastContent;
        this.updateTime=updateTime;
        this.unReadMsgCount = msgCount;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastContent() {
        return lastContent;
    }

    public void setLastContent(String lastContent) {
        this.lastContent = lastContent;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUnReadMsgCount() {
        return unReadMsgCount;
    }

    public void setUnReadMsgCount(int unReadMsgCount) {
        this.unReadMsgCount = unReadMsgCount;
    }

    public void increaseUnReadMsg() {
        this.unReadMsgCount++;
    }

    public void decreaseUnReadMsg() {
        if (this.unReadMsgCount > 0) {
            unReadMsgCount--;
        }
    }

    //成为好友
    public void becomeFriends() {
        setFriendStatus(2);
    }

    //是否是好友
    public boolean isFriends() {
        return getFriendStatus() == 2;
    }

    public int getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(int status) {
        friendStatus = status;
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", icon='" + icon + '\'' +
                ", online='" + online + '\'' +
                ", lastContent='" + lastContent + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", msgCount=" + unReadMsgCount +
                '}';
    }


    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5;
    }

    public int getInt1() {
        return int1;
    }

    public void setInt1(int int1) {
        this.int1 = int1;
    }

    public int getInt2() {
        return int2;
    }

    public void setInt2(int int2) {
        this.int2 = int2;
    }
    //目前使用int2 标识ml字段
    public int getML() {
        return int2;
    }

    public void setML(int int2) {
        this.int2 = int2;
    }

    public int getInt3() {
        return int3;
    }

    public void setInt3(int int3) {
        this.int3 = int3;
    }

    public long getLong4() {
        return long4;
    }

    public void setLong4(long long4) {
        this.long4 = long4;
    }

    public long getLong5() {
        return long5;
    }

    public void setLong5(long long5) {
        this.long5 = long5;
    }

    public long getTop() {
        String val = getText1();
        return NumUtils.parseLong(val);
    }

    public boolean isTop() {
        return getTop() > 0;
    }

    public void setTop(long top) {
        setText1(top+"");
    }

    //目前使用text1 在car类型消息标识cid
    public String getCid() {
        return text1;
    }

    public void setCid(String text1) {
        this.text1 = text1;
    }

    public int getLastMsgId() {
        return getInt1();
    }

    //int1固定使用lastmsgId
    public void setLastMsgId(int lastmsgId) {
        setInt1(lastmsgId);
    }

    public int getAddFriendEvent() {
        return addFriendEvent;
    }

    public void setAddFriendEvent(int addFriendEvent) {
        this.addFriendEvent = addFriendEvent;
    }
}
