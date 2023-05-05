package com.rongtuoyouxuan.qfcommon.share.exception;

import com.umeng.socialize.bean.SHARE_MEDIA;

public class UmengPlatformCancelException extends Exception{
    private SHARE_MEDIA shareMedia;

    public UmengPlatformCancelException(SHARE_MEDIA shareMedia) {
        super(String.format("%s cancel", shareMedia.name()));
        this.shareMedia = shareMedia;
    }

    public SHARE_MEDIA getShareMedia() {
        return shareMedia;
    }
}
