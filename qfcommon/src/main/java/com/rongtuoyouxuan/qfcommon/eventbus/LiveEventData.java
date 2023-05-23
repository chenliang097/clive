package com.rongtuoyouxuan.qfcommon.eventbus;


import com.rongtuoyouxuan.chatlive.biz2.model.stream.EnterRoomBean;

public class LiveEventData {
    //登录成功
    public static LiveEventKey<String> LIVE_ALLOW_RANGE = new LiveEventKey<>();
    public static LiveEventKey<EnterRoomBean> LIVE_NEXT_PAGER = new LiveEventKey<>();
}
