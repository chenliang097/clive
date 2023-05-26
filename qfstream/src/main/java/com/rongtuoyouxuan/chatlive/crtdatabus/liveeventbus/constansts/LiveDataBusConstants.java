package com.rongtuoyouxuan.chatlive.crtdatabus.liveeventbus.constansts;


public interface LiveDataBusConstants {

    /**
     * 关注按钮消失
     */
    String EVENT_KEY_TO_FOLLOW_GONE = "follow_gone";
    //游客跳转登录弹窗
    String EVENT_KEY_TO_SHOW_LOGIN_DIALOG = "show_login_dilog";
    // 私聊、群聊发送gif点击回调
    String EVENT_KEY_TO_SEND_IM_GIF_CLICK = "im_gif_click";
    //关播 finish gif和gift面板
    String EVENT_KEY_TO_FINISH_ROOM_ACTIVITY = "finish_room_all_activity";
    //gif面板finish之后 调整公聊区位置
    String EVENT_KEY_TO_ADJUST_PUBLIC_CHAT = "adjust_public_chat";
    //连麦申请状态
    String EVENT_KEY_TO_PAY_COMPLETE = "pay_complete";
}
