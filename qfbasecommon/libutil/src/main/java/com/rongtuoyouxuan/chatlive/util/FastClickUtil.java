package com.rongtuoyouxuan.chatlive.util;

public class FastClickUtil {

    private static final int MIN_CLICK_DELAY_TIME = 200;//ms
    private static long lastClickTime;
    private FastClickUtil() {
    }

    public synchronized static boolean isFastClick() {
        synchronized (FastClickUtil.class) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - lastClickTime < MIN_CLICK_DELAY_TIME) {
                return true;
            }
            lastClickTime = currentTimeMillis;
            return false;
        }
    }
}
