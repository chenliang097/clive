package com.rongtuoyouxuan.chatlive.crtoss;

/**
 * create by Administrator on 2019/6/4
 */
public interface UploadListener {

    void onProgress(float fraction, String tag);

    void onSuccess(String relativePath, String fullPath, String tag);

    void onFail(String msg, String tag);
}
