package com.rongtuoyouxuan.qfcommon.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.rongtuoyouxuan.qfcommon.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class UrlDrawableLoader {

    private Context mContext;
    private TextView mTextView;
    private int mImageHeight;
    private String mUrl;

    public UrlDrawableLoader(Context mContext, TextView mTextView, int mImageHeight, String url) {
        this.mContext = mContext;
        this.mTextView = mTextView;
        this.mImageHeight = mImageHeight;
        this.mUrl = url;
    }

    public UrlImageCallBack mUrlImageCallBack;

    public BitmapDrawable getDrawable() {
        final UrlDrawable urlDrawable = new UrlDrawable();
        Glide.with(mContext).asBitmap().load(mUrl).placeholder(R.drawable.icon_gift_default).error(R.drawable.icon_gift_default).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
                Bitmap resource = ConvertUtils.drawable2Bitmap(placeholder);
                float ratio = (float) resource.getWidth() / resource.getHeight();
                BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), resource);
                bitmapDrawable.setBounds(0, 0, (int) (mImageHeight * ratio), mImageHeight);
                //设置图片宽、高（这里传入的mImageSize为字体大小，所以，设置的高为字体大小，宽为按宽高比缩放）
                urlDrawable.setBounds(0, 0, (int) (mImageHeight * ratio), mImageHeight);
                urlDrawable.bitmapDrawable = bitmapDrawable;
            }

            @Override
            public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                // 设置
                float ratio = (float) resource.getWidth() / resource.getHeight();
                BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), resource);
                bitmapDrawable.setBounds(0, 0, (int) (mImageHeight * ratio), mImageHeight);
                //设置图片宽、高（这里传入的mImageSize为字体大小，所以，设置的高为字体大小，宽为按宽高比缩放）
                urlDrawable.setBounds(0, 0, (int) (mImageHeight * ratio), mImageHeight);
                urlDrawable.bitmapDrawable = bitmapDrawable;
//                mUrlImageCallBack.callBack(bitmapDrawable);
                //两次调用invalidate才会在异步加载完图片后，刷新图文混排TextView，显示出图片o
                bitmapDrawable.invalidateSelf();
                mTextView.invalidate();
            }
        });
        return urlDrawable;
    }

    public void setUrlImageCallBack(UrlImageCallBack mUrlImageCallBack) {
        this.mUrlImageCallBack = mUrlImageCallBack;
    }

    public interface UrlImageCallBack {
        void callBack(BitmapDrawable bitmapDrawable);
    }
}
