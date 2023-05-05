package com.rongtuoyouxuan.qfcommon.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rongtuoyouxuan.chatlive.util.LaToastUtil;
import com.rongtuoyouxuan.qfcommon.R;
import com.rongtuoyouxuan.qfcommon.share.RxUmengSocial;
import com.rongtuoyouxuan.qfcommon.share.UmengPlatformInfo;
import com.rongtuoyouxuan.qfcommon.share.exception.UmengPlatformCancelException;
import com.rongtuoyouxuan.qfcommon.share.exception.UmengPlatformInstallException;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Describe: 分享
 *
 * @author Ning
 * @date 2019/6/3
 */
public class ShareBottomDialog extends Dialog {

    public ShareBottomDialog(Context context) {
        super(context);
    }

    public ShareBottomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder implements View.OnClickListener {
        private Context context;
        private boolean isCancle = true;
        private boolean isOnKeyCancle = false;
        public EventListener mEventListener;
        private long time = System.currentTimeMillis();

        private boolean isFromStream = false;

        public Builder(Context context) {
            this.context = context;
        }


        public Builder setIsCancle(boolean isCancle) {
            this.isCancle = isCancle;
            return this;
        }

        public Builder setOnKeyCancle(boolean isOnKeyCancle) {
            this.isOnKeyCancle = !isOnKeyCancle;
            return this;
        }

        //                UMWeb web;
        UMImage image;
        String title;
        String info;
        String sUrl;
        String imageUrl;
        String anchorId;

        public ShareBottomDialog create(String url, String shareContext, String shareUrl,
                                        String shareTitle) {
            return create(false, url, shareContext, shareUrl, shareTitle);
        }

        public ShareBottomDialog create(boolean isFromStream, String url, String shareContent, String shareUrl,
                                        String shareTitle) {
            this.isFromStream = isFromStream;
            this.title = shareTitle;
            this.info = shareContent;
            this.sUrl = shareUrl;
            this.imageUrl = url;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final ShareBottomDialog dialog = new ShareBottomDialog(context, R.style.pl_libshare_BottomDialog);
            View layout = inflater.inflate(R.layout.rt_common_dialog_bottom_share, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setCanceledOnTouchOutside(isCancle);
            dialog.setCancelable(isCancle);
            dialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface arg0, int keyCode, KeyEvent arg2) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return isOnKeyCancle;
                    } else {
                        return false;
                    }
                }
            });
            LinearLayout shareWeChat = layout.findViewById(R.id.iv_share_wechat);
            shareWeChat.setOnClickListener(this);
            LinearLayout shareMoments = layout.findViewById(R.id.iv_share_friends);
            shareMoments.setOnClickListener(this);
            LinearLayout shareQQ = layout.findViewById(R.id.iv_share_qq);
            shareQQ.setOnClickListener(this);
            LinearLayout shareWeibo = layout.findViewById(R.id.iv_share_weibo);
            shareWeibo.setOnClickListener(this);
            //分享按钮
            //分享图片
            if (TextUtils.isEmpty(url)) {
                image = new UMImage(context, R.drawable.loading);
            } else {
                image = new UMImage(context, url);//网络图片
            }
            //分享内容
            if (TextUtils.isEmpty(info)) {
                info = context.getResources().getString(R.string.app_name);
            }
            //分享标题
            if (TextUtils.isEmpty(title)) {
                title = context.getResources().getString(R.string.app_name);
            }
            //分享的链接
//            if (TextUtils.isEmpty(sUrl)) {
//                sUrl = EnvUtils.API.getH5AppNormalShare();
//            }
//            web = new UMWeb(shareUrl);
//            web.setTitle(shareTitle);//标题
//            web.setThumb(image);  //缩略图
//            web.setDescription(shareContext);//描述
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);

            /*
             * 将对话框的大小按屏幕大小的百分比设置
             */
            WindowManager m = dialogWindow.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
            p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.95
            dialogWindow.setAttributes(p);
            dialog.setContentView(layout);
            return dialog;
        }

        public ShareBottomDialog.Builder setAnchorId(String anchorId){
            this.anchorId = anchorId;
            return this;
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (System.currentTimeMillis() - time < 1000) {
                return;
            }
            time = System.currentTimeMillis();
            if (i == R.id.iv_share_wechat) {

                clickPlatformShare(SHARE_MEDIA.WEIXIN, title, info, sUrl);
                if (mEventListener != null) {
                    mEventListener.onSuccess(getType(SHARE_MEDIA.WEIXIN));
                }
            } else if (i == R.id.iv_share_friends) {

                clickPlatformShare(SHARE_MEDIA.WEIXIN_CIRCLE, title, info, sUrl);
                if (mEventListener != null) {
                    mEventListener.onSuccess(getType(SHARE_MEDIA.WEIXIN_CIRCLE));
                }
            } else if (i == R.id.iv_share_qq) {

                clickPlatformShare(SHARE_MEDIA.QQ, title, info, sUrl);

            } else if (i == R.id.iv_share_weibo) {

                clickPlatformShare(SHARE_MEDIA.SINA, title, info, sUrl);

            }

        }

        /**
         * 分享
         *
         * @param shareMedia
         */
        private void clickPlatformShare(final SHARE_MEDIA shareMedia, String shareTitle, String shareContext, String shareURL) {
            if (!RxUmengSocial.get().hasPermissions((Activity) context)) {
                return;
            }
            //检查平台是否可用
            if (!RxUmengSocial.get().isPlatformAvailable((Activity) context, shareMedia)) {
                LaToastUtil.showShort(context.getResources().getString(R.string.share_no_install) + shareMedia);
                return;
            }
            RxUmengSocial.get()
                    .setCheckPlatform(false)//前面手动检查了 把自动检查设为false
                    .setShareMedia(shareMedia)
                    .shareUrl((Activity) context, shareTitle, shareContext, image, shareURL)
                    .subscribe(new Observer<SHARE_MEDIA>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(SHARE_MEDIA result) {
                            //检查平台是否有成功事件
                            if (UmengPlatformInfo.isHasShareSuccessEvent(result)) {
                                LaToastUtil.showShort(context.getResources().getString(R.string.share_share_suc));
                                if (mEventListener != null) {
                                    mEventListener.onSuccess(getType(shareMedia));
                                }
                            }
                            if (shareMedia.equals(SHARE_MEDIA.WEIXIN) || shareMedia.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {
                                LaToastUtil.showShort(context.getResources().getString(R.string.finish));
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            if (e instanceof UmengPlatformInstallException) {
                                LaToastUtil.showShort(context.getResources().getString(R.string.share_no_install) + ((UmengPlatformInstallException) e).getShareMedia());
                            } else if (e instanceof UmengPlatformCancelException) {
                                LaToastUtil.showShort(context.getResources().getString(R.string.share_share_cancel));
                            } else {
                                LaToastUtil.showShort(context.getResources().getString(R.string.share_share_fail));
                            }
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }

        public ShareBottomDialog.Builder setEventListener(EventListener eventListener) {
            mEventListener = eventListener;
            return this;
        }
        public interface EventListener{
            void onSuccess(String type);
            void onError();
        }


        private String getType(SHARE_MEDIA shareMedia){
            String type = "";
            switch (shareMedia) {
                case WEIXIN:
                    type = "wx";
                    break;
                case WEIXIN_CIRCLE:
                    type = "pyq";
                    break;
                case QQ:
                    type = "qq";
                    break;
                case SINA:
                    type = "wb";
                    break;
            }
            return type;
        }
    }

    public void onActivityResult(Context context,int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }

}
