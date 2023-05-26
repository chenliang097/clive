package com.rongtuoyouxuan.chatlive.qfcommon.share;

import com.rongtuoyouxuan.chatlive.qfcommon.share.exception.UmengPlatformCancelException;
import com.rongtuoyouxuan.chatlive.qfcommon.share.exception.UmengPlatformCancelException;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import io.reactivex.ObservableEmitter;

public class UmengShareEmitterListener implements UMShareListener {
    private ObservableEmitter<SHARE_MEDIA> mEmitter;

    public UmengShareEmitterListener(ObservableEmitter<SHARE_MEDIA> emitter) {
        this.mEmitter = emitter;
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        mEmitter.onNext(share_media);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        mEmitter.onError(throwable);
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        mEmitter.onError(new UmengPlatformCancelException(share_media));
    }
}
