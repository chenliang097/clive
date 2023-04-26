package com.rongtuoyouxuan.chatlive.log.upload;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;

import com.rongtuoyouxuan.chatlive.log.OSUtils;
import com.rongtuoyouxuan.chatlive.log.PLog;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.DiskLogStrategy;
import com.orhanobut.logger.FormatStrategy;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ULog {
    private static boolean mIsDebugMode = false;
    private static AtomicBoolean isInit = new AtomicBoolean(false);
    private static UWriteHandler writeHandler;
    private ULog() {
    }

    public static boolean isDebugMode() {
        return mIsDebugMode;
    }

    public static String getLogsPath(Context context) {
        return context.getExternalFilesDir("logs").getAbsolutePath();
    }

    public static String getLogsPath(String product) {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separatorChar + product + File.separatorChar + "logs";
    }

    public synchronized static void init(Context context, final String channel, String product) {
        if (isInit.get()) return;
        mIsDebugMode = context.getApplicationInfo() != null &&
                (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;

        writeHandler = UWriteHandler.defaultWriteHandler(OSUtils.isRLater() ? getLogsPath(context) : getLogsPath(product));
        FormatStrategy formatStrategy = UCsvFormatStrategy.newBuilder()
                .product(product)
                .tag(channel)
                .logStrategy(new DiskLogStrategy(writeHandler))
                .build();
        ULogger.addLogAdapter(new DiskLogAdapter(formatStrategy));
        isInit.set(true);
    }

    public static List<String> getUploadLogFiles() {
        if (writeHandler != null) {
            return writeHandler.getCanUploadLogFiles();
        }
        return Collections.emptyList();
    }

    public static void d(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        ULogger.t(tag).d(message, args);
        PLog.d(tag, message, args);
    }

    public static void e(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        ULogger.t(tag).e(null, message, args);
        PLog.e(tag, message, args);
    }

    public static void e(Throwable e) {
        ULogger.e(e, "");
        PLog.e(e);
    }

    public static void e(@NonNull String tag, Throwable e) {
        ULogger.t(tag).e(e, "");
        PLog.e(tag, e);
    }

    public static void i(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        ULogger.t(tag).i(message, args);
        PLog.i(tag, message, args);
    }

    public static void v(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        ULogger.t(tag).v(message, args);
        PLog.v(tag, message, args);
    }

    public static void w(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        ULogger.t(tag).w(message, args);
        PLog.w(tag, message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull String tag, @NonNull String message, @Nullable Object... args) {
        ULogger.t(tag).wtf(message, args);
        PLog.wtf(tag, message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(@NonNull String tag, @Nullable String json) {
        ULogger.t(tag).json(json);
        PLog.json(tag, json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(@NonNull String tag, @Nullable String xml) {
        ULogger.t(tag).xml(xml);
        PLog.xml(tag, xml);
    }
}
