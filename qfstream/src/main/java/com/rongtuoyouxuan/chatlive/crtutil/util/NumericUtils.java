package com.rongtuoyouxuan.chatlive.crtutil.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by KingOX on 2015/11/20.
 */
public class NumericUtils {
    public static String getLivePeople(String livetext) {
        //将数字转换为以万为单位
        try {
            double livenum = Double.parseDouble(livetext);
            if(livenum >= 10000 * 10000) {
                double format_num = livenum / (10000 * 10000);
                BigDecimal bd = new BigDecimal(String.valueOf(format_num));
                bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                return removeZero(bd.toString()) + "亿";
            } else if (livenum >= 10000) {
                double format_num = livenum / 10000;
                BigDecimal bd = new BigDecimal(String.valueOf(format_num));
                bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                return removeZero(bd.toString()) + "万";
            } else {
                return String.valueOf((int)livenum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String removeZero(String livetext) {
        if(livetext == null)
            return livetext;

        try {
            if(livetext.endsWith(".0")) {
                return livetext.substring(0, livetext.length() - 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livetext;
    }

    public static String getNumText(String strNum) {
        if(strNum.isEmpty()){
            Random random = new Random();
            int num = random.nextInt(10)+1;
            return String.format("%d", num);
        } else {
            return strNum;
        }
    }

    // 格式化身高
    public static String getHeight(Long bamboosNum) {
        float bamboosFloat = 0.0f;
        String unit = "m";
        if (bamboosNum <= 1000 * 1000) {
            bamboosFloat = bamboosNum / (1000.0f);
            unit = "mm";
        } else if (bamboosNum < 1000 * 1000 * 1000) {
            bamboosFloat = bamboosNum / (1000.0f * 1000);
            unit = "m";
        } else {
            bamboosFloat = bamboosNum / (1000.0f * 1000 * 1000);
            unit = "km";
        }
        bamboosFloat = (float) (Math.round(bamboosFloat * 100)) / 100;
        return (bamboosFloat + unit);
    }

    /**
     * 纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
