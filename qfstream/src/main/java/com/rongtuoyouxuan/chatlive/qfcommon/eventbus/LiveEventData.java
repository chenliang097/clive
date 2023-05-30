package com.rongtuoyouxuan.chatlive.qfcommon.eventbus;


import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.EnterRoomBean;

public class LiveEventData {
    //登录成功
    public static LiveEventKey<String> LIVE_ALLOW_RANGE = new LiveEventKey<>();
    public static LiveEventKey<EnterRoomBean> LIVE_NEXT_PAGER = new LiveEventKey<>();
    public static LiveEventKey<Integer> LIVE_SHOW_GIFT_DIALOG = new LiveEventKey<>();
}
