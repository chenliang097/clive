package com.rongtuoyouxuan.chatlive.util;

public class NumUtils {
    public static int parseInt(String str) {
        int val = -1;
        try {
            val = Integer.parseInt(str);
        } catch (Exception e) {

        }
        return val;
    }

    public static float parseFloat(String str) {
        float val = 0f;
        try {
            val = Float.parseFloat(str);
        } catch (Exception e) {

        }
        return val;
    }

    public static long parseLong(String str) {
        long val = 0;
        try {
            val = Long.parseLong(str);
        } catch (Exception e) {

        }
        return val;
    }
}