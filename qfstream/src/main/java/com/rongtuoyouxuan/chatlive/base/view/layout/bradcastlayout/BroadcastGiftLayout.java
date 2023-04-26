package com.rongtuoyouxuan.chatlive.base.view.layout.bradcastlayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.BaseBroadcastGiftModel;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.BlastBroadcastGiftModel;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.BroadcastType;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.LuckyBroadcastModel;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomBannerGift;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.SuperLuckyMoneyBroadcastModel;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.stream.R;

import java.util.concurrent.ConcurrentLinkedQueue;

public class BroadcastGiftLayout extends FrameLayout {

    private final static int DURATION = 4000;
    int screenWidth;
    private ConcurrentLinkedQueue<BaseBroadcastGiftModel> radioQueue = new ConcurrentLinkedQueue<>();//广播队列
    private IBaseBradcastGiftLayout mBaseBradcastGiftLayout;
    private boolean isShowing = false;
    private TranslateAnimation translateAnimation;
    //    private IMLiveViewModel imViewModel;
    private RelativeLayout content;

    public BroadcastGiftLayout(Context context) {
        this(context, null);
    }

    public BroadcastGiftLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BroadcastGiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initViewModel();
        init(context);
    }

//    private void initViewModel() {
//        if((FragmentActivity) getContext() instanceof StreamActivity){
//            imViewModel = ViewModelUtils.get((FragmentActivity) getContext(), IMLiveViewModel.class);
//        }else{
//            imViewModel = ViewModelUtils.getLive(IMLiveViewModel.class);
//        }
//    }

    private void init(Context context) {
        inflate(context, R.layout.qf_stream_layout_broadcast_gift, this);
        screenWidth = getScreenWidth();
        content = findViewById(R.id.rl_broadcast_content);
        setVisibility(GONE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (translateAnimation != null) {
            translateAnimation.cancel();
        }
        super.onDetachedFromWindow();
    }

    public void addWinningMsg(BaseBroadcastGiftModel info) {
        if (canAddData(info)) {
            radioQueue.offer(info);
            start();
        }
    }

    private boolean canAddData(BaseBroadcastGiftModel info) {
        if (null != getContext()) {
            return null != info && (info.getType() == BroadcastType.COMMONGIFT
                    || info.getType() == BroadcastType.SUPER_LUCKY_MONEY);
        }
        return false;
//        if (getContext() != null && getContext() instanceof StreamActivity) {
//            if (info.getType() == BroadcastType.COMMONGIFT) {
//                RoomBannerGift bannerGift = (RoomBannerGift) info;
//                switch (bannerGift.giftid) {
//                    case CommonGIftType.LUCKYGIFT:
//                        return false;
//                    default:
//                        return true;
//                }
//            } else if (BroadcastType.LUCKGIFTAWARD == info.getType()
//                    || BroadcastType.LIVE_PARTY_TASK == info.getType()) {
//                return true;
//            }
//            return false;
//        }
//        return true;
    }

    private void startAnimation() {
        translateAnimation = new TranslateAnimation(screenWidth, 0, 0, 0);
        translateAnimation.setDuration(DURATION);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (content.getChildCount() > 0) {
                    View view = content.getChildAt(0);
                    if (null != view) {
                        int width = 0;
                        if (view instanceof ViewGroup) {
                            ViewGroup vg = (ViewGroup) view;
                            for (int i = 0; i < vg.getChildCount(); i++) {
                                View viewI = vg.getChildAt(i);
                                if (viewI instanceof ViewGroup) {
                                    ViewGroup vgI = (ViewGroup) viewI;
                                    for (int j = 0; j < vgI.getChildCount(); j++) {
                                        width += vgI.getChildAt(j).getMeasuredWidth();
                                    }
                                }
                            }
                        }
                        width += SizeUtils.dp2px(45f);
                        ULog.d("BroadcastGiftLayout", "BBBBB>>>" + width + ">>>" + content.getMeasuredWidth());
                        if (width > content.getWidth()) {
                            ObjectAnimator animator = ObjectAnimator.ofFloat(
                                    view,
                                    View.TRANSLATION_X,
                                    0f, -(width - content.getWidth())
                            );
                            animator.setInterpolator(new AccelerateDecelerateInterpolator());
                            animator.setDuration(2000);
                            animator.start();
                        }
                    }
                }

                mBaseBradcastGiftLayout.setTextSelected(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                //设置动画持续时长
                alphaAnimation.setDuration(2000);
                alphaAnimation.setStartOffset(3000);
                //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
                alphaAnimation.setFillAfter(true);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isShowing = false;
                        start();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                //开始动画
                content.startAnimation(alphaAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        content.startAnimation(translateAnimation);
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private void start() {
        if (!isShowing) {
            isShowing = true;
            final BaseBroadcastGiftModel radioInfo = radioQueue.poll();
            if (radioInfo != null) {
                if (mBaseBradcastGiftLayout != null) {
                    mBaseBradcastGiftLayout.release();
                }
                content.removeAllViews();
                toGiftMatcher(radioInfo);
                setVisibility(VISIBLE);
                startAnimation();
            } else {
                isShowing = false;
                if (mBaseBradcastGiftLayout != null) {
                    mBaseBradcastGiftLayout.release();
                }
                content.removeAllViews();
                setVisibility(GONE);
            }
        }
    }

    private void toGiftMatcher(BaseBroadcastGiftModel radioInfo) {
        switch (radioInfo.getType()) {
            case BLASTGIFT:
                BlastBroadcastGiftLayout blastBroadcastGiftLayout = new BlastBroadcastGiftLayout(getContext());
                blastBroadcastGiftLayout.bindData((BlastBroadcastGiftModel) radioInfo);
                mBaseBradcastGiftLayout = blastBroadcastGiftLayout;
                content.addView(blastBroadcastGiftLayout);
                break;
            case LUCKYGIFT:
                LuckyBroadcastGiftLayout luckyBroadcastGiftLayout = new LuckyBroadcastGiftLayout(getContext());
                luckyBroadcastGiftLayout.bindData((LuckyBroadcastModel) radioInfo);
                mBaseBradcastGiftLayout = luckyBroadcastGiftLayout;
                content.addView(luckyBroadcastGiftLayout);
                break;
            case COMMONGIFT:
                RoomBannerGift mRadioInfo = (RoomBannerGift) radioInfo;
                if (CommonGIftType.LUCKYGIFT.equalsIgnoreCase(mRadioInfo.giftid)) {
                    LuckyCommonBroadcastGiftLayout luckyCommonBroadcastGiftLayout = new LuckyCommonBroadcastGiftLayout(getContext());
                    luckyCommonBroadcastGiftLayout.bindData(mRadioInfo);
                    mBaseBradcastGiftLayout = luckyCommonBroadcastGiftLayout;
                    content.addView(luckyCommonBroadcastGiftLayout);
                } else {
                    RelativeLayout.LayoutParams lpImageView = new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
                    lpImageView.addRule(RelativeLayout.CENTER_HORIZONTAL);

                    CommonBroadcastGiftLayout commonBroadcastGiftLayout = new CommonBroadcastGiftLayout(getContext());
                    commonBroadcastGiftLayout.bindData(mRadioInfo);
                    mBaseBradcastGiftLayout = commonBroadcastGiftLayout;
                    content.addView(commonBroadcastGiftLayout, lpImageView);
                }
                break;
            case SUPER_LUCKY_MONEY:
                SuperLuckyMoneyBroadcastModel superLuckyMoneyBroadcastModel = (SuperLuckyMoneyBroadcastModel) radioInfo;
                RelativeLayout.LayoutParams lpImageView = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                lpImageView.addRule(RelativeLayout.CENTER_HORIZONTAL);

                SuperLuckyMoneyBroadcastLayout broadcastLayout = new SuperLuckyMoneyBroadcastLayout(getContext());
                broadcastLayout.bindData(superLuckyMoneyBroadcastModel);
                mBaseBradcastGiftLayout = broadcastLayout;
                content.addView(broadcastLayout, lpImageView);
                break;
        }
    }
}