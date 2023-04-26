package com.rongtuoyouxuan.chatlive.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 说明：字符串工具类
 * <p/>
 * 作者：zhang
 * <p/>
 * 时间：2018/09/21 10:35
 * <p/>
 * 版本：verson 1.0
 */

public final class StringUtils {

    private final static String emailer = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    private final static String phoner = "1\\d{10}$";

    /**
     * 说明：判断给定字符串是否空白串 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(CharSequence input) {
        return !isEmpty(input);
    }

    /**
     * 说明：判断多个定字符串是否空
     * 全为null或"",返回true
     * 否则返回false
     */
    public static boolean isEmpty(CharSequence... input) {
        boolean flag = true;
        if (input != null) {
            for (CharSequence c : input) {
                if (!isEmpty(c)) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public static int getCharacterPosition(String string) {
        //这里是获取"/"符号的位置
        Matcher slashMatcher = Pattern.compile("/").matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
            //当"/"符号第k次出现的位置

            int N = 3;

            if (mIdx == N) {
                break;
            }
        }
        return slashMatcher.start();
    }

    /**
     * 说明：判断两个字符串是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isEquals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 说明：比较字符串是否相等（忽视大小写）
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isEqualsIgnoreCase(String a, String b) {
        if (a == b) return true;
        if (a != null && b != null) {
            return a.equalsIgnoreCase(b);
        }
        return false;
    }

    /**
     * 说明：判定是否符合
     *
     * @param pattern 正则表达式
     * @param str     验证的字符串
     * @return
     */
    public static boolean matches(String pattern, CharSequence str) {
        if (isEmpty(str) || isEmpty(str)) {
            return false;
        }
        Pattern p = Pattern.compile(pattern);
        return p.matcher(str).matches();
    }

    /**
     * 说明：判断是不是一个合法的电子邮件地址（自定义规则）
     *
     * @param pattern
     * @param email
     * @return
     */
    public static boolean isEmail(String pattern, CharSequence email) {
        return matches(pattern, email);
    }

    /**
     * 说明：判断是不是一个合法的电子邮件地址
     */
    public static boolean isEmail(CharSequence email) {
        return isEmail(emailer, email);
    }

    /**
     * 说明：判断是不是一个合法的手机号码
     */
    public static boolean isPhone(CharSequence phoneNum) {
        return isPhone(phoner, phoneNum);
    }

    /**
     * 说明：判断是不是一个合法的手机号码(自定义规则)
     */
    public static boolean isPhone(String pattern, CharSequence phoneNum) {
        return matches(pattern, phoneNum);
    }

    /**
     * 说明：过滤字符串中Emoji表情
     *
     * @param src 源字符串
     * @return 过滤后字符串
     */
    public static String emojiFilter(String src) {
        if (isEmpty(src)) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < src.codePointCount(0, src.length()); i++) {
                int point = src.codePointAt(i);
                if (!isEmojiCharacter(point)) {
                    sb.append((char) point);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 说明：判读字符是否为Emoji表情
     *
     * @param codePoint
     * @return false:不是，true:是
     */
    public static boolean isEmojiCharacter(int codePoint) {
        return (codePoint >= 0x2600 && codePoint <= 0x27BF)
                || codePoint == 0x303D
                || codePoint == 0x2049
                || codePoint == 0x203C
                || (codePoint >= 0x3200 && codePoint <= 0x32FF)
                || (codePoint >= 0x2100 && codePoint <= 0x214F)
                || (codePoint >= 0x2B00 && codePoint <= 0x23FF)
                || (codePoint >= 0x2900 && codePoint <= 0x297F)
                || codePoint >= 0x10000;
    }

    /**
     * 说明：生成32为包含数字和字母的唯一UUID字符串
     *
     * @return 32位长度
     */
    public static String UUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 说明：Encode编码
     *
     * @param sign
     * @return
     */
    public static String utfEncode(String sign) {
        String result = "";
        try {
            if (!isEmpty(sign)) {
                result = URLEncoder.encode(sign, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
//            MyLog.e("Encode编码", e);
        }
        return result;
    }

    /**
     * 用正则去判断是不是一串数
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    /**
     * 支付宝 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }
    /**
     * 对支付参数信息进行签名
     *
     * @param map
     *            待签名授权信息
     *
     * @return
     */
   /* public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);
        String encodedSign = "";

        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sign=" + encodedSign;
    }*/
}

