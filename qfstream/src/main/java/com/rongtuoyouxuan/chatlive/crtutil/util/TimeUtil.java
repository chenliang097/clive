package com.rongtuoyouxuan.chatlive.crtutil.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public final class TimeUtil {
    static public String getTimeFormatStringForUserList(long time) {
        if (time <= 0) {
            return "";
        }

        Calendar curTime = Calendar.getInstance();
        Calendar messageTime = Calendar.getInstance();
        messageTime.setTimeInMillis(time * 1000);

        String timeString = "";

        if (curTime.get(Calendar.DAY_OF_YEAR) == messageTime.get(Calendar.DAY_OF_YEAR)) {//同一天
            return getTimeAMPM(messageTime);
        } else if (curTime.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) == 1) {//前一天
            return "昨天";
        } else if ((curTime.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) > 1)
                && (curTime.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) < 7)) {//本周
            int day = messageTime.get(Calendar.DAY_OF_WEEK);
            switch (day) {
                case Calendar.SUNDAY:
                    return "星期日";
                case Calendar.MONDAY:
                    return "星期一";
                case Calendar.TUESDAY:
                    return "星期二";
                case Calendar.WEDNESDAY:
                    return "星期三";
                case Calendar.THURSDAY:
                    return "星期四";
                case Calendar.FRIDAY:
                    return "星期五";
                case Calendar.SATURDAY:
                    return "星期六";
                default:
                    return timeString;
            }
        } else if (curTime.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) >= 7) {//超过一周
            return messageTime.get(Calendar.YEAR) + "-" + messageTime.get(Calendar.MONTH) + "-" + messageTime.get(Calendar.DAY_OF_MONTH);
        }
        return timeString;
    }

    static public String getTimeFormatStringForMessageList(long time) {
        if (time <= 0) {
            return "";
        }
        Calendar curTime = Calendar.getInstance();
        Calendar messageTime = Calendar.getInstance();
        messageTime.setTimeInMillis(time * 1000);

        String timeString = "";

        if (curTime.get(Calendar.DAY_OF_YEAR) == messageTime.get(Calendar.DAY_OF_YEAR)) {//同一天
            return getTimeAMPM(messageTime);
        } else if (curTime.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) == 1) {//前一天
            return "昨天 " + getTimeAMPM(messageTime);
        } else if ((curTime.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) > 1)
                && (curTime.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) < 7)) {//本周
            int day = messageTime.get(Calendar.DAY_OF_WEEK);
            switch (day) {
                case Calendar.SUNDAY:
                    timeString = "星期日";
                    break;
                case Calendar.MONDAY:
                    timeString = "星期一";
                    break;
                case Calendar.TUESDAY:
                    timeString = "星期二";
                    break;
                case Calendar.WEDNESDAY:
                    timeString = "星期三";
                    break;
                case Calendar.THURSDAY:
                    timeString = "星期四";
                    break;
                case Calendar.FRIDAY:
                    timeString = "星期五";
                    break;
                case Calendar.SATURDAY:
                    timeString = "星期六";
                    break;
                default:
                    break;
            }
            return timeString + " " + getTimeAMPM(messageTime);
        } else if (curTime.get(Calendar.DAY_OF_YEAR) - messageTime.get(Calendar.DAY_OF_YEAR) >= 7) {//超过一周
            return messageTime.get(Calendar.YEAR) + "-" + messageTime.get(Calendar.MONTH) + "-" + messageTime.get(Calendar.DAY_OF_MONTH);
        }
        return timeString;
    }

    static private String getTimeAMPM(Calendar messageTime) {
        String timeString;
        int hourIn24 = messageTime.get(Calendar.HOUR_OF_DAY);
        if (hourIn24 >= 0 && hourIn24 < 5) {//凌晨
            timeString = String.format(Locale.SIMPLIFIED_CHINESE, "凌晨 %02d:%02d", messageTime.get(Calendar.HOUR), messageTime.get(Calendar.MINUTE));
            return timeString;
        } else if (hourIn24 >= 5 && hourIn24 < 12) {//上午
            timeString = String.format(Locale.SIMPLIFIED_CHINESE, "上午 %02d:%02d", messageTime.get(Calendar.HOUR), messageTime.get(Calendar.MINUTE));
            return timeString;
        } else {//下午
            timeString = String.format(Locale.SIMPLIFIED_CHINESE, "下午 %02d:%02d", messageTime.get(Calendar.HOUR), messageTime.get(Calendar.MINUTE));
            return timeString;
        }
    }

    /**
     * 分：秒：毫秒
     *
     * @param ms
     * @return
     */
    public static String formatData(long ms) {
        long minute = ms / (60 * 1000);
        long senond = (ms - minute * 60 * 1000) / 1000;
        long ss = ms - minute * 60 * 1000 - senond * 1000;
        ss = ss >= 0 ? ss : 0;
        ss /= 10;

        String time = String.format("%02d:%02d.%02d", minute, senond, ss);
        return time;
    }


    /**
     * 时：分：秒
     *
     * @param ms
     * @return
     */
    public static String formatData2(long ms) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms);
        return hms;
    }

    /**
     * Y-M-D HH:mm
     *
     * @param ms
     * @return
     */
    public static String formatDate3(long ms) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = format.format(ms * 1000);
        return date;
    }

    /**
     * Y-M-D
     *
     * @param ms
     * @return
     */
    public static String formatDate4(long ms) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(ms);
    }
    /**
     * Y-M-D HH:mm:ss
     *
     * @param ms
     * @return
     */
    public static String formatDate5(long ms) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(ms * 1000);
        return date;
    }

}
