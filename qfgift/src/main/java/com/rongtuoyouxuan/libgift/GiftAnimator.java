package com.rongtuoyouxuan.libgift;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.rongtuoyouxuan.DampingInterpolator;


/**
 * 说明：礼物动画
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/12/14 11:06
 * <p/>
 * 版本：verson 1.0
 */
public class GiftAnimator {

    /**
     * @param duration     持续时间
     * @return
     * 创建一个从左到右的飞入动画
     */
    public static Animation createFlyFromLtoR(long duration, Interpolator interpolator) {
        Animation animation = BaseAnimViewS.SlideFromLeft(interpolator);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        return animation;
    }

    /**
     * @param duration     持续时间
     * @return
     * 创建一个从右到左的飞入动画
     */
    public static Animation createFlyFromRtoL(long duration, Interpolator interpolator) {
        Animation animation = BaseAnimViewS.SlideFromRight(interpolator);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        return animation;
    }

    /**
     * 送礼数字变化
     */
    public static Animation scaleGiftNum(Context context,long duration){
        Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.pl_libgift_gift_room_master_num);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setInterpolator(new DampingInterpolator(6,0.5f));
        return scaleAnimation;
    }

}
