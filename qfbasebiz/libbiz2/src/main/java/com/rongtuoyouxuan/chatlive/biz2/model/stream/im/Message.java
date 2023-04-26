package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.rongtuoyouxuan.chatlive.util.BaseItem;

@Entity(tableName = "message_table")
public  class Message implements BaseItem {

    @Ignore
    public static final String TYPE_EMPTYVIEW = "";
    @Ignore
    public static final int ITME_EMPTYVIEW = 0;

    @Ignore
    public static final String TYPE_GIFT = "gift";
    @Ignore
    public static final int ITME_LEFT_GIFT = 1;
    public static final int ITME_RIGHT_GIFT = 2;

    @Ignore
    public static final String TYPE_EMOJI = "emoji";
    @Ignore
    public static final int ITME_LEFT_EMOJI = 3;
    public static final int ITME_RIGHT_EMOJI = 4;

    @Ignore
    public static final String TYPE_IMAGE = "img";
    @Ignore
    public static final int ITME_LEFT_IMAGE = 5;
    public static final int ITME_RIGHT_IMAGE = 6;

    @Ignore
    public static final String TYPE_TEXT = "text";
    @Ignore
    public static final int ITME_LEFT_TEXT = 7;
    public static final int ITME_RIGHT_TEXT = 8;

    @Ignore
    public static final String TYPE_VIDEO = "video";
    @Ignore
    public static final int ITME_LEFT_VIDEO = 9;
    public static final int ITME_RIGHT_VIDEO = 10;

    @Ignore
    public static final String TYPE_SHAREHOST = "sharehost";
    @Ignore
    public static final int ITME_LEFT_HAREHOST = 11;
    public static final int ITME_RIGHT_HAREHOST = 12;

    @Ignore
    public static final String TYPE_VOICECHAT = "voicechat";
    @Ignore
    public static final int ITME_LEFT_VOICECHAT = 13;
    public static final int ITME_RIGHT_VOICECHAT = 14;
    @Ignore
    public static final String TYPE_PHONEVIDEOCHAT = "videochat";
    @Ignore
    public static final int ITME_LEFT_PHONEVIDEOCHAT = 15;
    public static final int ITME_RIGHT_PHONEVIDEOCHAT = 16;
    @Ignore
    public static final String TYPE_CAR = "sendcar";
    @Ignore
    public static final int ITME_LEFT_CAR = 17;
    public static final int ITME_RIGHT_CAR = 18;

    @Ignore
    public static final String TYPE_SHOWLOVE = "showlove";
    public static final int ITME_LEFT_SHOWLOVE = 19;
    public static final int ITME_RIGHT_SHOWLOVE = 20;

    public static final String TYPE_PARTY = "party";
    @Ignore
    public static final int ITME_LEFT_PARTY = 21;
    public static final int ITME_RIGHT_PARTY = 22;

    public static final String TYPE_CARD = "card";
    @Ignore
    public static final int ITME_LEFT_CARD = 23;
    public static final int ITME_RIGHT_CARD = 24;

    public static final String TYPE_CARD_REMOTE = "usercard";
    @Ignore
    public static final int ITME_LEFT_CARD_REMOTE = 25;
    public static final int ITME_RIGHT_CARD_REMOTE = 26;

    public static final String TYPE_VOICE_ROOM = "voiceroom";
    @Ignore
    public static final int ITME_LEFT_VOICE_ROOM = 27;
    public static final int ITME_RIGHT_VOICE_ROOM = 28;

    @Ignore
    public static final String TYPE_AUDIO = "voice";
    @Ignore
    public static final int ITME_LEFT_AUDIO = 29;
    public static final int ITME_RIGHT_AUDIO = 30;

    @Ignore
    public static final String TYPE_ADD_FRIEND = "applyfriend"; //添加好友
    public static final int ITME_ADD_FRIEND = 31; //添加好友

    @Ignore
    public static final String TYPE_BECOME_FRIEND = "bothfriend"; //成为好友
    public static final int ITME_BECOME_FRIEND = 32; //成为好友

    //默认消息状态
    @Ignore
    public static final int MSG_STATUS_MORMAL = 0;

    //消息正在发送中
    @Ignore
    public static final int MSG_STATUS_SENDING = 1;

    //消息正在重新发送中
    @Ignore
    public static final int MSG_STATUS_RESENDING = 2;


