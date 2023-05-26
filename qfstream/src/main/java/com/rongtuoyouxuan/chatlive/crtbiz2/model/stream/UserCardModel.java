package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class UserCardModel extends BaseModel {


    private DataBean data = new DataBean();

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private UinfoBean uinfo = new UinfoBean();
        private RoleBean role = new RoleBean();
        private boolean forbid = false;
        private List<GiftranklistBean> giftranklist = new ArrayList<>();

        public UinfoBean getUinfo() {
            return uinfo;
        }

        public void setUinfo(UinfoBean uinfo) {
            this.uinfo = uinfo;
        }

        public RoleBean getRole() {
            return role;
        }

        public void setRole(RoleBean role) {
            this.role = role;
        }

        public boolean isForbid() {
            return forbid;
        }

        public void setForbid(boolean forbid) {
            this.forbid = forbid;
        }

        public List<GiftranklistBean> getGiftranklist() {
            return giftranklist;
        }

        public void setGiftranklist(List<GiftranklistBean> giftranklist) {
            this.giftranklist = giftranklist;
        }

        public static class UinfoBean {
            public String blauth = "";
            public String oldnick = "";//原昵称

            private String uid = "";
            private String avatar = "";
            private String nick = "";
            private int vip = 0;
            private int svip = 0;
            private int gender = 0;
            private int age = 0;
            private int level = 0;
            private int roomlevel = -1;
            private int online = 0;
            private String fans = "";
            private String follow = "";
            private String send = "";
            private String receive = "";
            private String country = "";
            private String language = "";
            private String sign = "";
            private String video = "";
            private int regtime = 0;
            private String gdtype = "";

            public String getGdtype() {
                return gdtype;
            }

            public void setGdtype(String gdtype) {
                this.gdtype = gdtype;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getBlauth() {
                return blauth;
            }

            public void setBlauth(String blauth) {
                this.blauth = blauth;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNick() {
                return nick;
            }

            public void setNick(String nick) {
                this.nick = nick;
            }

            public int getVip() {
                return vip;
            }

            public void setVip(int vip) {
                this.vip = vip;
            }

            public int getSvip() {
                return svip;
            }

            public void setSvip(int svip) {
                this.svip = svip;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRoomlevel() {
                return roomlevel;
            }

            public void setRoomlevel(int roomlevel) {
                this.roomlevel = roomlevel;
            }

            public int getOnline() {
                return online;
            }

            public void setOnline(int online) {
                this.online = online;
            }

            public String getFans() {
                return fans;
            }

            public void setFans(String fans) {
                this.fans = fans;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getSend() {
                return send;
            }

            public void setSend(String send) {
                this.send = send;
            }

            public String getReceive() {
                return receive;
            }

            public void setReceive(String receive) {
                this.receive = receive;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public int getRegtime() {
                return regtime;
            }

            public void setRegtime(int regtime) {
                this.regtime = regtime;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }
        }

        public static class RoleBean {
            /**
             * lv : 10
             * role : 用户
             * name : 用户
             * power : 1,2
             */

            private String lv = "10";
            private String role = "";
            private String name = "";
            private String power = "";

            public String getLv() {
                return lv;
            }

            public void setLv(String lv) {
                this.lv = lv;
            }

            public String getRole() {
                return role;
            }

            public void setRole(String role) {
                this.role = role;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPower() {
                return power;
            }

            public void setPower(String power) {
                this.power = power;
            }
        }

        public static class GiftranklistBean {

            private int uid ;
            private int score;
            private int rank;
            private UinfoBeanX uinfo;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public UinfoBeanX getUinfo() {
                return uinfo;
            }

            public void setUinfo(UinfoBeanX uinfo) {
                this.uinfo = uinfo;
            }

            public static class UinfoBeanX {

                private String uid = "";
                private String nick = "";
                private String avatar = "";
                private int gender = 0;
                private int age = 0;
                private String sign = "";
                private int online = 0;
                private int vip = 0;
                private int svip = 0;
                private int level = 0;
                private int roomlevel = 0;
                private String country = "";
                private String language = "";
                private int playstatus = 0;
                private int roomtype = 0;

                public String getUid() {
                    return uid;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public String getNick() {
                    return nick;
                }

                public void setNick(String nick) {
                    this.nick = nick;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public int getGender() {
                    return gender;
                }

                public void setGender(int gender) {
                    this.gender = gender;
                }

                public int getAge() {
                    return age;
                }

                public void setAge(int age) {
                    this.age = age;
                }

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }

                public int getOnline() {
                    return online;
                }

                public void setOnline(int online) {
                    this.online = online;
                }

                public int getVip() {
                    return vip;
                }

                public void setVip(int vip) {
                    this.vip = vip;
                }

                public int getSvip() {
                    return svip;
                }

                public void setSvip(int svip) {
                    this.svip = svip;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public int getRoomlevel() {
                    return roomlevel;
                }

                public void setRoomlevel(int roomlevel) {
                    this.roomlevel = roomlevel;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getLanguage() {
                    return language;
                }

                public void setLanguage(String language) {
                    this.language = language;
                }

                public int getPlaystatus() {
                    return playstatus;
                }

                public void setPlaystatus(int playstatus) {
                    this.playstatus = playstatus;
                }

                public int getRoomtype() {
                    return roomtype;
                }

                public void setRoomtype(int roomtype) {
                    this.roomtype = roomtype;
                }
            }
        }
    }
}
