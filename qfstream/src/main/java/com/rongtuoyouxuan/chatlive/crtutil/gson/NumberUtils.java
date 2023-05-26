package com.rongtuoyouxuan.chatlive.crtutil.gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jinqinglin on 2018/4/27.
 */

public class NumberUtils {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("-?[0-9]+(\\\\.[0-9]+)?");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 数字过长转k
     * @param num
     * @return
     */
    public static String numberFormat(int num) {
        StringBuilder sb = new StringBuilder();
        if (num < 0) {
            return "0";
        } else if (num <= 9999) {
            return num+"";
        } else {
            if (num <= 99999999) {
                double d1 = num * 1.0 / 1000;
                String string = String.valueOf(d1);
                int leng=string.indexOf(".");
                String result=string.substring(0,leng+2);
                return result+"k";
//                sb.append("k");
//        }else {
//            double d1 = num * 1.0/10000;
//            sb.append(NumberUtils.saveDecimal(d1,1));
//            sb.append("万");
            }
        }
        return 0+"";
    }
}
