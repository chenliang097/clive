package utils;


import android.text.TextUtils;

import com.rongtuoyouxuan.chatlive.log.PLog;

/**
 * @author zhoushengtao
 */
public class LogUtils {

    public final static boolean DEBUG = false;

    public static String customTagPrefix = "";
    public static boolean allowD = DEBUG;
    public static boolean allowE = DEBUG;
    public static boolean allowI = DEBUG;
    public static boolean allowV = DEBUG;
    public static boolean allowW = DEBUG;
    public static boolean allowWtf = DEBUG;
    private LogUtils() {
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s[%s, %d]";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if (!allowD) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.d(tag, content);
    }

    public static void d(String content, Throwable tr) {
        if (!allowD) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.d(tag, content + tr.getMessage());
    }

    public static void e(String content) {
        if (!allowE) {
            return;
        }
        if (content == null) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.e(tag, content);
    }

    public static void e(Exception content) {
        if (!allowE)
            return;
        if (content == null) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        Throwable cause = content.getCause();
        if (cause == null) {
            if (content.getMessage() != null) {
                PLog.e(tag, content.getMessage());
            } else {
                PLog.e(tag, "" + content);
            }
        } else {
            if (content.getMessage() != null) {
                e(content.getMessage(), cause);
            } else {
                e("msearch", cause);
            }
        }

    }

    public static void e(String content, Throwable tr) {
        if (!allowE) {
            return;
        }
        if (tr == null) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.e(tag, content + tr.getMessage());
    }

    public static void i(String content) {
        if (!allowI) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.i(tag, content);
    }

    public static void i(String content, Throwable tr) {
        if (!allowI) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.i(tag, content + tr.getMessage());
    }

    public static void v(String content) {
        if (!allowI) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.v(tag, content);
    }

    public static void v(String content, Throwable tr) {
        if (!allowV) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.v(tag, content + tr.getMessage());
    }

    public static void w(String content) {
        if (!allowV) {
            return;
        }
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.w(tag, content);
    }

    public static void w(String content, Throwable tr) {
        if (!allowW)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.w(tag, content + tr.getMessage());
    }

    public static void w(Throwable tr) {
        if (!allowW)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.w(tag, tr.getMessage());
    }

    public static void wtf(String content) {
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.wtf(tag, content);
    }

    public static void wtf(String content, Throwable tr) {
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.wtf(tag, content + tr.getMessage());
    }

    public static void wtf(Throwable tr) {
        if (!allowWtf)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller);
        PLog.wtf(tag, tr.getMessage());
    }

    public static StackTraceElement getCurrentStackTraceElement() {
        return Thread.currentThread().getStackTrace()[3];
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

}
