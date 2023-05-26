package com.rongtuoyouxuan.chatlive.crtbiz2;

//所有网络请求必须定义reqid,用来标识该网络请求，每个biz一组，顺序递增，不能重复,长度固定为4位
public final class ReqId {
    public static final String getClientConf = "1000";
    public static final String CLIENT_CONFIG = "1001";
    public static final String GET_COUNTRY_CODE = "1002";
    public static final String LASTVERSION = "1003";
    public static final String PUSH_DEVICE = "1004";
    public static final String GIFT_LIST = "2001";
    public static final String SEND_GIFT = "2002";
    public static final String GIFT_PACKAGE_LIST = "2003";
    public static final String SEND_GIFT1 = "2004";
    public static final String GET_BALANCE = "2005";
    public static final String USER_CHECK_FOLLOW = "2006";
    public static final String USER_ADD_FOLLOW = "2007";
    public static final String USER_DEL_FOLLOW = "2008";
    public static final String USER_CARD_INFO = "2009";
    public static final String LIVE_ADD_MANAGER = "2010";
    public static final String LIVE_DEL_MANAGER = "2011";
    public static final String LIVE_CLOSURE_LIVE_ROOM = "2012";
    public static final String TRANSLATE = "2013";
    public static final String STREAM_CONMEN = "3005";
    public static final String GIF_LIST = "4001";
    public static final String LINK_MIC_APPLY_LIST = "4002";
    public static final String LINK_MIC_INVITE_LIST = "4003";
    public static final String LINK_MIC_MANAGER_LIST = "4004";
    public static final String LINK_MIC_ANCHOR_INVITE = "4005";
    public static final String LINK_MIC_ANCHOR_INVITE_TIMEOUT_CANCEL = "4006";
    public static final String LINK_MIC_ANCHOR_REFUSE_INVITE = "4007";
    public static final String LINK_MIC_AUDIENCE_REFUSE_INVITE = "4008";
    public static final String LINK_MIC_AUDIENCE_AGREE_INVITE = "4009";

}
