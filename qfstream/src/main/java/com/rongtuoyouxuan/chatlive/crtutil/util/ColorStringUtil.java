package com.rongtuoyouxuan.chatlive.crtutil.util;

import androidx.annotation.ColorInt;
import androidx.annotation.Size;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Locale;

public final class ColorStringUtil {

    private static final String COLOR_DEFAULT = "#FF0000";

    private static final HashMap<String, Integer> sColorNameMap;
    @ColorInt
    private static final int BLACK = 0xFF000000;
    @ColorInt
    private static final int DKGRAY = 0xFF444444;
    @ColorInt
    private static final int GRAY = 0xFF888888;
    @ColorInt
    private static final int LTGRAY = 0xFFCCCCCC;
    @ColorInt
    private static final int WHITE = 0xFFFFFFFF;
    @ColorInt
    private static final int RED = 0xFFFF0000;
    @ColorInt
    private static final int GREEN = 0xFF00FF00;
    @ColorInt
    private static final int BLUE = 0xFF0000FF;
    @ColorInt
    private static final int YELLOW = 0xFFFFFF00;
    @ColorInt
    private static final int CYAN = 0xFF00FFFF;
    @ColorInt
    private static final int MAGENTA = 0xFFFF00FF;
    @ColorInt
    private static final int MAROON = 0xFF800000;
    @ColorInt
    private static final int NAVY = 0xFF000080;
    @ColorInt
    private static final int OLIVE = 0xFF808000;
    @ColorInt
    private static final int PURPLE = 0xFF800080;
    @ColorInt
    private static final int SILVER = 0xFFC0C0C0;
    @ColorInt
    private static final int TEAL = 0xFF008080;

    static {
        sColorNameMap = new HashMap<String, Integer>();
        sColorNameMap.put("black", BLACK);
        sColorNameMap.put("darkgray", DKGRAY);
        sColorNameMap.put("gray", GRAY);
        sColorNameMap.put("lightgray", LTGRAY);
        sColorNameMap.put("white", WHITE);
        sColorNameMap.put("red", RED);
        sColorNameMap.put("green", GREEN);
        sColorNameMap.put("blue", BLUE);
        sColorNameMap.put("yellow", YELLOW);
        sColorNameMap.put("cyan", CYAN);
        sColorNameMap.put("magenta", MAGENTA);
        sColorNameMap.put("aqua", CYAN);
        sColorNameMap.put("fuchsia", MAGENTA);
        sColorNameMap.put("darkgrey", DKGRAY);
        sColorNameMap.put("grey", GRAY);
        sColorNameMap.put("lightgrey", LTGRAY);
        sColorNameMap.put("lime", GREEN);
        sColorNameMap.put("maroon", MAROON);
        sColorNameMap.put("navy", NAVY);
        sColorNameMap.put("olive", OLIVE);
        sColorNameMap.put("purple", PURPLE);
        sColorNameMap.put("silver", SILVER);
        sColorNameMap.put("teal", TEAL);

    }


    /**
     * 判断是否支持
     *
     * @param colorString colorString
     * @return boolean
     */
    private static boolean isColorString(@Size(min = 1) String colorString) {
        if (colorString.charAt(0) == '#') {
            // Use a long to avoid rollovers on #ffXXXXXX
            try {
                Long.parseLong(colorString.substring(1), 16);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
            if (colorString.length() == 7) {
                return true;
            } else if (colorString.length() != 9) {
                return false;
            }
            return true;
        } else {
            Integer color = sColorNameMap.get(colorString.toLowerCase(Locale.ROOT));
            if (color != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 转换为支持的颜色
     *
     * @param color color
     * @return String
     */
    public static String getSupporColor(String color) {
        if (TextUtils.isEmpty(color)) {
            return COLOR_DEFAULT;
        } else {
            if (isColorString(color)) {
                return color;
            } else {
                return COLOR_DEFAULT;
            }
        }
    }


    public static String getSupportColor(String color, String defaultColor) {
        if (TextUtils.isEmpty(color)) {
            return defaultColor;
        } else {
            if (isColorString(color)) {
                return color;
            } else {
                return defaultColor;
            }
        }
    }


    public static String getSafeColor(String color){
        if (TextUtils.isEmpty(color)) {
            return "";
        } else {
            if (isColorString(color)) {
                return color;
            } else {
                return "";
            }
        }
    }
}
