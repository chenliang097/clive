package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.im;

import com.google.gson.annotations.SerializedName;

public class _Message {
    /**
     * id : 97
     * fid : 13
     * tid : 19
     * msg : {"type":"gift","gift":{"giftid":1,"giftnum":2},"trans":""}
     * addtime : 1559360101
     */

    @SerializedName("id")
    public String id = "";
    @SerializedName("fid")
    public String fid = "";
    @SerializedName("tid")
    public String tid = "";
    @SerializedName("msg")
    public Msg msg = new Msg();
    @SerializedName("addtime")
    public long addtime = 0;

    public Message getMessage(String uid) {
        Message m = new Message();
        m.setMsgid(id);
        m.setUid(uid);
        m.setFid(fid);
        m.setTid(tid);
        m.setType(msg.type);
        m.setText(msg.text);
        m.setEmoji(msg.emoji);
        m.setImgurl(msg.img);
        m.setTranstext(msg.trans);
        m.setAddtime(addtime);
        m.setIsnew(true);
        return m;
    }

    public static class Msg {
        /**
         * type : gift
         * gift : {"giftid":1,"giftnum":2}
         * trans :
         */

        @SerializedName("type")
        public String type = "";
        @SerializedName("text")
        public String text = "";
        @SerializedName("emoji")
        public String emoji = "";
        @SerializedName("img")
        public String img = "";
        @SerializedName("gift")
        public Gift gift = new Gift();
        @SerializedName("trans")
        public String trans = "";
        @SerializedName("cid")
        public String cid = "";
        @SerializedName("ishello")
        public String ishello = "0";
        @SerializedName("ratecheck")
        public String ratecheck = "0";
        @SerializedName("videoData")
        public VideoDataBean videoData = new VideoDataBean();

        @SerializedName("sharehost")
        public Sharehost sharehost = new Sharehost();

        @SerializedName("voicechat")
        public Voicechat voicechat = new Voicechat();

        @SerializedName("videochat")
        public Voicechat videochat = new Voicechat();

        @SerializedName("showlove")
        public Showlove showlove = new Showlove();

        @SerializedName("party")
        public Party party = new Party();

        @SerializedName("usercard")
        public UserCard usercard = new UserCard();

        @SerializedName("voiceroom")
        public VoiceRoom voiceRoom = new VoiceRoom();

        @SerializedName("voiceData")
        public VoiceData voiceData = new VoiceData();

        public static class Gift {
            /**
             * giftid : 1
             * giftnum : 2
             */

            @SerializedName("giftid")
            public String giftid = "";
            @SerializedName("giftnum")
            public int giftnum = 0;
            @SerializedName("intimacy")
            public int intimacy = 0;
        }

        public static class VideoDataBean {

            public String url = "";
        }

        public static class Voicechat {
            public int cstat = 0;
            public int failmsg = 0;
            public int time = 0;
            public String price = "0";
        }

        public static class Sharehost {
            @SerializedName("hostid")
            public String hostid = "";
            @SerializedName("nick")
            public String nick = "";
            @SerializedName("image")
            public Image image = new Image();
            @SerializedName("title")
            public String title = "";
            @SerializedName("content")
            public String content = "";
            @SerializedName("hasmessage")
            public boolean hasmessage = false;

            public static class Image {
                @SerializedName("simg")
                public String simg = "";
            }
        }

        public static class Showlove {
        }

        public static class Party {
            public String channelid = "";
            public String rid = "0";
            public String nick = "";
            public String avatar = "";
        }

        public static class UserCard {
            public String uid = "";
            public String nick = "";
            public UserCardAvatar avatar = new UserCardAvatar();
            public int gender = 0;
            public int age = 0;
            public String ctcode = "";
        }

        public static class UserCardAvatar {
            public String simg = "";
        }

        public static class VoiceRoom {
            public String channelid = "";
            public String hostid = "";
            public String nick= "";
            public String avatar = "";
        }

        public static class VoiceData {
            public String url = "";
            public int duration = 1;
        }
    }
}