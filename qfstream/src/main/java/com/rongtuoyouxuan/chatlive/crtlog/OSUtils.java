package com.rongtuoyouxuan.chatlive.crtlog;

public class OSUtils {
    /**
     * 是否是android 11 以后的版本
     *
     * @return boolean
     */
    public static boolean isRLater() {
        return android.os.Build.VERSION.SDK_INT >= 30;
    }
}
