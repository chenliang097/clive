package com.rongtuoyouxuan.chatlive.base.view.layout;

import android.content.Context;
import androidx.annotation.CallSuper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.rongtuoyouxuan.chatlive.stream.R;
import com.rongtuoyouxuan.libuikit.layout.MarqueeTextListenerView;

import static com.rongtuoyouxuan.libuikit.layout.MarqueeTextListenerView.MARQUEE_STOPPED;

public abstract class BaseRoomTitleView extends RelativeLayout {

    private final static int DURATION = 4000;
    public TranslateAnimation translateAnimation;
    private MarqueeTextListenerView titel;
    private int screenWidth;
    private Animation out_anima;

    public BaseRoomTitleView(Context context) {
        this(context, null);
    }

    public BaseRoomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRoomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @CallSuper
    public void init(Context context) {
        screenWidth = getScreenWidth();
        initViewModel(context);
        inflate(context, R.layout.qf_stream_live_layout_room_title, this);
        initView(context);
        out_anima = AnimationUtils.loadAnimation(context, R.anim.layout_ltr_out);
        out_anima.setDuration(200);
    }

    private void initView(Context context) {
        titel = findViewById(R.id.tv_room_title);
        titel.setFocusable(true);
        setVisibility(GONE);
        titel.setOnScrollingStateChangedListener(new MarqueeTextListenerView.OnScrollingStateChangedListener() {
            @Override
            public void onStateChanged(byte state) {
                if (state == MARQUEE_STOPPED) {
                    out_anima.setStartOffset(0);
                    setVisibility(GONE);
                    startAnimation(out_anima);
                }
            }
        });
    }

    public void setTitel(String text) {
        if (translateAnimation != null) {
            return;
        }
        if (!TextUtils.isEmpty(text)) {
            titel.setText(String.format(getContext().getString(R.string.room_title), text));
            setVisibility(GONE);
            startAnimation();
        } else {
            titel.setText("");
            setVisibility(GONE);
        }
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
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
                titel.setMarqueeEnable(true);
                byte state = titel.getState();
                if (state == MarqueeTextListenerView.MARQUEE_STARTING || state == MarqueeTextListenerView.MARQUEE_RUNNING) {
                    // TODO 开始走马灯
                } else {
                    // TODO 长度没达到走马灯，延迟2秒开启走马灯
                    out_anima.setStartOffset(2000);
                    setVisibility(GONE);
                    startAnimation(out_anima);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(translateAnimation);
    }

    @Override
    protected void onDetachedFromWindow() {
        clearAnimation();
        super.onDetachedFromWindow();
    }

    public abstract void initViewModel(Context context);
}
