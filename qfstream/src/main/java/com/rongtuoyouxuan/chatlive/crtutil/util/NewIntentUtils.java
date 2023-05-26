package com.rongtuoyouxuan.chatlive.crtutil.util;

/**
 * Created by KingOX on 2016/4/18.
 */
public class NewIntentUtils {

    private static long mLastOpenActivityTime = 0;      //为防止快速点击打开多个ACTIVITY

    public static boolean canNewIntent() {
        // 为防止快速点击，打开多个ACTIVITY
        long now = System.currentTimeMillis();
        if (Math.abs(now - mLastOpenActivityTime) < 500)
            return false;

        mLastOpenActivityTime = now;
        return true;
    }

    public static boolean canNewIntent(long time) {
        // 为防止快速点击，打开多个ACTIVITY
        long now = System.currentTimeMillis();
        if (Math.abs(now - mLastOpenActivityTime) < time)
            return false;

        mLastOpenActivityTime = now;
        return true;
    }
}
