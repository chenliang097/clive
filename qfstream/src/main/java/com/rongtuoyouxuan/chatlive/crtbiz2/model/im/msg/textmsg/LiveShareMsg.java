package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg;

import androidx.annotation.Keep;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;

/*
 *Create by {Mr秦} on 2022/12/7
 */
@Keep
public class LiveShareMsg  extends BaseMsg.MsgBody {
    private AnchorBean anchor;
    private long live_id;
    private String live_pic;
    private String live_title;
    private int room_type;
    private int classify_id;
    private String live_type;

    public AnchorBean getAnchor() {
        return anchor;
    }

    public void setAnchor(AnchorBean anchor) {
        this.anchor = anchor;
    }

    public long getLive_id() {
        return live_id;
    }

    public void setLive_id(long live_id) {
        this.live_id = live_id;
    }

    public String getLive_pic() {
        return live_pic == null ? "" : live_pic;
    }

    public void setLive_pic(String live_pic) {
        this.live_pic = live_pic;
    }

    public String getLive_title() {
        return live_title == null ? "" : live_title;
    }

    public void setLive_title(String live_title) {
        this.live_title = live_title;
    }

    public int getRoom_type() {
        return room_type;
    }

    public void setRoom_type(int room_type) {
        this.room_type = room_type;
    }

    public int getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(int classify_id) {
        this.classify_id = classify_id;
    }

    public String getLive_type() {
        return live_type == null ? "" : live_type;
    }

    public void setLive_type(String live_type) {
        this.live_type = live_type;
    }

    @Keep
    public static class AnchorBean {
        private long user_id;
        private String nickname;
        private String avatar;

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}