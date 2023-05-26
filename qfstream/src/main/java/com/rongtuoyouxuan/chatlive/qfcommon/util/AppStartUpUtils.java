package com.rongtuoyouxuan.chatlive.qfcommon.util;

import com.blankj.utilcode.util.SPUtils;
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * APP启动判断工具类
 *
 * @author llw
 */
public class AppStartUpUtils {

    /**
     * App首次启动
     */
    public static final String APP_FIRST_START = "appFirstStart";
    /**
     * 今日启动APP的时间
     */
    public static final String START_UP_APP_TIME = "startAppTime";

    /**
     * 判断是否是首次启动
     *
     * @return
     */
    public static boolean isFirstStartApp() {
        Boolean isFirst = SPUtils.getInstance().getBoolean(APP_FIRST_START, true);
        // 第一次
        if (isFirst) {
            SPUtils.getInstance().put(APP_FIRST_START, false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是今日首次启动APP
     *
     * @return
     */
    public static boolean isTodayFirstStartApp() {
        String userId = DataBus.instance().getUid();
        String saveDate = SPUtils.getInstance().getString(START_UP_APP_TIME + "_" + userId, "2020-08-27");
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //第一次打开
        if (!saveDate.equals(todayDate)) {
            SPUtils.getInstance().put(START_UP_APP_TIME + "_" + userId, todayDate);
            return true;
        } else {
            return false;
        }
    }
}