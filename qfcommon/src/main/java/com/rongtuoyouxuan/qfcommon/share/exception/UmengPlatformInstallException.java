package com.rongtuoyouxuan.qfcommon.share.exception;

import com.umeng.socialize.bean.SHARE_MEDIA;
public class UmengPlatformInstallException extends Exception {
    private SHARE_MEDIA shareMedia;

    public UmengPlatformInstallException(SHARE_MEDIA shareMedia) {
        super(String.format("%s Not installed", shareMedia.name()));
        this.shareMedia = shareMedia;
    }

    public SHARE_MEDIA getShareMedia() {
        return shareMedia;
    }
}
