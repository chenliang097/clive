package com.rongtuoyouxuan.chatlive.crtlog.upload;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.rongtuoyouxuan.chatlive.crtlog.PLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

import static com.rongtuoyouxuan.chatlive.crtlog.upload.Utils.checkNotNull;

class UWriteHandler extends Handler {

    private static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file
    private static final int MAX_LOG_FILE_QUANTITY = 10; // 日志文件最大数量，用来控制文件总体大小
    private final static String LOG_FILE_NAME = "%s_log.csv";
    @NonNull private final String folder;
    private final int maxFileSize;
    private String TAG = "UWriteHandler";
    private File currentFile;

    public UWriteHandler(@NonNull Looper looper, @NonNull String folder, int maxFileSize) {
        super(checkNotNull(looper));

        this.folder = checkNotNull(folder);
        this.maxFileSize = maxFileSize;
        currentFile = generateNewFileName();
    }

    public static UWriteHandler defaultWriteHandler(@NonNull String folder) {
        HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
        ht.start();
        return new UWriteHandler(ht.getLooper(), folder, MAX_BYTES);
    }

    private File generateNewFileName() {
        return new File(folder, String.format(LOG_FILE_NAME, String.valueOf(System.currentTimeMillis())));
    }

    @SuppressWarnings("checkstyle:emptyblock")
    @Override
    public void handleMessage(@NonNull Message msg) {
        String content = (String) msg.obj;

        File[] files = new File(folder).listFiles();
        if (files != null && files.length >= MAX_LOG_FILE_QUANTITY) {//日志文件过多，不在收集日志
            PLog.d(TAG, "超过文件最大数量，不在记录日志");
            return;
        }

        FileWriter fileWriter = null;

        try {
            currentFile = getLogFile();
            fileWriter = new FileWriter(currentFile, true);
            writeLog(fileWriter, content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e1) { /* fail silently */ }
            }
        }
    }

    /**
     * This is always called on a single background thread.
     * Implementing classes must ONLY write to the fileWriter and nothing more.
     * The abstract class takes care of everything else including close the stream and catching IOException
     *
     * @param fileWriter an instance of FileWriter already initialised to the correct file
     */
    private void writeLog(@NonNull FileWriter fileWriter, @NonNull String content) throws IOException {
        checkNotNull(fileWriter);
        checkNotNull(content);

        fileWriter.append(content);
    }

    private File getLogFile() {
        checkNotNull(folder);

        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }

        if (currentFile.length() >= maxFileSize) {
            return generateNewFileName();
        }
        return currentFile;
    }

    List<String> getCanUploadLogFiles() {
        File[] files = new File(folder).listFiles();
        if (files == null || files.length == 0) return Collections.emptyList();

        List<String> list = new ArrayList<>();
        for (File file : files) {
            if (!currentFile.getName().equals(file.getName())) {
                if (file.length() > 0 && file.length() < MAX_BYTES * 2) {//过滤异常文件
                    list.add(file.getAbsolutePath());
                } else {
                    boolean isDeleteSuccess = file.delete();
                    if (!isDeleteSuccess) {
                        PLog.e("UWriteHandler", "File delete failed %s", file.getAbsolutePath());
                    }
                }
            }
        }
        return list;
    }
}