    //消息直接发送失败
    @Ignore
    public static final int MSG_STATUS_SENDFAIL = 4;

    //消息发送时，图片上传失败
    @Ignore
    public static final int MSG_STATUS_SEND_UPLOADIMG_FAIL = 5;


    //消息发送成功
    @Ignore
    public static final int MSG_STATUS_SENDSUCCESS = 6;

    //消息接收成功
    @Ignore
    public static final int MSG_STATUS_RECVSUCCESS = 7;


    @Ignore
    private long _id;

    //消息id
    @PrimaryKey @NonNull
    private String msgid = "";

    //扩展id，用户发送消息成功失败时找到对应的item
    @Ignore
    private int extraId = 0;

    //消息状态
    private int status = MSG_STATUS_MORMAL;

    //对方用户id
    private String uid = "";

    //发送方id
    private String fid = "";

    //接收方id
    private String tid = "";

    //消息类型
    private String type = "";

    //文本消息
    private String text = "";

    //emoji消息
    private String emoji = "";

    //图片消息(图片url)
    private String imgurl = "";

    //图片本地路径
    private String imgPath = "";

    //视频本地路径
    @ColumnInfo(defaultValue = "") @NonNull
    private String videoPath = "";

    //视频url
    @ColumnInfo(defaultValue = "") @NonNull
    private String videoUrl = "";

    //礼物ID
    private String giftid = "";

    //礼物数量
    private int giftnum = 0;

    //翻译后的消息
    private String transtext = "";

    //消息时间
    private long addtime = 0;

    //是否已读
    private boolean isnew = false;

    //扩展参数
    @ColumnInfo(defaultValue = "") @NonNull
    private String text1 = "";
    @ColumnInfo(defaultValue = "") @NonNull
    private String text2 = "";
    @ColumnInfo(defaultValue = "") @NonNull
    private String text3 = "";
    @ColumnInfo(defaultValue = "") @NonNull
    private String text4 = "";
    @ColumnInfo(defaultValue = "") @NonNull
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


    public Message(String msgid, String uid, String text, String type) {
        this.msgid = msgid;
        this.uid = uid;
        this.text = text;
        this.type = type;
    }

    public Message() {}

    @Override
    public int getItemType() {
        if (type != null) {
            switch (type) {
                case TYPE_GIFT:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_GIFT;
                        } else {
                            return ITME_RIGHT_GIFT;
                        }
                    }
                    break;
                case TYPE_EMOJI:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_EMOJI;
                        } else {
                            return ITME_RIGHT_EMOJI;
                        }
                    }
                    break;
                case TYPE_IMAGE:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_IMAGE;
                        } else {
                            return ITME_RIGHT_IMAGE;
                        }
                    }
                    break;
                case TYPE_TEXT:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_TEXT;
                        } else {
                            return ITME_RIGHT_TEXT;
                        }
                    }
                    break;
                case TYPE_VIDEO:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_VIDEO;
                        } else {
                            return ITME_RIGHT_VIDEO;
                        }
                    }
                    break;
                case TYPE_SHAREHOST:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_HAREHOST;
                        } else {
                            return ITME_RIGHT_HAREHOST;
                        }
                    }
                    break;
                case TYPE_VOICECHAT:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_VOICECHAT;
                        } else {
                            return ITME_RIGHT_VOICECHAT;
                        }
                    }
                    break;
                case TYPE_PHONEVIDEOCHAT:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_PHONEVIDEOCHAT;
                        } else {
                            return ITME_RIGHT_PHONEVIDEOCHAT;
                        }
                    }
                    break;
                case TYPE_CAR:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_CAR;
                        } else {
                            return ITME_RIGHT_CAR;
                        }
                    }
                    break;
                case TYPE_SHOWLOVE:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_SHOWLOVE;
                        } else {
                            return ITME_RIGHT_SHOWLOVE;
                        }
                    }
                    break;
                case TYPE_PARTY:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_PARTY;
                        } else {
                            return ITME_RIGHT_PARTY;
                        }
                    }
                    break;
