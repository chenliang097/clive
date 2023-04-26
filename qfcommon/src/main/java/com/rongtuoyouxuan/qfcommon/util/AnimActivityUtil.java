/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rongtuoyouxuan.qfcommon.util;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;

import com.rongtuoyouxuan.qfcommon.R;


/**
 * 动画工具类
 * activity 过渡动画工具类
 */
public class AnimActivityUtil {

    private AnimActivityUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!第一套动画!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    /**
     * Activity 从左边进入
     *
     * @param activity
     */
    public static void enterFromLeft(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_left_in, R.anim.qfcommon_sutils_right_out);
    }

    /**
     * Activity 从左边退出
     *
     * @param activity
     */
    public static void exitToLeft(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_right_in, R.anim.qfcommon_sutils_left_out);
    }

    /**
     * Activity 从右边进入
     *
     * @param activity
     */
    public static void enterFromRight(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_right_in, R.anim.qfcommon_sutils_left_out);
    }

    /**
     * Activity 从右边退出
     *
     * @param activity
     */
    public static void exitToRight(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_left_in, R.anim.qfcommon_sutils_right_out);
    }

    /**
     * Activity 从上边进入
     *
     * @param activity
     */
    public static void enterFromTop(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_top_in, R.anim.qfcommon_sutils_unchanged);
    }

    /**
     * Activity 从上边退出
     *
     * @param activity
     */
    public static void exitToTop(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_unchanged, R.anim.qfcommon_sutils_top_out);
    }

    /**
     * Activity 从下边进入
     *
     * @param activity
     */
    public static void enterFromBottom(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_bottom_in, R.anim.qfcommon_sutils_unchanged);
    }

    /**
     * Activity 从下边退出
     *
     * @param activity
     */
    public static void exitToBottom(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_unchanged, R.anim.qfcommon_sutils_bottom_out);
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!第二套动画!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    /**
     * Activity 放大进入
     *
     * @param activity
     */
    public static void enterFromScale(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_create_zoomin, R.anim.qfcommon_create_zoomout);
    }


    /**
     * Activity 缩小退出
     *
     * @param activity
     */
    public static void exitToScale(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_scale_in, R.anim.qfcommon_sutils_scale_out);
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!第三套动画!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    /**
     * Activity 放大淡入
     *
     * @param activity
     */

    public static void inZoom(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_fade, R.anim.qfcommon_sutils_hold);
    }

    /**
     * Activity缩小退出
     *
     * @param activity
     */

    public static void exitZoom(@NonNull Activity activity) {
        startTransition(activity, R.anim.qfcommon_sutils_zoom_enter, R.anim.qfcommon_sutils_zoom_exit);
    }


    /**
     * 启动过渡动画
     *
     * @param activity   activity
     * @param enterStyle activity 的进场效果样式
     * @param outStyle   activity的退场效果样式
     */
    @SuppressLint("NewApi")
    private static void startTransition(@NonNull Activity activity, int enterStyle, int outStyle) {
        activity.overridePendingTransition(enterStyle, outStyle);

    }

}
