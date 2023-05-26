package com.rongtuoyouxuan.chatlive.qfcommon.util;

import java.util.Calendar;
import java.util.Date;


public final class TimeUtil {
    public static final String FORMAT_1 = "yyyy-MM-dd";

    /**
     * 获取当前年份
     */
    public static int getCurrentYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int intYear = c.get(Calendar.YEAR);
        return intYear;
    }

    /**
     * 获取当前年份
     */
    public static int getCurrentYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int intYear = c.get(Calendar.YEAR);
        return intYear;
    }

    /**
     * 获取当前月份
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int intMonth = c.get(Calendar.MONTH);
        return intMonth;
    }

    /**
     * 获取当前月份
     */
    public static int getCurrentMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int intMonth = c.get(Calendar.MONTH);
        return intMonth;
    }

    /**
     * 获取当前天
     */
    public static int getCurrentDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int intDay = c.get(Calendar.DAY_OF_MONTH);
        return intDay;
    }


    /**
     * 获取当前天
     */
    public static int getCurrentDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int intDay = c.get(Calendar.DAY_OF_MONTH);
        return intDay;
    }

    /**
     * 获取当前小时
     */
    public static int getCurrentHours() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int intHour = c.get(Calendar.HOUR_OF_DAY);
        return intHour;
    }

    /**
     * 获取当前小时
     */
    public static int getCurrentHours(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int intHour = c.get(Calendar.HOUR_OF_DAY);
        return intHour;
    }

    /**
     * 获取当前分钟
     */
    public static int getCurrentMinute() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int intMinute = c.get(Calendar.MINUTE);
        return intMinute;
    }

    /**
     * 获取当前分钟
     */
    public static int getCurrentMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int intMinute = c.get(Calendar.MINUTE);
        return intMinute;
    }
}
