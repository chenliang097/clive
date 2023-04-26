package com.rongtuoyouxuan.chatlive.util;


import android.content.Context;
import android.graphics.Color;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;

public final class ColorParseUtil {

    /**
     * parse color
     *
     * @param context      Context
     * @param colorString  colorString
     * @param defaultColor defaultColor
     * @return
     */
    public static int parse(Context context, String colorString, @ColorRes int defaultColor) {
        int colorInt;
        String colorSafe = ColorStringUtil.getSafeColor(colorString);
        if (TextUtils.isEmpty(colorSafe)) {
            colorInt = ContextCompat.getColor(context, defaultColor);
        } else {
            try {
                colorInt = Color.parseColor(colorSafe);
            } catch (Throwable e) {
                e.printStackTrace();
                colorInt = ContextCompat.getColor(context, defaultColor);
            }
        }
        return colorInt;
    }
}
