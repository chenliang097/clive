package com.rongtuoyouxuan.chatlive.download;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SimpleDownload {
    private HandlerThread mHandlerThread;
    private Handler mThreadHandler;
    private Handler mMainHandler;
    public SimpleDownload(Context context) {
        mHandlerThread = new HandlerThread("SimpleDownload");
    }

    public static String getFileNameWithoutExtFromURL(String url) {
        String filename = getFileNameFromURL(url);
        if (TextUtils.isEmpty(filename)) {
            return filename;
        }
        int lastpos = filename.lastIndexOf('.');
        if (lastpos == -1) {
            lastpos = filename.length();
        }
        return filename.substring(0, lastpos);
    }

    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        }
        catch(MalformedURLException e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        // find end index for ?
        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        // find end index for #
        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        // calculate the end index
        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }

    private static int downloadFile(String urlStr, String path, String fileName) {
        String tempFileName = "_"+fileName;
        InputStream inputStream = null;
        try {
            deleteIfExists(path + tempFileName);
            inputStream = getInputStreamFromUrl(urlStr);
            File resultFile = write2SDFromInput(path, tempFileName, inputStream);
            if (resultFile == null) {
                return -1;
            }
            deleteIfExists(path+fileName);
            resultFile.renameTo(new File(path+fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * @param urlStr url
     * @return 根据URL得到输入流
     * @throws MalformedURLException
     * @throws IOException
     */
    private static InputStream getInputStreamFromUrl(String urlStr)
            throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        return urlConn.getInputStream();
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static void deleteIfExists(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public static File write2SDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = creatSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int len = -1;
            while (-1 != (len = input.read(buffer))) {
                output.write(buffer, 0, len);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public static File creatSDFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public static File creatSDDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdirs();
        return dir;
    }

    static public boolean deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    public void start() {
        mHandlerThread.start();
        mThreadHandler = new Handler(mHandlerThread.getLooper());
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public void download(final String url, final String path, final String fileName, final DownloadCallback callback) {
        mThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    if (!callback.onBefore()) {
                        return;
                    }
                }
                String realFilename = TextUtils.isEmpty(fileName) ? getFileNameFromURL(url) : fileName;
                downloadFile(url, path, realFilename);

                if (callback != null) {
                    callback.onAfter(path+realFilename);
                }
                if (callback != null) {
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onComplete();
                        }
                    });
                }
            }
        });
    }

    public static class DownloadCallback {
        public boolean onBefore() {
            return true;
        }
        public void onAfter(String filePath) {

        }
        public void onComplete() {

        }
    }
}
