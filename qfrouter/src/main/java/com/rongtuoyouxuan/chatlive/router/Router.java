package com.rongtuoyouxuan.chatlive.router;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.rongtuoyouxuan.chatlive.router.bean.FromSource;
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant;
import com.rongtuoyouxuan.chatlive.router.constants.RouterParams;

public class Router {

    /**
     * 公用
     */
    public static void toUrl(Context context, String url) {
        try {
            if (StringUtils.isTrimEmpty(url)) return;
            Activity topActivity = ActivityUtils.getTopActivity();
            if (url.startsWith("http://") || url.startsWith("https://")) {
                toWebActivity(topActivity, url);
            } else {
                ARouter.getInstance().build(url)
                        .navigation(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 跳转开播界面
     */
    public static void toStreamActivity() {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_STREAM).navigation();
    }

    /**
     * 跳转开播界面
     */
    public static void toStreamActivity(String userId, String userName) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_STREAM)
                .withString("userId", userId)
                .withString("userName", userName)
                .navigation();
    }

    /**
     * 跳转直播界面
     */
    public static void toLiveRoomActivity(String streamId, String anchorId, @FromSource String fromSource, boolean recoverIs) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_LIVEROOM)
                .withString("stream_id", streamId)
                .withString("anchor_id", anchorId)
                .withString("from_source", fromSource)
                .withBoolean("recover_is", recoverIs)
                .withInt("type", 2)
                .navigation();
    }

    /**
     * 跳转直播界面
     */
    public static void toLiveRoomActivity(String roomId, String streamId, String sceneId, String anchorId, @FromSource String fromSource) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_LIVEROOM)
                .withString("room_id", roomId)
                .withString("stream_id", streamId)
                .withString("from_source", fromSource)
                .withString("sceneId", sceneId)
                .withString("anchor_id", anchorId)
                .withInt("type", 1)
                .navigation();
    }

    /**
     * 跳转直播界面
     */
    public static void toLiveRoomActivity(String liveData, String streamId, String anchorId, @FromSource String fromSource, boolean recoverIs) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_LIVEROOM)
                .withString("liveData", liveData)
                .withString("stream_id", streamId)
                .withString("anchor_id", anchorId)
                .withString("from_source", fromSource)
                .withBoolean("recover_is", recoverIs)
                .withInt("type", 1)
                .navigation();
    }

    /**
     * 跳转webview
     */
    public static void toWebViewActivity(String url, boolean addCommonParams) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_WEBVIEW)
                .withString("url", url)
                .withBoolean("addCommonParams", addCommonParams)
                .navigation();
    }

    /**
     * 跳转半屏webview
     */
    public static void toHalfScreenWebViewActivity(String url, String hostId, boolean addCommonParams) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_HALFSCREEN_WEBVIEW)
                .withString("url", url)
                .withBoolean("addCommonParams", addCommonParams)
                .withString("hostId", hostId)
                .navigation();
    }


    //充值dialog
    public static void toGoldToBuyDialog(String fromSource) {
        ARouter.getInstance().build(RouterConstant.PATH_BUY_GOLD_DIALOG)
                .withString("fromSource", fromSource)
                .navigation();
    }

    //主播端直播结束
    public static void toStreamEndActivity(Context context, String streamId, String streamType) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_STREAM_END)
                .withString("streamId", streamId)
                .withString("streamType", streamType)
                .navigation(context);
    }

    //观看端直播结束
    public static void toLiveEndActivity(Context context, String streamId, long anchorId, String avatar, String nickName, long time, boolean isFollow, String pic) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_LIVE_END)
                .withString("streamId", streamId)
                .withLong("anchorId", anchorId)
                .withString("avatar", avatar)
                .withString("nickName", nickName)
                .withLong("time", time)
                .withBoolean("follow", isFollow)
                .withString("pic", pic)
                .navigation(context);
    }

    public static void toRechargeActivity(Context context, boolean isFinish, int type) {
        ARouter.getInstance().build(RouterConstant.PATH_RECHARGE)
                .withBoolean(RouterParams.IS_FINISH, isFinish)
                .withInt(RouterParams.type, type)
                .navigation(context);
    }

    //用户主页
    public static void toUserInfoActivity(Context context, long userId,boolean isToMainActivity) {
        ARouter.getInstance().build(RouterConstant.PATH_USER_INFO)
                .withLong(RouterParams.USER_ID, userId)
                .withBoolean(RouterParams.IS_TO_MAIN_ACTIVITY, isToMainActivity)
                .navigation(context);
    }

    public static void toUserInfoActivity(Context context, long userId) {
        toUserInfoActivity(context,userId,false);
    }

    //背包页面
    //tabId  Config.CAR  Config.AVATAR
    public static void toBackPackActivity(Context context, int tabId) {
        ARouter.getInstance().build(RouterConstant.PATH_BACK_PACK)
                .withInt(RouterParams.TAB_ID, tabId)
                .navigation(context);
    }

    //web(h5)页面
    public static void toWebActivity(Context context, String url,boolean isToMainActivity) {
        ARouter.getInstance().build(RouterConstant.PATH_WEB)
                .withString(RouterParams.URL, url)
                .withBoolean(RouterParams.IS_TO_MAIN_ACTIVITY, isToMainActivity)
                .navigation(context);
    }

    //web(h5)页面
    public static void toWebActivity(Context context, String url) {
        toWebActivity(context,url,false);
    }

    //打开礼物面板--场景默认是live
    public static void toGiftPanelActivity(Context context, String roomId, String sceneId, String anchorId, String userId, String userName, String avatar) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_GIFT_PANEL)
                .withString("roomId", roomId)
                .withString("sceneId", sceneId)
                .withString("anchorId", anchorId)
                .withString("userId", userId)
                .withString("userName", userName)
                .withString("avatar", avatar)
                .navigation(context);
    }

    //web(h5)页面 半屏
    public static void toWebDialogActivity(Context context, String url) {
        ARouter.getInstance().build(RouterConstant.PATH_WEB_DIALOG)
                .withString(RouterParams.URL, url)
                .navigation(context);
    }

    //web(h5)页面 半屏
    public static void toWebDialogActivity(Context context, String url, int viewHeight) {
        ARouter.getInstance().build(RouterConstant.PATH_WEB_DIALOG)
                .withString(RouterParams.URL, url)
                .withInt("height", viewHeight)
                .navigation(context);
    }

    //简介
    public static void toGiftDescActivity(Context context, String title, String content) {
        ARouter.getInstance().build(RouterConstant.PATH_ACTIVITY_GIFT_DESC)
                .withString("title", title)
                .withString("content", content)
                .navigation(context);
    }

    //充值方式dialog
    public static void toPayTypeDialogg(int rt_coin, int rt_rmb) {
        ARouter.getInstance().build(RouterConstant.PATH_PAY_TYPE_DIALOG)
                .withInt("rt_coin", rt_coin)
                .withInt("rt_rmb", rt_rmb)
                .navigation();
    }

    //身份验证
    public static void toUserIdentificationActivity() {
        ARouter.getInstance().build(RouterConstant.PATH_USER_IDENTIFICATION)
                .navigation();
    }

}
