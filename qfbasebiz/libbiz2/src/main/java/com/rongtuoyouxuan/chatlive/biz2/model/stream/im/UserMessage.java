package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.biz2.model.config.User;
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.UserInfo;
import com.rongtuoyouxuan.chatlive.util.NumUtils;
import com.google.gson.annotations.SerializedName;

public class UserMessage {
    @SerializedName("from")
    public UserInfo userInfo = new UserInfo();
    @SerializedName("to")
    public UserInfo userInfoTo = new UserInfo();
    @SerializedName("msgRet")
    public _Message msg = new _Message();

    public int status = Message.MSG_STATUS_MORMAL;
    public Object extra = new Object();

    public String errCode = "";
    public String errMsg = "";
    public String isHello = "";

    public Message sendMsg;

    public int friendStatus = User.FRIEND_STATUS_NOT_INIT;
    public int addFriendEvent = User.NO_ADD_FRIEND;

    public UserMessage() {
    }

    public UserMessage(UserInfo info, _Message msg) {
        userInfo = info;
        this.msg = msg;
    }

    public UserMessage(UserInfo info, _Message msg, int status, Object extra) {
        userInfo = info;
        this.msg = msg;
        this.status = status;
        this.extra = extra;
    }

    public UserMessage(UserInfo info, Message sendMsg, int status, Object extra) {
        userInfo = info;
        this.sendMsg = sendMsg;
        this.status = status;
        this.extra = extra;
    }

