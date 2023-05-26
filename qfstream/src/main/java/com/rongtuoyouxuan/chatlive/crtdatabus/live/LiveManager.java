package com.rongtuoyouxuan.chatlive.crtdatabus.live;

public class LiveManager {

    private static final int LIVESTATUS_LIVING = 1;
    private static final int LIVESTATUS_NULL = 0;

    private int mLiveStatus = LIVESTATUS_NULL;

    private LiveManager() {
    }

    public static LiveManager instance() {
        return Holder.liveManager;
    }

    public void setLiveStatusNull() {
        mLiveStatus = LIVESTATUS_NULL;
    }

    public void setLivestatusLiving() {
        mLiveStatus = LIVESTATUS_LIVING;
    }

    public boolean isLiving() {
        return mLiveStatus == LIVESTATUS_LIVING;
    }

    private static class Holder {
        public static LiveManager liveManager = new LiveManager();
    }
}
