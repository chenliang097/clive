package com.rongtuoyouxuan.qfcommon.player.exo;

/**
 * 
 * date:2022/8/8-17:05
 * des:
 */
public class Logger {
    private static Logging instance = null;

    public Logger() {
    }

    public static void setLogger(Logging logging) {
        instance = logging;
    }

    public static void d(String log) {
        if (instance != null) {
            instance.d(log);
        }

    }

    public static void d(Throwable log) {
        if (instance != null) {
            instance.d(log);
        }

    }

    public static void d(String tag, String log) {
        if (instance != null) {
            instance.d(tag, log);
        }

    }
}
