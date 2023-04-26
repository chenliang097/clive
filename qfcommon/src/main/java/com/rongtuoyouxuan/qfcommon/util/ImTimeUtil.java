package com.rongtuoyouxuan.qfcommon.util;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.StringUtils;
import com.rongtuoyouxuan.qfcommon.R;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * ————————————————————————————————————————
 *
 * @Description : 聊天显示时间工具类
 * @Author : jianbo
 * @Date : 2022/7/21  10:38
 * ————————————————————————————————————————
 */
@SuppressLint("SimpleDateFormat")
public class ImTimeUtil {


    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    public static String getMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm");
        return format.format(new Date(time));
    }

    /**
     * 聊天时间显示规则：不到一年则：月/日  时：分；超过则加年即可；
     * <p>
     * 2022-07-15
     *
     * @param timeStamp
     * @return
     */
    public static String getChatTime(long timeStamp) {
        String result = "";
        GregorianCalendar gcCurrent = new GregorianCalendar();
        gcCurrent.setTime(new Date());
        int currentYear = gcCurrent.get(GregorianCalendar.YEAR);
        int currentMonth = gcCurrent.get(GregorianCalendar.MONTH) + 1;
        int currentDay = gcCurrent.get(GregorianCalendar.DAY_OF_MONTH);

        Date srcDate = new Date(timeStamp);
        GregorianCalendar gcSrc = new GregorianCalendar();
        gcSrc.setTime(srcDate);
        int srcYear = gcSrc.get(GregorianCalendar.YEAR);
        int srcMonth = gcSrc.get(GregorianCalendar.MONTH) + 1;
        int srcDay = gcSrc.get(GregorianCalendar.DAY_OF_MONTH);
        // 要额外显示的时间分钟
        String timeExtraStr = " " + getTimeString(srcDate, "HH:mm");
        // 当年
        if (currentYear == srcYear) {
            if (currentMonth == srcMonth && currentDay == srcDay) {
                result = timeExtraStr;
            } else {
                result = getTimeString(srcDate, "MM/dd ") + timeExtraStr;
            }
        } else
            result = getTimeString(srcDate, "yyyy/MM/dd ") + timeExtraStr;
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getConversationListTime(long timeStamp) {
        Date srcDate = new Date(timeStamp);
        Date current = new Date();
        int days = getBetweenDays(srcDate, current);
        if (days == 0) {
            long time = current.getTime() - srcDate.getTime();
            long seconds = time / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            if (minutes < 1) {
                return "刚刚";
            } else if (minutes < 60) {
                return minutes + "m前";
            }
            if (hours >= 1) {
                return hours + "h前";
            }
        } else if (days == 1) {
            return "昨天";
        }
        return days + "天前 ";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getPrivateDateTimeAxis(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String createTime = sf.format(date);
        int days = getBetweenDays(date, new Date());
        if (days == 0) {
            return "今天 " + createTime.split(" ")[1];
        } else if (days == 1) {
            return "昨天 " + createTime.split(" ")[1];
        }
        return createTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getPublicDateTimeAxis(Date date) {
        if (date == null) {
            return "";
        }
        Date current = new Date();
        int days = getBetweenDays(date, current);
        if (days == 0) {
            long time = current.getTime() - date.getTime();
            long seconds = time / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            if (minutes < 1) {
                return "刚刚";
            } else if (minutes >= 1 && minutes < 60) {
                return minutes + "分钟前";
            }
            return hours + "小时前";
        } else if (days == 1) {
            return "昨天";
        }
        return days + "天前 ";
    }

    /**
     * @param date1
     * @param date2
     * @return
     * @Desc 获取两个时间之间的间隔天数（方式1）
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int getBetweenDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new RuntimeException("日期不能为空");
        }
        LocalDate localDate1 = date2LocalDate(date1);
        LocalDate localDate2 = date2LocalDate(date2);

        Long until = localDate1.until(localDate2, ChronoUnit.DAYS);
        return until.intValue();
        // 这种方法是Java8特性
        //Generic.long2int(localDate1.until(localDate2, ChronoUnit.DAYS));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    /**
     * 时间戳（单位：毫秒）转换为友好的显示格式.
     * <p>
     * 1、刚刚：0-1分钟内；
     * 2、m:分:60分钟内；比如：1m前:1-2分钟之内
     * 3、h:小时- 24小时内；
     * 4、一周内：具体星期几；
     * 5、超过一周：X月X号；
     * 6、超过一年：XX年/X月X日；
     *
     * @param timeStamp 时间戳
     */
    public static String getConversationTime(long timeStamp) {
        Date srcDate = new Date(timeStamp);
        String ret = "";
        try {
            GregorianCalendar gcCurrent = new GregorianCalendar();
            gcCurrent.setTime(new Date());
            int currentYear = gcCurrent.get(GregorianCalendar.YEAR);
            int currentMonth = gcCurrent.get(GregorianCalendar.MONTH) + 1;
            int currentDay = gcCurrent.get(GregorianCalendar.DAY_OF_MONTH);

            GregorianCalendar gcSrc = new GregorianCalendar();
            gcSrc.setTime(srcDate);
            int srcYear = gcSrc.get(GregorianCalendar.YEAR);
//            int srcMonth = gcSrc.get(GregorianCalendar.MONTH) + 1;
//            int srcDay = gcSrc.get(GregorianCalendar.DAY_OF_MONTH);

//            // 要额外显示的时间分钟
//            String timeExtraStr = (mustIncludeTime ? " " + getTimeString(srcDate, "HH:mm") : "");

            // 当年
            if (currentYear == srcYear) {
                long currentTimestamp = gcCurrent.getTimeInMillis();
                long srcTimestamp = gcSrc.getTimeInMillis();
                // 相差时间（单位：毫秒）
                long delta = currentTimestamp - srcTimestamp;
                long seconds = delta / 1000;
                long deltaMinutes = delta / (60 * 1000);
                // 跟当前时间相差的小时数
                long deltaHour = delta / (60 * 60 * 1000);
                // 时间相差1分钟内
                if (deltaMinutes < 1) {
                    ret = StringUtils.getString(R.string.chat_conversation_just_now);
                    // 否则60分钟内，显示“m:分”的形式
                } else if (deltaMinutes < 60) {
                    ret = StringUtils.getString(R.string.chat_conversation_a_few_minutes_ago, deltaMinutes);
                } else if (deltaHour < 24) {
                    ret = StringUtils.getString(R.string.chat_conversation_a_few_hours_ago, deltaHour);
                } else if (deltaHour < 7 * 24) {
                    String[] weekday = {
                            StringUtils.getString(R.string.week_sunday),
                            StringUtils.getString(R.string.week_monday),
                            StringUtils.getString(R.string.week_tuesday),
                            StringUtils.getString(R.string.week_wednesday),
                            StringUtils.getString(R.string.week_thursday),
                            StringUtils.getString(R.string.week_friday),
                            StringUtils.getString(R.string.week_saturday)};

                    // 取出当前是星期几
                    String weedayDesc = weekday[gcSrc.get(GregorianCalendar.DAY_OF_WEEK) - 1];
                    ret = weedayDesc;
                } else {
                    // 否则直接显示完整日期时间
                    ret = getTimeString(srcDate, "MM/dd");
                }
            } else {
                ret = getTimeString(srcDate, "yyyy/MM/dd");
            }
        } catch (Exception e) {
            System.err.println("【DEBUG-getTimeStringAutoShort】计算出错：" + e.getMessage() + " 【NO】");
        }
        return ret;
    }

    /**
     * 仿照微信中的消息时间显示逻辑，将时间戳（单位：毫秒）转换为友好的显示格式.
     * <p>
     * 1）7天之内的日期显示逻辑是：今天、昨天(-1d)、前天(-2d)、星期？（只显示总计7天之内的星期数，即<=-4d）；<br>
     * 2）7天之外（即>7天）的逻辑：直接显示完整日期时间。
     *
     * @param srcDate         要处理的源日期时间对象
     * @param mustIncludeTime true表示输出的格式里一定会包含“时间:分钟”，否则不包含（参考微信，不包含时分的情况，用于首页“消息”中显示时）
     * @return 输出格式形如：“10:30”、“昨天 12:04”、“前天 20:51”、“星期二”、“2019/2/21 12:09”等形式
     * @author 即时通讯网 @link http://www.52im.net
     */
    public static String getTimeStringAutoShort2(Date srcDate, boolean mustIncludeTime) {
        String ret = "";
        try {
            GregorianCalendar gcCurrent = new GregorianCalendar();
            gcCurrent.setTime(new Date());
            int currentYear = gcCurrent.get(GregorianCalendar.YEAR);
            int currentMonth = gcCurrent.get(GregorianCalendar.MONTH) + 1;
            int currentDay = gcCurrent.get(GregorianCalendar.DAY_OF_MONTH);

            GregorianCalendar gcSrc = new GregorianCalendar();
            gcSrc.setTime(srcDate);
            int srcYear = gcSrc.get(GregorianCalendar.YEAR);
            int srcMonth = gcSrc.get(GregorianCalendar.MONTH) + 1;
            int srcDay = gcSrc.get(GregorianCalendar.DAY_OF_MONTH);

            // 要额外显示的时间分钟
            String timeExtraStr = (mustIncludeTime ? " " + getTimeString(srcDate, "HH:mm") : "");

            // 当年
            if (currentYear == srcYear) {
                long currentTimestamp = gcCurrent.getTimeInMillis();
                long srcTimestamp = gcSrc.getTimeInMillis();

                // 相差时间（单位：毫秒）
                long delta = (currentTimestamp - srcTimestamp);

                // 当天（月份和日期一致才是）
                if (currentMonth == srcMonth && currentDay == srcDay) {
                    // 时间相差60秒以内
                    if (delta < 60 * 1000)
                        ret = "刚刚";
                        // 否则当天其它时间段的，直接显示“时:分”的形式
                    else
                        ret = getTimeString(srcDate, "HH:mm");
                }
                // 当年 && 当天之外的时间（即昨天及以前的时间）
                else {
                    // 昨天（以“现在”的时候为基准-1天）
                    GregorianCalendar yesterdayDate = new GregorianCalendar();
                    yesterdayDate.add(GregorianCalendar.DAY_OF_MONTH, -1);

                    // 前天（以“现在”的时候为基准-2天）
                    GregorianCalendar beforeYesterdayDate = new GregorianCalendar();
                    beforeYesterdayDate.add(GregorianCalendar.DAY_OF_MONTH, -2);

                    // 用目标日期的“月”和“天”跟上方计算出来的“昨天”进行比较，是最为准确的（如果用时间戳差值的形式，是不准确的，
                    // 比如：现在时刻是2019年02月22日1:00、而srcDate是2019年02月21日23:00，这两者间只相差2小时，
                    // 直接用“delta/(3600 * 1000)” > 24小时来判断是否昨天，就完全是扯蛋的逻辑了）
                    if (srcMonth == (yesterdayDate.get(GregorianCalendar.MONTH) + 1)
                            && srcDay == yesterdayDate.get(GregorianCalendar.DAY_OF_MONTH)) {
                        ret = "昨天" + timeExtraStr;// -1d
                    }
                    // “前天”判断逻辑同上
                    else if (srcMonth == (beforeYesterdayDate.get(GregorianCalendar.MONTH) + 1)
                            && srcDay == beforeYesterdayDate.get(GregorianCalendar.DAY_OF_MONTH)) {
                        ret = "前天" + timeExtraStr;// -2d
                    } else {
                        // 跟当前时间相差的小时数
                        long deltaHour = (delta / (3600 * 1000));

                        // 如果小于 7*24小时就显示星期几
                        if (deltaHour < 7 * 24) {
                            String[] weekday = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

                            // 取出当前是星期几
                            String weedayDesc = weekday[gcSrc.get(GregorianCalendar.DAY_OF_WEEK) - 1];
                            ret = weedayDesc + timeExtraStr;
                        }
                        // 否则直接显示完整日期时间
                        else
                            ret = getTimeString(srcDate, "yyyy/M/d") + timeExtraStr;
                    }
                }
            } else
                ret = getTimeString(srcDate, "yyyy/M/d") + timeExtraStr;
        } catch (Exception e) {
            System.err.println("【DEBUG-getTimeStringAutoShort】计算出错：" + e.getMessage() + " 【NO】");
        }
        return ret;
    }

    /**
     * 返回指定pattern样的日期时间字符串。
     *
     * @param dt
     * @param pattern
     * @return 如果时间转换成功则返回结果，否则返回空字符串""
     * @author 即时通讯网 @link http://www.52im.net
     */
    public static String getTimeString(Date dt, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);//"yyyy-MM-dd HH:mm:ss"
            sdf.setTimeZone(TimeZone.getDefault());
            return sdf.format(dt);
        } catch (Exception e) {
            return "";
        }
    }

}
