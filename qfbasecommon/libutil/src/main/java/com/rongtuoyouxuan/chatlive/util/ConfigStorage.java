package com.rongtuoyouxuan.chatlive.util;

/**
 * 存储运行时的配置.
 * <p>
 * Created by zhangdaoqiang on 2/28/17.
 */

public class ConfigStorage {
    public final static int LIVE_STREAM_NULL = 0;
    public final static int LIVE_STREAM_CAMERA = 1;
    public final static int LIVE_STREAM_SCREEN = 2;
    private static ConfigStorage instance;
    private int mLiveStreamState = 0;
    private String mShareInfoText = "";

    private ConfigStorage() {

    }

    public static ConfigStorage instance() {
        if (null == instance) {
            synchronized (ConfigStorage.class) {
                if (null == instance) {
                    instance = new ConfigStorage();
                }
            }
        }
        return instance;
    }

    public int getLiveStreamState() {
        return mLiveStreamState;
    }

    public void setLiveStreamState(int s) {
        mLiveStreamState = s;
    }

    public String getShareInfoText() {
        return mShareInfoText;
    }

    public void setShareInfoText(String text) {
        mShareInfoText = text;
    }

}
