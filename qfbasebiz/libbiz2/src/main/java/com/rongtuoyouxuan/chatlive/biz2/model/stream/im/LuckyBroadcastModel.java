package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

/**
 * 描述：
 *
 * @time 2019/9/26
 */
public class LuckyBroadcastModel extends BaseBroadcastGiftModel {


    /**
     * score : 13312462
     * luck_time : 1569380135
     * luck_left : 60
     */

    public int score = 10000;
    public int luck_time;
    public int luck_left;

    @Override
    public BroadcastType getType() {
        return BroadcastType.LUCKYGIFT;
    }
}
