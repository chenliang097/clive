package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.im;

/**
 * 超大福袋广播
 */
public class SuperLuckyMoneyBroadcastModel extends BaseBroadcastGiftModel {

    public long roomId;
    public String fromNickName;

    @Override
    public BroadcastType getType() {
        return BroadcastType.SUPER_LUCKY_MONEY;
    }
}
