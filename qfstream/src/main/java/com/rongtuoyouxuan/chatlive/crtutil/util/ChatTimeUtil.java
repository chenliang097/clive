package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.annotation.SuppressLint;
import android.content.Context;

import com.rongtuoyouxuan.chatlive.stream.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author Administrator
 */
@SuppressLint("SimpleDateFormat")
public class ChatTimeUtil {
    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    public static String getMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm");
        return format.format(new Date(time));
    }

    /**
     * Wed Oct 07 10:30:21 CST 2015转换数字格式
     *
     * @param timesamp
     * @return
     */
    public static String getSZTime(String timesamp) {

        String timesamp1 = Date.parse(timesamp) + "";
        return timesamp1;
    }

    @SuppressLint("StringFormatMatches")
    public static String getChatTime(Context context, long timesamp) {
        long currtime = System.currentTimeMillis();
        long oldtime = timesamp * 1000l;
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        SimpleDateFormat mdf = new SimpleDateFormat("MM");
        SimpleDateFormat ydf = new SimpleDateFormat("yy");
        Date today = new Date(currtime);
        Date otherDay = new Date(oldtime);
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        int mtemp = Integer.parseInt(mdf.format(today)) - Integer.parseInt(mdf.format(otherDay));
        int ytemp = Integer.parseInt(ydf.format(today)) - Integer.parseInt(ydf.format(otherDay));
        int min = Integer.parseInt(getMin(System.currentTimeMillis())) - Integer.parseInt(getMin(oldtime));
        switch (temp) {
            case 0:
                switch (min) {
                    case 0:
                        result = 0 + "";
                        break;
                    case 1:
                    case 2:
                    case 3:
                        result = context.getResources().getString(R.string.just);
                        break;
                    default:
                        result = String.format(context.getString(R.string.minutesago), min);
                        break;
                }
                result = getHourAndMin(oldtime);
                break;
            default:
                int days = ytemp * 365 + mtemp * 30 + temp;
                if (days >= 365) {
                    result = context.getResources().getString(R.string.yearago);
                } else {
                    if (days >= 2) {
                        result = String.format(context.getString(R.string.daysago), days);

                    } else {
                        result = String.format(context.getString(R.string.dayago), days);
                    }
                }
                break;
        }
        return result;
    }

//    @SuppressLint("StringFormatMatches")
//    public static String getEncounterTime(Context context, long timesamp) {
//        String result = "";
//        SimpleDateFormat sdf = new SimpleDateFormat("dd");
//        SimpleDateFormat mdf = new SimpleDateFormat("MM");
//        SimpleDateFormat ydf = new SimpleDateFormat("yy");
//        Date today = new Date(System.currentTimeMillis());
//        Date otherDay = new Date(timesamp * 1000l);
//        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
//        int mtemp = Integer.parseInt(mdf.format(today)) - Integer.parseInt(mdf.format(otherDay));
//        int ytemp = Integer.parseInt(ydf.format(today)) - Integer.parseInt(ydf.format(otherDay));
//        int min = Integer.parseInt(getMin(System.currentTimeMillis())) - Integer.parseInt(getMin(timesamp));
//        switch (temp) {
//            case 0:
//                result = context.getResources().getString(R.string.today);
//                break;
//            default:
//                int days = ytemp * 365 + mtemp * 30 + temp;
//                if (days >= 365) {
//                    result = context.getResources().getString(R.string.yearago);
//                } else {
//                    if (days >= 2) {
//                        result = String.format(context.getString(R.string.dayago), days);
//                    } else {
//                        result = String.format(context.getString(R.string.dayago), days);
//
//                    }
//                }
//                // result = getTime(timesamp);
//                break;
//        }
//        return result;
//    }

    public static String getEncounterTime(Context context, long timesamp) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        SimpleDateFormat mdf = new SimpleDateFormat("MM");
        SimpleDateFormat ydf = new SimpleDateFormat("yy");
        Date today = new Date(System.currentTimeMillis());
        Date otherDay = new Date(timesamp * 1000l);
        int temp = Integer.parseInt(sdf.format(today)) - Integer.parseInt(sdf.format(otherDay));
        int mtemp = Integer.parseInt(mdf.format(today)) - Integer.parseInt(mdf.format(otherDay));
        int ytemp = Integer.parseInt(ydf.format(today)) - Integer.parseInt(ydf.format(otherDay));
        int min = Integer.parseInt(getMin(System.currentTimeMillis())) - Integer.parseInt(getMin(timesamp));

        long time = System.currentTimeMillis() - timesamp * 1000l;
        long year = time / (3600 * 24 * 30 * 12 * 1000L);
        long month = time / (3600 * 24 * 30 * 1000L);
        long day = time / (3600 * 24 * 1000L);
        long hour = time / (3600 * 1000L);
        long minute = time / (60 * 1000L);

        if (year > 0) {
            result = context.getResources().getString(R.string.yearago_format, String.valueOf(year));
        } else if (month > 0) {
            result = context.getResources().getString(R.string.monthago_format, String.valueOf(month));
        } else if (day > 0) {
            result = context.getResources().getString(R.string.dayago, String.valueOf(day));
        } else if (hour > 0) {
            result = context.getResources().getString(R.string.hourago_format, String.valueOf(hour));
        } else if (minute > 0) {
            result = context.getResources().getString(R.string.minutesago, String.valueOf(minute));
        } else {
            result = context.getResources().getString(R.string.just);
        }
        return result;
    }


    //
    public static String getSystemTime() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -365);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        return year + "-" + month + "-" + date + " " + hours + ":" + minute + ":" + second;
    }

    public static String getTimeToDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -365);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        return year + "-" + month + "-" + date + " " + hours + ":" + minute + ":" + second;
    }

    public static String getStringToDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }

    public static String getStringToDateShort(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }

    public static String getStringToDateSs(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    public static String getDateToString(long time, SimpleDateFormat dateFormat) {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        }
        return dateFormat.format(new Date(time * 1000l));
    }

    public static long getDateCount(long timeLeft, long timeRight) {
        return (timeLeft - timeRight) / 60 / 1000;
    }

}
