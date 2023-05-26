package com.rongtuoyouxuan.chatlive.qfcommon.share;

import com.umeng.socialize.bean.SHARE_MEDIA;

public interface OnSystemSocialListener {

    void onError(SHARE_MEDIA share_media, Throwable throwable);
}
