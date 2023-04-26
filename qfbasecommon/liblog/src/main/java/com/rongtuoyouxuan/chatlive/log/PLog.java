package com.rongtuoyouxuan.chatlive.log;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

@Deprecated
public final class PLog {
    private static boolean mIsDebugMode = false;

    private PLog() {
    }

    public static boolean isDebugMode() {
        return mIsDebugMode;
    }
    public static void init(Context context, final String channel) {
        mIsDebugMode = context.getApplicationInfo() != null &&
                (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

        boolean isLocaltest = !TextUtils.isEmpty(channel) && channel.endsWith("qa");

        //只有debug或者localtest渠道能输出log
        if (mIsDebugMode || isLocaltest) {
            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(false)
                    .methodCount(0)
                    .tag("BLLOG")
                    .build();
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

            //fixme android 11 不在本地记录日志。logger 库默认在sd卡根目录下写入文件。
            if (!OSUtils.isRLater()) {
                addFileLog();
            }
        } else {
            Logger.clearLogAdapters();
        }
    }

    private static void addFileLog() {
        FormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                .tag("BLLOG")
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
    }

    public static void d(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).d(message, args);
    }

    public static void e(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).e(null, message, args);
    }

    public static void e(Throwable e) {
        Logger.e(e, "");
    }

    public static void e(@NonNull String tag, Throwable e) {
        Logger.t(tag).e(e, "");
    }

    public static void i(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).i(message, args);
    }

    public static void v(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).v(message, args);
    }

    public static void w(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        Logger.t(tag).wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(@NonNull String tag, @Nullable String json) {
        Logger.t(tag).json(json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(@NonNull String tag, @Nullable String xml) {
        Logger.t(tag).xml(xml);
    }
}