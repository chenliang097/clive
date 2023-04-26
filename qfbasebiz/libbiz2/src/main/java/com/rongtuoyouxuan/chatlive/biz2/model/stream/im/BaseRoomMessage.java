package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRoomMessage implements MultiItemEntity {

    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_MESSAGE = 1;
    public static final int TYPE_FOLLOW = 2;
    public static final int TYPE_ENTER = 3;
    public static final int TYPE_SHARE = 4;
    public static final int TYPE_ADD_ADMIN = 5;
    public static final int TYPE_REVOKE_ADMIN = 6;
    public static final int TYPE_GIFT = 7;
    public static final int TYPE_SYSTEM_NOTICE = 8;
    public static final int TYPE_REQUEST_FOCUS = 9;
    public static final int TYPE_NO_TALKING = 10;
    public static final int TYPE_TIPS_SEND_GIFT = 11;
    public static final int TYPE_COMMON_SYSTEM_NOTICE = 12; // 可设置背景色
    public static final int TYPE_COMMON_TEMPLATEMSG = 13; // 模板消息
    public static final int TYPE_MESSAGE_GUARD = 14; // 开通/续费 守护
    public static final int TYPE_MESSAGE_INTIMACY = 15;//亲密等级升级
    public static final int TYPE_MESSAGE_GAME_ANIMATION = 16;//游戏动画
    public static final int TYPE_MESSAGE_GAME_TURNPLATE = 17;//转盘消息

    @SerializedName("from")
    public FromBean from = new FromBean();
    @SerializedName("to")
    public FromBean to = new FromBean();

    public static class FromBean {

        public String uid = "";
        public String nick = "";
        public String avatar = "";
        public int level = 0;
        public int vip = 0;
        public int svip = 0;
        public int rank = 0;
        public String blauth = "";
        public RoleBean role = new RoleBean();
        public String enter = "0";//座驾标识
        public List<String> medal = new ArrayList<>();
        public String gdtype = "";
        public int qmdlv = 0;


        public boolean isZhiZunGuard(){
            return "year".equalsIgnoreCase(gdtype);
        }

        public boolean isNormalGuard(){
            return "normal".equalsIgnoreCase(gdtype);
        }

        public static class RoleBean {

            /**
             * lv : 90
             * role : 超管
             * name : 超管
             * power : 1,2,3,4,5,6,7,8,9,10,11,12
             */

            public int lv = 0;
            public String role = "";
            public String name = "";
            public String power = "";
        }
    }

}
