package com.rongtuoyouxuan.chatlive.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj、Super秦
 *     blog  : http://blankj.com
 *     time  : 2018/11/15
 *     desc  : utils about adapt screen
 * </pre>
 */
public final class MyAdaptScreenUtils {
    public static int defaultWidth = 750;
    public static int defaultHeight = 1624;

    private static List<Field> sMetricsFields;

    private MyAdaptScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    ///适配横屏
    @NonNull
    public static Resources adaptHorizontal(@NonNull final Resources resources, final int design) {
        float newXdpi = (Math.max(resources.getDisplayMetrics().widthPixels, resources.getDisplayMetrics().heightPixels) * 72f) / design;
        applyDisplayMetrics(resources, newXdpi);
        return resources;
    }

    ///适配竖屏
    @NonNull
    public static Resources adaptVertical(@NonNull final Resources resources, final int design) {
        float newXdpi = (Math.min(resources.getDisplayMetrics().widthPixels, resources.getDisplayMetrics().heightPixels) * 72f) / design;
        applyDisplayMetrics(resources, newXdpi);
        return resources;
    }

    /**
     * @param resources The resources.
     * @return the resource
     */
    @NonNull
    public static Resources closeAdapt(@NonNull final Resources resources) {
        float newXdpi = Resources.getSystem().getDisplayMetrics().density * 72f;
        applyDisplayMetrics(resources, newXdpi);
        return resources;
    }

    private static void applyDisplayMetrics(@NonNull final Resources resources, final float newXdpi) {
        resources.getDisplayMetrics().xdpi = newXdpi;
        Utils.getApp().getResources().getDisplayMetrics().xdpi = newXdpi;
        applyOtherDisplayMetrics(resources, newXdpi);
    }

    private static void applyOtherDisplayMetrics(final Resources resources, final float newXdpi) {
        if (sMetricsFields == null) {
            sMetricsFields = new ArrayList<>();
            Class resCls = resources.getClass();
            Field[] declaredFields = resCls.getDeclaredFields();
            while (declaredFields != null && declaredFields.length > 0) {
                for (Field field : declaredFields) {
                    if (field.getType().isAssignableFrom(DisplayMetrics.class)) {
                        field.setAccessible(true);
                        DisplayMetrics tmpDm = getMetricsFromField(resources, field);
                        if (tmpDm != null) {
                            sMetricsFields.add(field);
                            tmpDm.xdpi = newXdpi;
                        }
                    }
                }
                resCls = resCls.getSuperclass();
                if (resCls != null) {
                    declaredFields = resCls.getDeclaredFields();
                } else {
                    break;
                }
            }
        } else {
            applyMetricsFields(resources, newXdpi);
        }
    }

    private static void applyMetricsFields(final Resources resources, final float newXdpi) {
        for (Field metricsField : sMetricsFields) {
            try {
                DisplayMetrics dm = (DisplayMetrics) metricsField.get(resources);
                if (dm != null) dm.xdpi = newXdpi;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static DisplayMetrics getMetricsFromField(final Resources resources, final Field field) {
        try {
            return (DisplayMetrics) field.get(resources);
        } catch (Exception ignore) {
            return null;
        }
    }
}