//                case TYPE_CARD:
//                    if (uid != null) {
//                        if (uid.equals(fid)) {
//                            return ITME_LEFT_CARD;
//                        } else {
//                            return ITME_RIGHT_CARD;
//                        }
//                    }
//                    break;
                case TYPE_CARD_REMOTE:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_CARD_REMOTE;
                        } else {
                            return ITME_RIGHT_CARD_REMOTE;
                        }
                    }
                    break;
                case TYPE_VOICE_ROOM:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_VOICE_ROOM;
                        } else {
                            return ITME_RIGHT_VOICE_ROOM;
                        }
                    }
                    break;
                case TYPE_AUDIO:
                    if (uid != null) {
                        if (uid.equals(fid)) {
                            return ITME_LEFT_AUDIO;
                        } else {
                            return ITME_RIGHT_AUDIO;
                        }
                    }
                    break;
                case TYPE_ADD_FRIEND:
                    if (uid != null) {
                        return ITME_ADD_FRIEND;
                    }
                    break;
                case TYPE_BECOME_FRIEND:
                    if (uid != null) {
                        return ITME_BECOME_FRIEND;
                    }
                    break;
            }
        }
        return ITME_EMPTYVIEW;
    }

    @NonNull
    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(@NonNull String msgid) {
        this.msgid = msgid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getGiftid() {
        return giftid;
    }

    public void setGiftid(String giftid) {
        this.giftid = giftid;
    }

    public int getGiftnum() {
        return giftnum;
    }

    public void setGiftnum(int giftnum) {
        this.giftnum = giftnum;
    }

    public String getTranstext() {
        return transtext;
    }

    public void setTranstext(String transtext) {
        this.transtext = transtext;
    }

    public long getAddtime() {
        return addtime;
    }

    public void setAddtime(long addtime) {
        this.addtime = addtime;
    }

    public boolean isIsnew() {
        return isnew;
    }

    public void setIsnew(boolean isnew) {
        this.isnew = isnew;
    }

    public int getExtraId() {
        return extraId;
    }

    public void setExtraId(int extraId) {
        this.extraId = extraId;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public boolean isSendMsg() {
        return uid.equalsIgnoreCase(tid);
    }

    public boolean isRecvMsg() {
        return uid.equalsIgnoreCase(fid);
    }

    public boolean isSendFail() {
        return status == MSG_STATUS_SEND_UPLOADIMG_FAIL || status == MSG_STATUS_SENDFAIL;
    }

    @Override
    public boolean isSameItem(BaseItem item) {
        Message other = (Message)item;
        if (getStatus() == MSG_STATUS_MORMAL) {
            if (!TextUtils.isEmpty(getMsgid()) && getMsgid().equals(other.getMsgid())) {
                return true;
            }
        } else {
            if (getExtraId() == other.getExtraId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSameContent(BaseItem item) {
        Message other = (Message)item;
        if (getStatus() == other.getStatus()
        && getUid().equals(other.getUid())
        && getFid().equals(other.getFid())
        && getTid().equals(other.getTid())
        && getType().equals(other.getType())
        && getText().equals(other.getText())
        && getEmoji().equals(other.getEmoji())
        && getImgurl().equals(other.getImgurl())
        && getImgPath().equals(other.getImgPath())
        && getVideoPath().equals(other.getVideoPath())
        && getVideoUrl().equals(other.getVideoUrl())
        && getGiftid().equals(other.getGiftid())
        && getGiftnum() == other.getGiftnum()
        && getTranstext().equals(other.getTranstext())
        ) {
            return true;
        }
        return false;
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

    //主播id
    public String getHostId() {
        return getText1();
    }

    public void setHostId(String hostId) {
        setText1(hostId);
    }

    //主播昵称
    public String getHostNick() {
        return getText2();
    }

    public void setHostNick(String hostNick) {
        setText2(hostNick);
    }

    public String getHostImage() {
        return getImgurl();
    }

    public void setHostImage(String hostImage) {
        setImgurl(hostImage);
    }

    public String getShareTitle() {
        return getText3();
    }

    public void setShareTitle(String shareTitle) {
        setText3(shareTitle);
    }

    public String getShareContent() {
        return getText();
    }

    public void setShareContent(String shareContent) {
        setText(shareContent);
    }

    public int getVoicechatStatus() {
        return int1;
    }

    public void setVoicechatStatus(int voicechatStatus) {
        this.int1 = voicechatStatus;
    }

    public int getVoicechatFailReason() {
        return int2;
    }

    public void setVoicechatFailReason(int voicechatFailReason) {
        this.int2 = voicechatFailReason;
    }

    public int getVoicechatDuration() {
        return giftnum;
    }

    public void setVoicechatDuration(int voicechatDuration) {
        this.giftnum = voicechatDuration;
    }
    //在voicechat/videochat类型中 使用giftid标记price
    public String getVoicechatPrice() {
        return giftid;
    }

    public void setVoicechatPrice(String voicechatPrice) {
        this.giftid = voicechatPrice;
    }
    //在sendcar类型中用text1标识cid
    public String getCid() {
        return text1;
    }

    public void setCid(String text1) {
        this.text1 = text1;
    }

    //在打招呼中用text3标识isHello
    public String getIsHello() {
        return text3;
    }

    public void setIsHello(String text3) {
        this.text3 = text3;
    }
    //在打招呼中用text3标识错误码
    public String getErrorCode() {
        return text2;
    }

    public void setErrorCode(String text2) {
        this.text2 = text2;
    }
    //在打招呼中用text1标识ratecheck
    public String getRatecheck() {
        return text1;
    }
    public void setRatecheck(String text1) {
        this.text1 = text1;
    }

    //在party类型中用text1标识channelid
    public String getChannelid() {
        return text1;
    }

    public void setChannelid(String text1) {
        this.text1 = text1;
    }

    //在party类型中用int1标识rid
    public String getRid() {
        return text;
    }

    public void setRid(String text) {
        this.text = text;
    }

    //在party类型中用text2标识nick
    public String getNick() {
        return text2;
    }

    public void setNick(String text2) {
        this.text2 = text2;
    }

    //在party类型中用text3标识avatar
    public String getAvatar() {
        return text3;
    }

    public void setAvatar(String text3) {
        this.text3 = text3;
    }

    //在礼物中类型中用text3标识avatar
    public int getIntimacy() {
        return int2;
    }
    public void setIntimacy(int int2) {
        this.int2 = int2;
    }

    //在卡片中类型中用_id标识年龄
    public long getAge() {
        return _id;
    }
    public void setAge(long _id) {
        this._id = _id;
    }
    //在卡片中类型中用text1标识性别
    public String getGender() {
        return text1;
    }
    public void setGender(String text1) {
        this.text1 = text1;
    }
    //在卡片中类型中用imgPath标识国家
    public String getCountry() {
        return imgPath;
    }
    public void setCountry(String imgPath) {
        this.imgPath = imgPath;
    }
    //在卡片中类型中用text3标识签名
    public String getSign() {
        return text3;
    }
    public void setSign(String text3) {
        this.text3 = text3;
    }

    //在卡片中类型中用text标识头像
    public String getCardAvatar() {
        return text;
    }
    public void setCardAvatar(String text) {
        this.text = text;
    }

    //在卡片中类型中用giftnum标识图片类型
    public int getPicType() {
        return giftnum;
    }
    public void setPicType(int giftnum) {
        this.giftnum = giftnum;
    }

    //在服务端下发卡片中类型中用int1标识年龄
    public int getAgeCardRemote() {
        return int1;
    }
    public void setAgeCardRemote(int int1) {
        this.int1 = int1;
    }
    //在卡片中类型中用int2标识性别
    public int getGenderCardRemote() {
        return int2;
    }
    public void setGenderCardRemote(int int2) {
        this.int2 = int2;
    }
    //在卡片中类型中用text1标识国家
    public String getCountryCardRemote() {
        return text;
    }
    public void setCountryCardRemote(String text) {
        this.text = text;
    }
    //在卡片中类型中用text1标识签名
    public String getNickCardRemote() {
        return text1;
    }
    public void setNickCardRemote(String text1) {
        this.text1 = text1;
    }
    //在卡片中类型中用text标识头像
    public String getAvatarCardRemote() {
        return text2;
    }
    public void setAvatarCardRemote(String text2) {
        this.text2 = text2;
    }
    //在卡片中类型中用uid标识头像
    public String getUidCardRemote() {
        return text3;
    }
    public void setUidCardRemote(String text3) {
        this.text3 = text3;
    }

    //语音房邀请消息 使用text标识hostid
    public String getVoiceHostId() {
        return text;
    }
    public void setVoiceHostId(String text) {
        this.text = text;
    }

    public String getAudioPath() {
        return text;
    }
    public void setAudioPath(String text) {
        this.text = text;
    }
    public String getAudioUrl() {
        return text1;
    }
    public void setAudioUrl(String text1) {
        this.text1 = text1;
    }
    public int getDuration() {
        return int1;
    }
    public void setDuration(int int1) {
        this.int1 = int1;
    }
}
