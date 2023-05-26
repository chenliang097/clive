package com.rongtuoyouxuan.chatlive.crtbiz2.constanst;

/*
 *Create by {Mr秦} on 2022/7/23
 */
public class Config {
    //1: 登陆验证码 2: 找回密码 3: 重置密码 4: 绑定手机号 7: 注销账号 8：账户激活验证码
    public static final int verify_login = 1;
    public static final int verify_forget = 2;
    public static final int verify_reset = 1;


    //1：手机验证码登陆 2：手机密码登陆 3：游客登录
    public static final int login_verify = 1;
    public static final int login_password = 2;
    public static final int login_visitor = 3;

    public static final int password_setting = 1;

    public static final String PASSWORD_KEY = "qwesidjwqie12345";
    public static final String IV_KEY = "qwesidjwqie12345";


    //plat_apple、plat_google、plat_facebook、plat_twitter、plat_line, plat_mobile
    public static final String PLAT_FACEBOOK = "plat_facebook";
    public static final String PLAT_LINE = "plat_line";
    public static final String PLAT_GOOGLE = "plat_google";
    public static final String PLAT_TWITTER = "plat_twitter";
    public static final String PLAT_MOBILE = "plat_mobile";


    //性别 0：未知 1：男 2：女
    public static final int sex_other = 0;
    public static final int sex_man = 1;
    public static final int sex_woman = 2;


    //Bo币余额不足 13331000
    //钻石余额不足 13332000
    //金币余额不足 13333000
    //金豆余额不足 13334000
    public static final String ALERT_DIALOG_BO_BI = "13331000";


    //游客标识
    public static final String GUEST = "guest";

    //房间chatroom, 粉丝群group   user
    public static final String USER = "user";

    //开发者调试
    public static final String DEVELOP = "develop";
    public static final String NON_EXPIRE = "non_expire";
    public static final String EXPIRE = "expire";


    //状态: 1未启用 2已启用 3已过期(1展示使用按钮 2/3展示续费按钮)
    public static final long NO_USE = 1L;

    //金豆 1  金币2   Bo币 0
    public static final int BOB = 0;
    public static final int JID = 1;
    public static final int JIB = 2;

    //type :
    //h5  网页
    //toast  暂未开放
    //shop 商店
    //pack 背包
    //fans_group 粉丝团
    //user_feedback 用户反馈
    //h5_share  网页 邀请用户
    public static final String H5 = "h5";
    public static final String H5_SHARE = "h5_share";
    public static final String GROUP_FANS = "group_fans";
    public static final String USER_FEEDBACK = "user_feedback";
    public static final String PACK = "pack";
    public static final String SHOP = "shop";
    public static final String RANK = "contribute_rank";
    public static final String TOAST = "toast";
    public static final String TASK_CENTER = "task_center";

    //座驾 1  头像框2
    public static final int CAR = 1;
    public static final int AVATAR = 2;


    //与设备关联tag
    public static final String TAG = "tag";



    //token key
    public static final String TOKEN_KEY = "im_token_key";


    //签到type
    //1 金币 2 经验 2 金豆 4 座驾 5 头像框 6 礼物 7 Bo币
    public static final int SIGN_CAR = 4;
    public static final int SIGN_AVATAR = 5;

    //签到跳转类型
    //Type: "hot_live",    // 跳转hot直播页
    //Type:   "recharge_shop", // 跳转充值商城
    //Type:   "edit_user",  // 跳转编辑用户信息页
    public static final String HOT_LIVE = "hot_live";
    public static final String RECHARGE_SHOP = "recharge_shop";
    public static final String EDIT_USER = "edit_user";


}