    public UserMessage(UserInfo info, _Message msg, int status, Object extra, String errCode, String errMsg) {
        userInfo = info;
        this.msg = msg;
        this.status = status;
        this.extra = extra;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

//    public Long getUid() {
//        return userInfo.user_id;
//    }

//    public String getName() {
//        return userInfo.nick;
//    }

    public String getLastContent() {
        if (msg == null) {
            return "";
        }
        if (msg.msg == null) {
            return "";
        }
        switch (msg.msg.type) {
            case "text":
            case Message.TYPE_ADD_FRIEND:
            case Message.TYPE_BECOME_FRIEND:
                return msg.msg.text;
            case "emoji":
                return msg.msg.emoji;
            case "img":
                return "[img]";
            case "video":
                return "[video]";
            case "sharehost":
                return "[share]";
            case Message.TYPE_VOICECHAT:
                return "[voicechat]";
            case Message.TYPE_PHONEVIDEOCHAT:
                return "[videochat]";
            case Message.TYPE_CAR:
                return "[Give car for free]";
            case Message.TYPE_PARTY:
                return "[Party invitation]";
            case "gift":
                return msg.msg.text;
            case Message.TYPE_SHOWLOVE:
                return "[Valentine's day confessions]";
            case Message.TYPE_CARD_REMOTE:
                return "[Card]";
            case Message.TYPE_VOICE_ROOM:
                return "[Voice Room Invite]";
            case Message.TYPE_AUDIO:
                return "[audio]";
                default:
                    break;
        }
        return "";
    }

    public User getUser() {
        User user = new User();
//        user.setUid(userInfo.user_id);
//        user.setName(userInfo.nick);
//        user.setGender(userInfo.gender);
//        user.setIcon(userInfo.avatar);
        user.setOnline("0");
        user.setLastContent(getLastContent());
        if (msg != null) {
            user.setUpdateTime(msg.addtime);
            user.setLastMsgId(NumUtils.parseInt(msg.id));
        }
//        user.setML(userInfo.ml);
//        user.setCid(userInfo.cid);
        user.setUnReadMsgCount(0);
        user.setFriendStatus(friendStatus);
        user.setAddFriendEvent(addFriendEvent);
        return user;
    }

    public Message getSendMsg() {
        return sendMsg;
    }

    public Message getMessage() {
        Message m = new Message();
        m.setMsgid(msg.id);
//        m.setUid(userInfo.user_id);
        m.setFid(msg.fid);
        m.setTid(msg.tid);
        m.setType(msg.msg.type);
        if (msg.msg.type.equals(Message.TYPE_SHAREHOST)) {
            m.setHostId(msg.msg.sharehost.hostid);
            m.setHostNick(msg.msg.sharehost.nick);
            m.setHostImage(msg.msg.sharehost.image.simg);
            m.setShareTitle(msg.msg.sharehost.title);
            m.setShareContent(msg.msg.sharehost.content);
        } else if (msg.msg.type.equals(Message.TYPE_VOICECHAT)) {
            m.setVoicechatDuration(msg.msg.voicechat.time);
            m.setVoicechatFailReason(msg.msg.voicechat.failmsg);
            m.setVoicechatStatus(msg.msg.voicechat.cstat);
            m.setVoicechatPrice(msg.msg.voicechat.price);
        } else if (msg.msg.type.equals(Message.TYPE_PHONEVIDEOCHAT)) {
            m.setVoicechatDuration(msg.msg.videochat.time);
            m.setVoicechatFailReason(msg.msg.videochat.failmsg);
            m.setVoicechatStatus(msg.msg.videochat.cstat);
            m.setVoicechatPrice(msg.msg.videochat.price);
        } else if (msg.msg.type.equals(Message.TYPE_SHOWLOVE)) {
            //不需要动态数据显示在ui上
        } else if(msg.msg.type.equals(Message.TYPE_PARTY)){
            m.setChannelid(msg.msg.party.channelid);
            m.setRid(msg.msg.party.rid);
            m.setNick(msg.msg.party.nick);
            m.setAvatar(msg.msg.party.avatar);
        }else if(msg.msg.type.equals(Message.TYPE_CARD_REMOTE)){
            m.setNickCardRemote(msg.msg.usercard.nick);
            m.setAvatarCardRemote(msg.msg.usercard.avatar.simg);
            m.setGenderCardRemote(msg.msg.usercard.gender);
            m.setAgeCardRemote(msg.msg.usercard.age);
            m.setCountryCardRemote(msg.msg.usercard.ctcode);
            m.setUidCardRemote(msg.msg.usercard.uid);
        }else if(msg.msg.type.equals(Message.TYPE_VOICE_ROOM)){
            m.setChannelid(msg.msg.voiceRoom.channelid);
            m.setHostId(msg.msg.voiceRoom.hostid);
            m.setAvatar(msg.msg.voiceRoom.avatar);
            m.setNick(msg.msg.voiceRoom.nick);
        }else if(Message.TYPE_AUDIO.equals(msg.msg.type)){
            m.setAudioUrl(msg.msg.voiceData.url);
            m.setDuration(msg.msg.voiceData.duration);
        }else {
            m.setText(msg.msg.text);
            m.setEmoji(msg.msg.emoji);
            m.setImgurl(msg.msg.img);
            m.setGiftid(msg.msg.gift.giftid);
            m.setGiftnum(msg.msg.gift.giftnum);
            m.setVideoUrl(msg.msg.videoData.url);
            m.setCid(msg.msg.cid);
            m.setIsHello(msg.msg.ishello);
            m.setRatecheck(msg.msg.ratecheck);
            m.setIntimacy(msg.msg.gift.intimacy);
        }
        m.setTranstext(msg.msg.trans);
        m.setAddtime(msg.addtime);
        m.setIsnew(true);
        m.setStatus(status);
        return m;
    }

    public Message updateMsg(Message m) {
        m.setMsgid(msg.id);
//        m.setUid(userInfo.uid);
        m.setFid(msg.fid);
        m.setTid(msg.tid);
        m.setType(msg.msg.type);
        if (msg.msg.type.equals(Message.TYPE_SHAREHOST)) {
            m.setHostId(msg.msg.sharehost.hostid);
            m.setHostNick(msg.msg.sharehost.nick);
            m.setHostImage(msg.msg.sharehost.image.simg);
            m.setShareTitle(msg.msg.sharehost.title);
            m.setShareContent(msg.msg.sharehost.content);
        } else if (msg.msg.type.equals(Message.TYPE_VOICECHAT)) {
            m.setVoicechatDuration(msg.msg.voicechat.time);
            m.setVoicechatFailReason(msg.msg.voicechat.failmsg);
            m.setVoicechatStatus(msg.msg.voicechat.cstat);
            m.setVoicechatPrice(msg.msg.voicechat.price);
        } else if (msg.msg.type.equals(Message.TYPE_PHONEVIDEOCHAT)) {
            m.setVoicechatDuration(msg.msg.videochat.time);
            m.setVoicechatFailReason(msg.msg.videochat.failmsg);
            m.setVoicechatStatus(msg.msg.videochat.cstat);
            m.setVoicechatPrice(msg.msg.videochat.price);
        } else if(msg.msg.type.equals(Message.TYPE_PARTY)){
            m.setChannelid(msg.msg.party.channelid);
            m.setRid(msg.msg.party.rid);
            m.setNick(msg.msg.party.nick);
            m.setAvatar(msg.msg.party.avatar);
        }else if(msg.msg.type.equals(Message.TYPE_CARD_REMOTE)){
            m.setNickCardRemote(msg.msg.usercard.nick);
            m.setAvatarCardRemote(msg.msg.usercard.avatar.simg);
            m.setGenderCardRemote(msg.msg.usercard.gender);
            m.setAgeCardRemote(msg.msg.usercard.age);
            m.setCountryCardRemote(msg.msg.usercard.ctcode);
            m.setUidCardRemote(msg.msg.usercard.uid);
        }else if(msg.msg.type.equals(Message.TYPE_VOICE_ROOM)){
            m.setChannelid(msg.msg.voiceRoom.channelid);
            m.setHostId(msg.msg.voiceRoom.hostid);
            m.setAvatar(msg.msg.voiceRoom.avatar);
            m.setNick(msg.msg.voiceRoom.nick);
        }else if(Message.TYPE_AUDIO.equals(msg.msg.type)){
            m.setAudioUrl(msg.msg.voiceData.url);
            m.setDuration(msg.msg.voiceData.duration);
        }else {
            if (!m.getType().equals(Message.TYPE_GIFT)) {
                m.setText(msg.msg.text);
                m.setImgurl(msg.msg.img);
            }
            m.setEmoji(msg.msg.emoji);
            m.setGiftid(msg.msg.gift.giftid);
            m.setGiftnum(msg.msg.gift.giftnum);
            m.setVideoUrl(msg.msg.videoData.url);
            m.setCid(msg.msg.cid);
            m.setIsHello(msg.msg.ishello);
            m.setRatecheck(msg.msg.ratecheck);
            m.setIntimacy(msg.msg.gift.intimacy);
        }
        m.setTranstext(msg.msg.trans);
        m.setAddtime(msg.addtime);
        m.setIsnew(true);
        m.setStatus(status);
        return m;
    }
}