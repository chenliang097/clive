package com.rongtuoyouxuan.chatlive.util;

import android.content.Context;

import java.io.File;
import java.io.IOException;

/**
 * Created by KingOX on 2016/2/18.
 * 清除WEBVIEW缓存
 */
public class WebViewCacheManager {

    public static void clearCache(Context context) {
        deleteDir(context.getCacheDir());
        String[] list = context.databaseList();
        if (list != null) {
            for (int i = 0; i < list.length; ++i) {
                if (list[i].contains("webview")) {
                    context.deleteDatabase(list[i]);

                }
            }
        }
    }

    public static void deleteDir(File dir) {
        if (dir == null)
            return;
        if (!dir.exists())
            return;
        if (!dir.isDirectory())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {

                try {
                    deleteDir(files[i].getCanonicalFile());
                    files[i].delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    files[i].delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
