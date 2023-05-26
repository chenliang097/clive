package com.rongtuoyouxuan.chatlive.qfcommon.webview.base;

/*
 *Create by {Mr秦} on 2022/8/10
 */
public class ErrorLayoutEntity {
    private int layoutRes = com.just.agentweb.R.layout.agentweb_error_page;
    private int reloadId;

    public void setLayoutRes(int layoutRes) {
        this.layoutRes = layoutRes;
        if (layoutRes <= 0) {
            layoutRes = -1;
        }
    }

    public void setReloadId(int reloadId) {
        this.reloadId = reloadId;
        if (reloadId <= 0) {
            reloadId = -1;
        }
    }

    public int getLayoutRes() {
        return layoutRes;
    }

    public int getReloadId() {
        return reloadId;
    }
}