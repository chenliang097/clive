package com.rongtuoyouxuan.chatlive.crtdatabus.log;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.rongtuoyouxuan.chatlive.crtlog.PLog;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class UploadLog {
    private final Queue<String> tasks = new LinkedList<>();
    private final Queue<String> executeTasks = new LinkedList<>();
    private String TAG = UploadLog.class.getSimpleName();
    private Context context;
    private int uploadLogRetryCount = 0;

    public UploadLog(Context context) {
        this.context = context;
    }

    public void uploadLog(List<String> logPaths) {
        if (logPaths == null || logPaths.isEmpty()) {
            PLog.d(TAG, "log paths is empty");
            return;
        }

        Collections.sort(logPaths, new Comparator<String>() {
            @Override
            public int compare(String file1, String file2) {
                try {
                    long f1LastMod = new File(file1).lastModified();
                    long f2LastMod = new File(file2).lastModified();
                    return Long.compare(f1LastMod, f2LastMod);
                } catch (Exception e) {
                    PLog.e(e);
                }
                return 0;
            }
        });
        PLog.d(TAG, "need upload log: %s", logPaths.toString());
        tasks.addAll(logPaths);
        executeTasks.addAll(logPaths);
        doUploadLog();
    }

    private void retryUploadLog() {
        PLog.d(TAG, "do retryUploadLog");
        executeTasks.clear();
        executeTasks.addAll(tasks);
        doUploadLog();
    }

    private void doUploadLog() {
        final String filePath = executeTasks.poll();
        if (filePath == null || TextUtils.isEmpty(filePath)) {
            doUploadLog();
            return;
        }
        PLog.d(TAG, "doUploadLog file: %s", filePath);
//        OssFileUploadManager.getInstance().uploadLogFile(context, DataBus.instance().getUid() + "_bl", filePath, new FileUploadListener() {
//            @Override
//            public void onProgress(long currentSize, long totalSize, String tag) {
//            }
//
//            @Override
//            public void onSuccess(String relativePath, String fullPath, String tag) {
//                PLog.d(TAG, " success %s", fullPath);
//                deleteLogFile(filePath);
//                executeTasks.remove(filePath);
//                tasks.remove(filePath);
//                continueUploadIfNeed();
//            }
//
//            @Override
//            public void onFail(String errCode, String msg, String tag) {
//                PLog.d(TAG, " onFail %s", filePath);
//                executeTasks.remove(filePath);
//                continueUploadIfNeed();
//            }
//        });
    }

    private void deleteLogFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean isDelSuccess = file.delete();
            if (!isDelSuccess) {
                PLog.d(TAG, "File delete failed %s", file.getAbsolutePath());
            }
        }
    }

    private void continueUploadIfNeed() {
        if (executeTasks.size() > 0) {
            doUploadLog();
            PLog.d(TAG, " doUploadLog");
        } else if (tasks.size() > 0 && uploadLogRetryCount < 3) {
            PLog.d(TAG, " retryUploadLog");
            uploadLogRetryCount++;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    retryUploadLog();
                }
            }, 5000);
        }
    }
}
