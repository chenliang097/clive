package com.rongtuoyouxuan.chatlive;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import com.rongtuoyouxuan.chatlive.log.PLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录本地，友盟负责统计发送至服务器.
 * 
 * @author zhoushengtao
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String STACK_TRACE = "STACK_TRACE";
    public static final String STACK_DATE = "STACK_DATE";
    public static final String CRASH_REPORTER_EXTENSION = ".cr";
    private static CrashHandler INSTANCE;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    private ActivityCallback mActivityCallback;
    /** 使用Properties来保存设备的信息和错误堆栈信息 */
    private Properties mDeviceCrashInfo = new Properties();

    private CrashHandler() {
        mActivityCallback = new ActivityCallback();
    }

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        ((Application) mContext.getApplicationContext()).registerActivityLifecycleCallbacks(mActivityCallback);
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        try {
            handleException(ex);
            mActivityCallback.finishAll();
            Intent launchIntentForPackage = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
            launchIntentForPackage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            launchIntentForPackage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(launchIntentForPackage);
        } finally {
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(thread, ex);
            } else {
                android.os.Process.killProcess(android.os.Process.myPid()); //杀死应用进程
                System.exit(0);
            }
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        // 保存错误报告文件
        String fileName = saveCrashInfoToFile(ex);
        PLog.e(null, "crash fileName = " + fileName);
        if (ex != null)
            ex.printStackTrace();

        return true;
    }

    /**
     * 获取错误报告文件名
     * 
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = ctx.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        return filesDir.list(filter);
    }

    /**
     * 保存错误信息到文件中
     * 
     * @param ex
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private String saveCrashInfoToFile(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String result = info.toString();
        printWriter.close();
        mDeviceCrashInfo.put(STACK_TRACE, result);
        mDeviceCrashInfo.put(STACK_DATE, new SimpleDateFormat("yyyyMMddHHMMss").format(new Date()));
        try {
            String crashDir = getDiskCacheDir(mContext.getApplicationContext(), "crash").getAbsolutePath() + File.separator;
            File dir = new File(crashDir);
            if(!dir.isDirectory()) {
                dir.mkdirs();
            }
            String fileName = crashDir + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt";
            File file = new File(fileName);
            OutputStream outputStream = new FileOutputStream(file);
            mDeviceCrashInfo.store(outputStream, "");
            outputStream.flush();
            outputStream.close();
            return fileName;
        } catch (Exception e) {

        }
        return null;
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private static final class ActivityCallback implements Application.ActivityLifecycleCallbacks {

        private final List<Activity> mActivities = new ArrayList<>();

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            mActivities.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivities.remove(activity);
        }

        synchronized void finishAll() {
            for (Activity activity : mActivities) {
                if (activity != null) {
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }

}
