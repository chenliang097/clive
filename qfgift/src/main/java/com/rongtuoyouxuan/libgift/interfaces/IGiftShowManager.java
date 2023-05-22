package com.rongtuoyouxuan.libgift.interfaces;


import android.content.Context;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTGiftMsg;

/**
 * Describe: IGiftShowManager
 *
 * @author Ning
 * @date 2019/6/4
 */
public interface IGiftShowManager {

    int ONE = 1;
    int TWO = 2;
    int THREE = 3;

    float MagicTextBigScale = 2.0f;
    float MagicTextNormalScale = 1.0f;

    int WHAT_START_TRAINING_ONE = 0x1990;//开始显示礼物一
    int WHAT_START_TRAINING_TWO = 0x1991;//开始显示礼物二
    int WHAT_START_TRAINING_THREE = 0x1995;//开始显示礼物三
    int WHAT_START_MAGIC_TRAINING_ONE = 0x1993;//继续显示礼物一
    int WHAT_START_MAGIC_TRAINING_TWO = 0x1994;//继续显示礼物二
    int WHAT_START_MAGIC_TRAINING_THREE = 0x1996;//继续显示礼物三
    int WHAT_GIFT_HIDDEN = 0x1992;//礼物隐藏

    long GIFT_BG_IN_DURATION = 500;//背景进场时间
    long GIFT_NUM_ADD_DURATION = 150;//数字增加时间
    long GIFT_EXIT_DURATION = 500;//退出时间
    long GIFT_EXIT_DURATION_DELAY = 3000;//等待3s退出


    void add(RTGiftMsg info, Context context);

    void setHostId(String hostId);

    void onDestory();
}
